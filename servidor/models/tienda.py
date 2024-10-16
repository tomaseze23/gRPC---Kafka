from sqlalchemy import Column, Text, Boolean
from db.database import Base

class Tienda(Base):
    __tablename__ = 'tiendas'

    codigo = Column(Text, primary_key=True)
    direccion = Column(Text)
    ciudad = Column(Text)
    provincia = Column(Text)
    habilitada = Column(Boolean, default=True)
