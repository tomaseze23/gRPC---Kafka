DELETE FROM catalogo_producto;
DELETE FROM filtros_usuario;
DELETE FROM items_orden_compra;
DELETE FROM ordenes_despacho;
DELETE FROM ordenes_compra;
DELETE FROM producto_tienda;

DELETE FROM catalogos;
DELETE FROM productos;
DELETE FROM usuarios;
DELETE FROM tiendas;

DO $$ 
DECLARE
    seq_name TEXT;
BEGIN
    SELECT pg_get_serial_sequence('usuarios', 'id') INTO seq_name;
    IF seq_name IS NOT NULL THEN
        EXECUTE 'ALTER SEQUENCE ' || seq_name || ' RESTART WITH 1';
    END IF;

    SELECT pg_get_serial_sequence('productos', 'id') INTO seq_name;
    IF seq_name IS NOT NULL THEN
        EXECUTE 'ALTER SEQUENCE ' || seq_name || ' RESTART WITH 1';
    END IF;

    SELECT pg_get_serial_sequence('producto_tienda', 'id') INTO seq_name;
    IF seq_name IS NOT NULL THEN
        EXECUTE 'ALTER SEQUENCE ' || seq_name || ' RESTART WITH 1';
    END IF;

    SELECT pg_get_serial_sequence('ordenes_compra', 'id') INTO seq_name;
    IF seq_name IS NOT NULL THEN
        EXECUTE 'ALTER SEQUENCE ' || seq_name || ' RESTART WITH 1';
    END IF;

    SELECT pg_get_serial_sequence('items_orden_compra', 'id') INTO seq_name;
    IF seq_name IS NOT NULL THEN
        EXECUTE 'ALTER SEQUENCE ' || seq_name || ' RESTART WITH 1';
    END IF;

    SELECT pg_get_serial_sequence('ordenes_despacho', 'id') INTO seq_name;
    IF seq_name IS NOT NULL THEN
        EXECUTE 'ALTER SEQUENCE ' || seq_name || ' RESTART WITH 1';
    END IF;

    SELECT pg_get_serial_sequence('filtros_usuario', 'id') INTO seq_name;
    IF seq_name IS NOT NULL THEN
        EXECUTE 'ALTER SEQUENCE ' || seq_name || ' RESTART WITH 1';
    END IF;

    SELECT pg_get_serial_sequence('catalogos', 'id') INTO seq_name;
    IF seq_name IS NOT NULL THEN
        EXECUTE 'ALTER SEQUENCE ' || seq_name || ' RESTART WITH 1';
    END IF;

    SELECT pg_get_serial_sequence('catalogo_producto', 'id') INTO seq_name;
    IF seq_name IS NOT NULL THEN
        EXECUTE 'ALTER SEQUENCE ' || seq_name || ' RESTART WITH 1';
    END IF;
END $$;

INSERT INTO tiendas (codigo, direccion, ciudad, provincia, habilitada)
VALUES 
    ('T001', 'Calle Falsa 123', 'Ciudad X', 'Provincia X', true),
    ('T002', 'Calle Verdadera 456', 'Ciudad Y', 'Provincia Y', true);

INSERT INTO productos (nombre, codigo, talle, foto, color, cantidad_stock_proveedor)
VALUES 
    ('Producto A', 'A001', 'M', 'url/to/fotoA', 'Rojo', 0),
    ('Producto B', 'B002', 'L', 'url/to/fotoB', 'Azul', 150);

INSERT INTO producto_tienda (producto_id, tienda_id, color, talle, cantidad)
VALUES 
    ((SELECT id FROM productos WHERE codigo='A001'), 'T001', 'Rojo', 'M', 100),
    ((SELECT id FROM productos WHERE codigo='B002'), 'T002', 'Azul', 'L', 150);

INSERT INTO usuarios (nombre_usuario, contrasena, tienda_id, nombre, apellido, habilitado)
VALUES 
    ('juan', '1', 'T001', 'Juan', 'Pérez', true),
    ('ana', '2', 'T002', 'Ana', 'Gómez', true);

INSERT INTO catalogos (tienda_id, nombre_catalogo, fecha_creacion)
VALUES
    ('T001', 'Catalogo Verano', '2024-06-01T10:00:00+00:00'),
    ('T002', 'Catalogo Invierno', '2024-06-15T12:00:00+00:00');

INSERT INTO catalogo_producto (catalogo_id, producto_id)
VALUES
    ((SELECT id FROM catalogos WHERE nombre_catalogo = 'Catalogo Verano'), 
     (SELECT id FROM productos WHERE codigo = 'A001')),
    ((SELECT id FROM catalogos WHERE nombre_catalogo = 'Catalogo Invierno'), 
     (SELECT id FROM productos WHERE codigo = 'B002'));

INSERT INTO ordenes_compra (tienda_id, fecha_solicitud, fecha_envio, fecha_recepcion, estado, observaciones)
VALUES
    ('T001', '2024-06-05T10:30:00+00:00', '2024-06-07T14:00:00+00:00', '2024-06-10T09:00:00+00:00', 'ACEPTADA', 'Orden recibida a tiempo'),
    ('T002', '2024-06-10T11:00:00+00:00', '2024-06-12T16:00:00+00:00', '2024-06-15T10:00:00+00:00', 'ACEPTADA', 'Pendiente de entrega');

INSERT INTO filtros_usuario (usuario_id, nombre_filtro, filtro, fecha_creacion)
VALUES
    ((SELECT id FROM usuarios WHERE nombre_usuario = 'juan'), 'Filtro Producto Rojo', '{"color": "Rojo"}', '2024-06-18T09:00:00+00:00'),
    ((SELECT id FROM usuarios WHERE nombre_usuario = 'ana'), 'Filtro Producto Azul', '{"color": "Azul"}', '2024-06-20T11:00:00+00:00');
