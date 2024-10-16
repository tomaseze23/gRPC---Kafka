from sqlalchemy import Column, BigInteger, Text, TIMESTAMP, ForeignKey
from db.database import Base

class Catalogo(Base):
    __tablename__ = 'catalogos'

    id = Column(BigInteger, primary_key=True, autoincrement=True)
    tienda_id = Column(Text, ForeignKey('tiendas.codigo', ondelete='CASCADE'), nullable=False)
    nombre_catalogo = Column(Text, nullable=False)
    fecha_creacion = Column(TIMESTAMP, default='now()')
