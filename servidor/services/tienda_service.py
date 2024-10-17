from grpcpb2 import tienda_pb2
from grpcpb2 import tienda_pb2_grpc
from sqlalchemy.orm import Session
from models import Tienda
from models import ProductoTienda

class TiendaService(tienda_pb2_grpc.TiendaServiceServicer):
    def __init__(self, db_session: Session):
        self.db_session = db_session

    def CreateTienda(self, request, context):
        nueva_tienda = Tienda(
            codigo=request.codigo,
            direccion=request.direccion,
            ciudad=request.ciudad,
            provincia=request.provincia,
            habilitada=request.habilitada
        )
        
        self.db_session.add(nueva_tienda)
        
        self.db_session.commit()
        
        for producto_id in request.producto_ids:
            tienda_producto = ProductoTienda(tienda_codigo=request.codigo, producto_id=producto_id)
            self.db_session.add(tienda_producto)

        self.db_session.commit()

        return request
    
    def GetTienda(self, request, context):
        tienda = self.db_session.query(Tienda).filter_by(codigo=request.codigo).first()
        
        if tienda:
            return tienda_pb2.Tienda(
                codigo=tienda.codigo,
                direccion=tienda.direccion,
                ciudad=tienda.ciudad,
                provincia=tienda.provincia,
                habilitada=tienda.habilitada,
                producto_ids=[]  # Debes implementar la lógica para obtener los productos relacionados
            )
        else:
            return tienda_pb2.Tienda()
    
    def UpdateTienda(self, request, context):
        tienda = self.db_session.query(Tienda).get(request.codigo)
        
        if tienda:
            tienda.direccion = request.direccion
            tienda.ciudad = request.ciudad
            tienda.provincia = request.provincia
            tienda.habilitada = request.habilitada
            self.db_session.commit()
            return request
        else:
            context.set_details("Tienda no encontrada.")
            return tienda_pb2.Tienda()

    def DeleteTienda(self, request, context):
        tienda = self.db_session.query(Tienda).get(request.codigo)
        
        if tienda:
            self.db_session.delete(tienda)
            self.db_session.commit()
            return request
        else:
            context.set_details("Tienda no encontrada.")
            return tienda_pb2.Tienda()

    def ListTiendas(self, request, context):
        tiendas = self.db_session.query(Tienda).all()
        
        tiendas_list = [
            tienda_pb2.Tienda(
                codigo=tienda.codigo,
                direccion=tienda.direccion,
                ciudad=tienda.ciudad,
                provincia=tienda.provincia,
                habilitada=tienda.habilitada,
                producto_ids=[]  
            ) for tienda in tiendas
        ]
        
        return tienda_pb2.TiendaList(tiendas=tiendas_list)
