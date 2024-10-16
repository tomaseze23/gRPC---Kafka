from sqlalchemy import Column, BigInteger, Text, Integer, ForeignKey
from db.database import Base

class ProductoTienda(Base):
    __tablename__ = 'producto_tienda'

    id = Column(BigInteger, primary_key=True, autoincrement=True)
    producto_id = Column(BigInteger, ForeignKey('productos.id', ondelete='CASCADE'), nullable=False)
    tienda_id = Column(Text, ForeignKey('tiendas.codigo', ondelete='CASCADE'), nullable=False)
    color = Column(Text)
    talle = Column(Text)
    cantidad = Column(Integer, default=0)
