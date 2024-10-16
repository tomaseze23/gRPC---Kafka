from sqlalchemy import Column, BigInteger, Text, Boolean, ForeignKey
from database_dos import Base

class Usuario(Base):
    __tablename__ = 'usuarios'

    id = Column(BigInteger, primary_key=True, autoincrement=True)
    nombre_usuario = Column(Text, unique=True, nullable=False)
    contrasena = Column(Text, nullable=False)
    tienda_id = Column(Text, ForeignKey('tiendas.codigo', ondelete='SET NULL'))
    nombre = Column(Text)
    apellido = Column(Text)
    habilitado = Column(Boolean, default=True)
