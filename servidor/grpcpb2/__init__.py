# servidor/grpc/__init__.py

# Importar los módulos generados por gRPC
from .empty_pb2 import *
from .empty_pb2_grpc import *
from .helloworld_pb2 import *
from .helloworld_pb2_grpc import *
from .producto_pb2 import *
from .producto_pb2_grpc import *
from .producto_tienda_pb2 import *
from .producto_tienda_pb2_grpc import *
from .tienda_pb2 import *
from .tienda_pb2_grpc import *
from .usuario_pb2 import *
from .usuario_pb2_grpc import *

# También puedes usar __all__ para controlar la exportación
__all__ = [
    'empty_pb2',
    'empty_pb2_grpc',
    'helloworld_pb2',
    'helloworld_pb2_grpc',
    'producto_pb2',
    'producto_pb2_grpc',
    'producto_tienda_pb2',
    'producto_tienda_pb2_grpc',
    'tienda_pb2',
    'tienda_pb2_grpc',
    'usuario_pb2',
    'usuario_pb2_grpc',
]
