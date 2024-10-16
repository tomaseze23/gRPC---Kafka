from sqlalchemy import Column, BigInteger, Text, Integer
from database_dos import Base

class Producto(Base):
    __tablename__ = 'productos'

    id = Column(BigInteger, primary_key=True, autoincrement=True)
    nombre = Column(Text, nullable=False)
    codigo = Column(Text, unique=True, nullable=False)
    talle = Column(Text)
    foto = Column(Text)
    color = Column(Text)
    cantidad_stock_proveedor = Column(Integer, default=0)
