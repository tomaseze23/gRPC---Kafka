INSERT INTO usuarios (nombre_usuario, contrasena, tienda_id, nombre, apellido, habilitado)
VALUES 
    ('juan', '1', 'T001', 'Juan', 'Pérez', 1),
    ('ana', '2', 'T002', 'Ana', 'Gómez', 1);

INSERT INTO productos (nombre, codigo, talle, foto, color, cantidad_stock_proveedor)
VALUES 
    ('Producto A', 'A001', 'M', 'url/to/fotoA', 'Rojo', 0),
    ('Producto B', 'B002', 'L', 'url/to/fotoB', 'Azul', 150);

INSERT INTO tiendas (codigo, direccion, ciudad, provincia, habilitada)
VALUES 
    ('T001', 'Calle Falsa 123', 'Ciudad X', 'Provincia X', 1),
    ('T002', 'Calle Verdadera 456', 'Ciudad Y', 'Provincia Y', 1);

INSERT INTO producto_tienda (producto_id, tienda_id, color, talle, cantidad)
VALUES 
    ((SELECT id FROM productos WHERE codigo='A001'), 'T001', 'Rojo', 'M', 100),
    ((SELECT id FROM productos WHERE codigo='B002'), 'T002', 'Azul', 'L', 150);
