from sqlalchemy import Column, BigInteger, Text, ForeignKey, TIMESTAMP, CheckConstraint
from database_dos import Base

class OrdenCompra(Base):
    __tablename__ = 'ordenes_compra'

    id = Column(BigInteger, primary_key=True, autoincrement=True)
    tienda_id = Column(Text, ForeignKey('tiendas.codigo', ondelete='CASCADE'), nullable=False)
    fecha_solicitud = Column(TIMESTAMP(timezone=True), default='now()')
    fecha_envio = Column(TIMESTAMP(timezone=True))
    fecha_recepcion = Column(TIMESTAMP(timezone=True))
    estado = Column(
        Text, 
        CheckConstraint("estado in ('SOLICITADA', 'RECHAZADA', 'ACEPTADA', 'PAUSADA')"),
        nullable=False
    )
    observaciones = Column(Text)
