-- 1. Inserta primero las tiendas
INSERT INTO tiendas (codigo, direccion, ciudad, provincia, habilitada)
VALUES 
    ('T001', 'Calle Falsa 123', 'Ciudad X', 'Provincia X', true),
    ('T002', 'Calle Verdadera 456', 'Ciudad Y', 'Provincia Y', true);

-- 2. Inserta los productos
INSERT INTO productos (nombre, codigo, talle, foto, color, cantidad_stock_proveedor)
VALUES 
    ('Producto A', 'A001', 'M', 'url/to/fotoA', 'Rojo', 0),
    ('Producto B', 'B002', 'L', 'url/to/fotoB', 'Azul', 150);

-- 3. Inserta en producto_tienda
INSERT INTO producto_tienda (producto_id, tienda_id, color, talle, cantidad)
VALUES 
    ((SELECT id FROM productos WHERE codigo='A001'), 'T001', 'Rojo', 'M', 100),
    ((SELECT id FROM productos WHERE codigo='B002'), 'T002', 'Azul', 'L', 150);

-- 4. Finalmente, inserta en usuarios
INSERT INTO usuarios (nombre_usuario, contrasena, tienda_id, nombre, apellido, habilitado)
VALUES 
    ('juan', '1', 'T001', 'Juan', 'Pérez', true),
    ('ana', '2', 'T002', 'Ana', 'Gómez', true);
