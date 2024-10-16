from sqlalchemy import Column, BigInteger, Text, Integer, ForeignKey
from database_dos import Base

class ItemOrdenCompra(Base):
    __tablename__ = 'items_orden_compra'

    id = Column(BigInteger, primary_key=True, autoincrement=True)
    orden_compra_id = Column(BigInteger, ForeignKey('ordenes_compra.id', ondelete='CASCADE'), nullable=False)
    producto_id = Column(BigInteger, ForeignKey('productos.id', ondelete='CASCADE'), nullable=False)
    color = Column(Text)
    talle = Column(Text)
    cantidad = Column(Integer, nullable=False)
