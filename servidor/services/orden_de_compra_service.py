from kafka.kafka_producer import KafkaProducer  
from datetime import datetime, timedelta
from sqlalchemy.orm import Session
from models import OrdenCompra, ItemOrdenCompra, OrdenDespacho, Producto

class OrdenDeCompraService:
    def __init__(self, db_session: Session):
        self.db_session = db_session
        self.kafka_producer = KafkaProducer()  

    def crear_orden_compra(self, tienda_id, estado, observaciones, items): 
        """
        Crea una nueva orden de compra y sus ítems asociados, y envía un mensaje a Kafka.
        Actualiza el stock de productos si la orden es aceptada.
        En caso de que no haya stock suficiente, la orden queda ACEPTADA, 
        pero se informa sobre los artículos faltantes.
        """
        errores, faltantes_stock = self.validar_items(items)
        
        estado, observaciones = self.generar_estado_observaciones(errores, faltantes_stock)
        print("Estado y observaciones generadas:", estado, observaciones)
        
        orden_compra = self.insertar_orden_compra(tienda_id, estado, observaciones)
        
        if not errores:
            self.insertar_items_orden_compra(orden_compra.id, items)

            if not faltantes_stock:
                self.actualizar_stock(items)
                orden_despacho_id = self.generar_orden_despacho(orden_compra.id)
                self.enviar_mensaje_despacho(tienda_id, orden_despacho_id, orden_compra.id)

        self.db_session.commit()

        self.enviar_mensaje_kafka(tienda_id, orden_compra.id, estado, observaciones, items)

        return orden_compra.id

    def validar_items(self, items):
        """Valida los ítems y devuelve errores y artículos faltantes de stock."""
        errores = []
        faltantes_stock = [] 
        productos_agrupados = {}

        for item in items:
            print(f"===> Procesando ítem: {item}")
            producto_id = item['producto_id']
            
            if item['cantidad'] < 1:
                error_msg = f"Artículo {producto_id}: cantidad mal informada."
                errores.append(error_msg)
                print(f"Error de cantidad: {error_msg}")
            
            if producto_id in productos_agrupados:
                productos_agrupados[producto_id]['cantidad'] += item['cantidad']
            else:
                productos_agrupados[producto_id] = {
                    'producto_id': producto_id,
                    'cantidad': item['cantidad']
                }

        print(f"Productos agrupados: {productos_agrupados}")
        
        for producto_id, datos in productos_agrupados.items():
            print(f"===> Validando stock del producto {producto_id} con cantidad total solicitada: {datos['cantidad']}")
            stock = self.db_session.query(Productos).filter(Productos.codigo == producto_id).first()

            print(f"Stock disponible para producto {producto_id}: {stock}")

            if not stock:
                error_msg = f"Artículo {producto_id}: no existe en el inventario."
                errores.append(error_msg)
                print(f"Error de inventario: {error_msg}")
            else:
                if datos['cantidad'] > stock.cantidad_stock_proveedor:
                    faltante_stock = {
                        'producto_id': producto_id,
                        'cantidad_solicitada': datos['cantidad'],
                        'cantidad_disponible': stock.cantidad_stock_proveedor
                    }
                    faltantes_stock.append(faltante_stock)
                    print(f"Stock insuficiente para {producto_id}: {faltante_stock}")

        print(f"Errores detectados: {errores}")
        print(f"Faltantes de stock: {faltantes_stock}")
        
        return errores, faltantes_stock

    def generar_estado_observaciones(self, errores, faltantes_stock):
        """Genera el estado y las observaciones de la orden."""
        estado = 'ACEPTADA'
        if errores:
            observaciones = '; '.join(errores)
            estado = 'RECHAZADA'
        elif faltantes_stock:
            observaciones = "Artículos faltantes: " + '; '.join([
                f"{falta['producto_id']}: solicitado {falta['cantidad_solicitada']}, disponible {falta['cantidad_disponible']}"
                for falta in faltantes_stock
            ])
            estado = 'PAUSADA'
        else:
            observaciones = ""

        return estado, observaciones

    def insertar_orden_compra(self, tienda_id, estado, observaciones):
        """Inserta la orden de compra en la base de datos y devuelve su objeto."""
        orden_compra = OrdenCompra(
            tienda_id=tienda_id,
            estado=estado,
            observaciones=observaciones
        )
        self.db_session.add(orden_compra)
        self.db_session.flush()  # Para obtener el ID después de agregar
        return orden_compra

    def insertar_items_orden_compra(self, orden_compra_id, items):
        """Inserta los ítems de la orden de compra en la base de datos."""
        for item in items:
            if item['cantidad'] > 0: 
                item_orden_compra = ItemOrdenCompra(
                    orden_compra_id=orden_compra_id,
                    producto_id=item['producto_id'],
                    color=item['color'],
                    talle=item['talle'],
                    cantidad=item['cantidad']
                )
                self.db_session.add(item_orden_compra)

    def actualizar_stock(self, items):
        """Actualiza el stock de productos en la base de datos."""
        for item in items:
            producto = self.db_session.query(Producto).filter(Producto.codigo == item['producto_id']).first()
            if producto:
                producto.cantidad_stock_proveedor -= item['cantidad']
                self.db_session.add(producto)

    def enviar_mensaje_kafka(self, tienda_id, orden_compra_id, estado, observaciones, items):
        """Envía un mensaje a Kafka con la información de la orden de compra."""
        mensaje_kafka = {
            'orden_id': orden_compra_id,
            'tienda_id': tienda_id,
            'estado': 'ACEPTADA' if estado == 'PAUSADA' else estado,
            'observaciones': observaciones,
            'items': items
        }
        self.kafka_producer.send_message(f"solicitudes", mensaje_kafka) 

    def obtener_ordenes_compra(self):
        """
        Retorna todas las órdenes de compra.
        """
        rows = self.db_session.query(OrdenCompra).all()
        print("Registros de órdenes de compra:", rows)  
        if not rows:
            print("No se encontraron órdenes de compra.")

        return [orden.__dict__ for orden in rows]

    def obtener_items_por_orden(self, orden_compra_id):
        """
        Retorna todos los ítems asociados a una orden de compra.
        """
        items = self.db_session.query(ItemOrdenCompra).filter(ItemOrdenCompra.orden_compra_id == orden_compra_id).all()
        return [item.__dict__ for item in items]

    def obtener_articulo_por_id(self, articulo_id):
        """
        Retorna un artículo por su ID.
        """
        articulo = self.db_session.query(Producto).filter(Producto.id == articulo_id).first()
        
        if articulo:
            return articulo.__dict__
        return None  
    
    def generar_orden_despacho(self, orden_compra_id):
        """Genera una nueva orden de despacho y devuelve su ID."""
        orden_despacho = OrdenDespacho(
            orden_compra_id=orden_compra_id,
            fecha_estimacion_envio=datetime.now() + timedelta(days=7),
            estado='PENDIENTE'
        )
        self.db_session.add(orden_despacho)
        self.db_session.flush()  # Para obtener el ID después de agregar
        return orden_despacho.id

    def enviar_mensaje_despacho(self, tienda_id, orden_despacho_id, orden_compra_id):
        """Envía un mensaje al topic de despacho con la información relevante."""
        fecha_estimacion_envio = datetime.now() + timedelta(days=7)
        
        mensaje_despacho = {
            'orden_despacho_id': orden_despacho_id,
            'orden_compra_id': orden_compra_id,
            'fecha_estimacion_envio': fecha_estimacion_envio.isoformat()  
        }
        
        self.kafka_producer.send_message(f"despacho", mensaje_despacho)  

    def marcar_orden_recibida(self, orden_id, despacho_id):
        """
        Marca una orden como recibida en la base de datos.
        """
        orden = self.db_session.query(OrdenCompra).filter(OrdenCompra.id == orden_id).first()

        if orden is None:
            print(f"Orden {orden_id} no existe en la base de datos.")
            return False

        estado = orden.estado 

        if estado != 'ACEPTADA':
            print(f"Orden {orden_id} no puede ser marcada como recibida porque no está en estado ACEPTADA. Está en {estado}")
            return False

        orden.fecha_recepcion = datetime.now()
        self.db_session.commit()

        print(f"Orden {orden_id} marcada como recibida con despacho {despacho_id}.")
        return True
