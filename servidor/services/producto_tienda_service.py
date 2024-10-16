from grpcpb2 import producto_tienda_pb2
from grpcpb2 import producto_tienda_pb2_grpc
from sqlalchemy.orm import Session
from models import ProductoTienda  # Asegúrate de que tu modelo esté importado correctamente

class ProductoTiendaService(producto_tienda_pb2_grpc.ProductoTiendaServiceServicer):
    def __init__(self, db_session: Session):
        self.db_session = db_session

    def CreateProductoTienda(self, request, context):
        nuevo_producto_tienda = ProductoTienda(
            producto_id=request.producto_id,
            tienda_id=request.tienda_id,
            color=request.color,
            talle=request.talle,
            cantidad=request.cantidad
        )
        self.db_session.add(nuevo_producto_tienda)
        self.db_session.commit()
        return request

    def GetProductoTienda(self, request, context):
        producto_tienda = self.db_session.query(ProductoTienda).get(request.id)
        if producto_tienda:
            return producto_tienda_pb2.ProductoTienda(
                id=producto_tienda.id,
                producto_id=producto_tienda.producto_id,
                tienda_id=producto_tienda.tienda_id,
                color=producto_tienda.color,
                talle=producto_tienda.talle,
                cantidad=producto_tienda.cantidad
            )
        else:
            return producto_tienda_pb2.ProductoTienda()

    def UpdateProductoTienda(self, request, context):
        producto_tienda = self.db_session.query(ProductoTienda).get(request.id)
        if producto_tienda:
            producto_tienda.producto_id = request.producto_id
            producto_tienda.tienda_id = request.tienda_id
            producto_tienda.color = request.color
            producto_tienda.talle = request.talle
            producto_tienda.cantidad = request.cantidad
            self.db_session.commit()
            return request
        else:
            context.set_details("Producto en tienda no encontrado.")
            return producto_tienda_pb2.ProductoTienda()

    def DeleteProductoTienda(self, request, context):
        producto_tienda = self.db_session.query(ProductoTienda).get(request.id)
        if producto_tienda:
            self.db_session.delete(producto_tienda)
            self.db_session.commit()
            return request
        else:
            context.set_details("Producto en tienda no encontrado.")
            return producto_tienda_pb2.ProductoTienda()

    def ListProductoTiendas(self, request, context):
        productos_tienda = self.db_session.query(ProductoTienda).all()
        productos_list = [
            producto_tienda_pb2.ProductoTienda(
                id=producto_tienda.id,
                producto_id=producto_tienda.producto_id,
                tienda_id=producto_tienda.tienda_id,
                color=producto_tienda.color,
                talle=producto_tienda.talle,
                cantidad=producto_tienda.cantidad
            ) for producto_tienda in productos_tienda
        ]
        return producto_tienda_pb2.ProductoTiendaList(productos=productos_list)
