from sqlalchemy import Column, BigInteger, TIMESTAMP, Text, ForeignKey, CheckConstraint
from database_dos import Base

class OrdenDespacho(Base):
    __tablename__ = 'ordenes_despacho'

    id = Column(BigInteger, primary_key=True, autoincrement=True)
    orden_compra_id = Column(BigInteger, ForeignKey('ordenes_compra.id', ondelete='CASCADE'), nullable=False)
    fecha_estimacion_envio = Column(TIMESTAMP(timezone=True))
    estado = Column(
        Text, 
        CheckConstraint("estado in ('PENDIENTE', 'ENVIADO', 'RECIBIDO')"),
        nullable=False
    )
