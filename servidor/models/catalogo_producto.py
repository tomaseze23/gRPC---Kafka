from sqlalchemy import Column, BigInteger, ForeignKey
from database_dos import Base

class CatalogoProducto(Base):
    __tablename__ = 'catalogo_producto'

    id = Column(BigInteger, primary_key=True, autoincrement=True)
    catalogo_id = Column(BigInteger, ForeignKey('catalogos.id', ondelete='CASCADE'), nullable=False)
    producto_id = Column(BigInteger, ForeignKey('productos.id', ondelete='CASCADE'), nullable=False)
