import sys
from hupper import start_reloader
import grpc
from concurrent import futures
from grpcpb2 import producto_pb2_grpc
from grpcpb2 import tienda_pb2_grpc
from grpcpb2 import usuario_pb2_grpc
from grpcpb2 import producto_tienda_pb2_grpc
from services.usuario_service import UsuarioService
from services.producto_service import ProductoService
from services.producto_tienda_service import ProductoTiendaService
from services.tienda_service import TiendaService
from db.database import get_db

def serve_grpc():
    server = grpc.server(futures.ThreadPoolExecutor(max_workers=10))

    usuario_pb2_grpc.add_UsuarioServiceServicer_to_server(UsuarioService(get_db()), server)
    producto_pb2_grpc.add_ProductoServiceServicer_to_server(ProductoService(get_db()), server)
    tienda_pb2_grpc.add_TiendaServiceServicer_to_server(TiendaService(get_db()), server)
    producto_tienda_pb2_grpc.add_ProductoTiendaServiceServicer_to_server(ProductoTiendaService(get_db()), server)

    server.add_insecure_port('[::]:50051')

    server.start()
    print("Servidor gRPC corriendo en el puerto 50051...")

    try:
        server.wait_for_termination()
    except KeyboardInterrupt:
        print("Servidor detenido por el usuario.")

if __name__ == '__main__':
    if 'reload' not in sys.argv:
        reloader = start_reloader('servidor.serve_grpc')
    else:
        serve_grpc()
