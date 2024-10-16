from grpcpb2 import producto_pb2
from grpcpb2 import producto_pb2_grpc
from kafka.kafka_producer import KafkaProducer
from sqlalchemy.orm import Session
from models import Producto  # Asegúrate de que tu modelo esté importado correctamente

class ProductoService(producto_pb2_grpc.ProductoServiceServicer):
    def __init__(self, db_session: Session):
        self.db_session = db_session
        self.kafka_producer = KafkaProducer()

    def CreateProducto(self, request, context):
        nuevo_producto = Producto(
            nombre=request.nombre,
            codigo=request.codigo,
            talle=request.talle,
            foto=request.foto,
            color=request.color
        )
        self.db_session.add(nuevo_producto)
        self.db_session.commit()
        return request

    def GetProducto(self, request, context):
        producto = self.db_session.query(Producto).get(request.id)
        if producto:
            return producto_pb2.Producto(
                id=producto.id,
                nombre=producto.nombre,
                codigo=producto.codigo,
                talle=producto.talle,
                foto=producto.foto,
                color=producto.color
            )
        else:
            return producto_pb2.Producto()

    def UpdateProducto(self, request, context):
        producto = self.db_session.query(Producto).get(request.id)
        if producto:
            producto.nombre = request.nombre
            producto.codigo = request.codigo
            producto.talle = request.talle
            producto.foto = request.foto
            producto.color = request.color
            self.db_session.commit()
            return request
        else:
            context.set_details("Producto no encontrado.")
            return producto_pb2.Producto()

    def DeleteProducto(self, request, context):
        producto = self.db_session.query(Producto).get(request.id)
        if producto:
            self.db_session.delete(producto)
            self.db_session.commit()
            return request
        else:
            context.set_details("Producto no encontrado.")
            return producto_pb2.Producto()

    def ListProductos(self, request, context):
        productos = self.db_session.query(Producto).all()
        productos_list = [
            producto_pb2.Producto(
                id=producto.id,
                nombre=producto.nombre,
                codigo=producto.codigo,
                talle=producto.talle,
                foto=producto.foto,
                color=producto.color
            ) for producto in productos
        ]
        return producto_pb2.ProductoList(productos=productos_list)

    def crear_producto(self, nombre, codigo, talle, foto, color, cantidad_stock):
        """
        Crea un nuevo producto en la base de datos y envía un mensaje a Kafka.
        """
        nuevo_producto = Producto(
            nombre=nombre,
            codigo=codigo,
            talle=talle,
            foto=foto,
            color=color,
            cantidad_stock_proveedor=cantidad_stock
        )

        self.db_session.add(nuevo_producto)
        self.db_session.commit()

        # Enviar mensaje a Kafka
        self.enviar_mensaje_kafka(nuevo_producto.id, codigo, talle, color, foto)

        return nuevo_producto.id

    def enviar_mensaje_kafka(self, producto_id, codigo, talle, color, foto):
        """Envía un mensaje a Kafka con la información del nuevo producto."""
        mensaje_kafka = {
            'producto_id': producto_id,
            'codigo': codigo,
            'talle': talle,
            'color': color,
            'foto': foto
        }
        self.kafka_producer.send_message("/novedades", mensaje_kafka)
