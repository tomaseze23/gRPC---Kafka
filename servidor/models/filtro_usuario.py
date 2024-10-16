from sqlalchemy import Column, BigInteger, Text, JSON, TIMESTAMP, ForeignKey
from database_dos import Base

class FiltroUsuario(Base):
    __tablename__ = 'filtros_usuario'

    id = Column(BigInteger, primary_key=True, autoincrement=True)
    usuario_id = Column(BigInteger, ForeignKey('usuarios.id', ondelete='CASCADE'), nullable=False)
    nombre_filtro = Column(Text, nullable=False)
    filtro = Column(JSON, nullable=False)
    fecha_creacion = Column(TIMESTAMP, default='now()')
