// Función para mostrar/ocultar la tabla de productos
function toggleTabla() {
    const tabla = document.getElementById('tablaProductos');
    if (tabla.style.display === 'none' || tabla.style.display === '') {
        tabla.style.display = 'table'; // Mostrar la tabla
        document.getElementById('toggleTablaButton').innerText = 'Ocultar Tabla Productos';
    } else {
        tabla.style.display = 'none'; // Ocultar la tabla
        document.getElementById('toggleTablaButton').innerText = 'Mostrar Tabla Productos';
    }
}

// Función para mostrar el formulario de agregar producto
function mostrarFormulario() {
    document.getElementById('formulario').style.display = 'block';
}

// Función para cerrar el formulario de agregar producto
function cerrarFormulario() {
    document.getElementById('formulario').style.display = 'none';
}

// Función para agregar una tienda al formulario
function agregarTienda() {
    const tiendaSelect = document.getElementById('tienda');
    const nuevaTienda = prompt('Ingrese el nombre de la tienda:');

    if (nuevaTienda) {
        // Verificar si la tienda ya existe
        const tiendasExistentes = Array.from(tiendaSelect.options).map(option => option.value);
        if (tiendasExistentes.includes(nuevaTienda)) {
            alert('La tienda ya existe. Por favor, ingrese un nombre diferente.');
            return; // Salir de la función si la tienda ya existe
        }

        const option = document.createElement('option');
        option.value = nuevaTienda;
        option.textContent = nuevaTienda;
        tiendaSelect.appendChild(option);

        // Agregar tienda también al filtro de tiendas
        const filtroTienda = document.getElementById('filtroTienda');
        const filtroOption = document.createElement('option');
        filtroOption.value = nuevaTienda;
        filtroOption.textContent = nuevaTienda;
        filtroTienda.appendChild(filtroOption);
    }
}


// Función para seleccionar el color en el formulario
function seleccionarColor(colorHex, nombreColor) {
    document.getElementById('colorSeleccionado').style.backgroundColor = colorHex;
    document.querySelector('.select-selected').innerText = nombreColor;
    toggleDropdown(); // Cierra el desplegable de selección de color
}

// Función para alternar la visibilidad del desplegable de colores
function toggleDropdown() {
    const items = document.querySelector('.select-items');
    items.classList.toggle('select-hide');
}

// Función para actualizar el código del producto basado en el tipo de producto
function updateProductCode() {
    const productType = document.getElementById('tipoProducto').value;
    const codigoInput = document.getElementById('codigo');
    let codePrefix = '';

    // Definir el prefijo según el tipo de producto
    if (productType === 'Remera') {
        codePrefix = 'R';
        populateSizes(['XXS', 'XS', 'S', 'M', 'L', 'XL', 'XXL']);
    } else if (productType === 'Pantalon') {
        codePrefix = 'P';
        populateSizes(['XXS', 'XS', 'S', 'M', 'L', 'XL', 'XXL']);
    } else if (productType === 'Calzado') {
        codePrefix = 'C';
        populateSizes(['24', '25', '26', '27', '28', '29', '30', '31', '32', '33','34', '35', '36', '37', '38', '39', '40', '41', '42', '43']);
    } else {
        codePrefix = '';
        populateSizes([]);
    }

    const randomCode = Math.floor(Math.random() * 1000).toString().padStart(3, '0');
    codigoInput.value = codePrefix + randomCode;
}


function populateSizes(sizesArray) {
    const talleSelect = document.getElementById('talle');
    talleSelect.innerHTML = ''; // Limpiar opciones previas
    sizesArray.forEach(size => {
        const option = document.createElement('option');
        option.value = size;
        option.textContent = size;
        talleSelect.appendChild(option);
    });
}

document.getElementById('color').addEventListener('change', function() {
    const colorSeleccionado = document.getElementById('color').value;
    const displayColor = document.getElementById('colorSeleccionado');

    if (colorSeleccionado !== '0') {
        displayColor.style.backgroundColor = colorSeleccionado;
    } else {
        displayColor.style.backgroundColor = ''; // Restaurar si no hay selección
    }
});







function agregarProducto() {
    const codigo = document.getElementById('codigo').value;
    const producto = document.getElementById('producto').value;
    const tipoProducto = document.getElementById('tipoProducto').value;
    const talle = document.getElementById('talle').value;
    const cantidad = document.getElementById('cantidad').value;
    const precio = document.getElementById('precio').value;
    const tienda = document.getElementById('tienda').value;
    const imagen = document.getElementById('imagen').files[0];
    const colorSeleccionado = document.getElementById('color').value;

    // Verificación de campos
    if (producto && tipoProducto && talle && cantidad && precio && tienda && imagen && colorSeleccionado !== '0') {
        const tabla = document.getElementById('tablaProductos').querySelector('tbody');

        // Verificar si el código ya existe
        const codigosExistentes = Array.from(tabla.rows).map(row => row.cells[0].textContent);
        if (codigosExistentes.includes(codigo)) {
            alert('El código del producto ya existe. Por favor, ingrese un código diferente.');
            return; // Salir de la función si el código ya existe
        }

        const nuevaFila = tabla.insertRow();

        // Insertar celdas en la fila
        nuevaFila.insertCell(0).textContent = codigo; // Código generado
        nuevaFila.insertCell(1).textContent = producto;
        nuevaFila.insertCell(2).textContent = tipoProducto;
        nuevaFila.insertCell(3).textContent = talle;

        // Celdas editables para cantidad y precio
        const cantidadCell = nuevaFila.insertCell(4);
        cantidadCell.textContent = cantidad;
        cantidadCell.setAttribute('contenteditable', 'true'); // Hacer editable

        const precioCell = nuevaFila.insertCell(5);
        precioCell.textContent = `$${parseFloat(precio).toFixed(2)}`;
        precioCell.setAttribute('contenteditable', 'true'); // Hacer editable

        nuevaFila.insertCell(6).textContent = tienda;

        // Mostrar color en la tabla
        const colorCell = nuevaFila.insertCell(7);
        const colorDiv = document.createElement('div');
        colorDiv.style.width = '50px';
        colorDiv.style.height = '20px';
        colorDiv.style.backgroundColor = colorSeleccionado;
        colorCell.appendChild(colorDiv);

        // Mostrar imagen en la tabla
        const imgCell = nuevaFila.insertCell(8);
        const reader = new FileReader();
        reader.onload = function(e) {
            const img = document.createElement('img');
            img.src = e.target.result;
            img.alt = 'Imagen del producto';
            img.style.width = '100px';
            img.style.height = 'auto';
            imgCell.appendChild(img);
        };
        reader.readAsDataURL(imagen);

        // Botón de eliminar
        const accionesCell = nuevaFila.insertCell(9);
        accionesCell.innerHTML = `<button class="action-button" onclick="eliminarFila(this)">❌</button>`;
        
        // Limpiar campos del formulario
        document.getElementById('codigo').value = '';
        document.getElementById('producto').value = '';
        document.getElementById('tipoProducto').value = '';
        document.getElementById('talle').value = '';
        document.getElementById('cantidad').value = '';
        document.getElementById('precio').value = '';
        document.getElementById('tienda').value = '';
        document.getElementById('imagen').value = ''; 
        document.getElementById('color').value = '0';
    } else {
        alert('Por favor, complete todos los campos requeridos.');
    }
}

// Eliminar una fila de la tabla
function eliminarFila(boton) {
    boton.parentNode.parentNode.remove();
}

function editarFila(boton) {
    var fila = boton.parentNode.parentNode; // Obtener la fila que contiene el botón
    var codigo = fila.cells[0].textContent; // Obtener el código
    var producto = fila.cells[1].textContent; // Obtener el producto
    var tipoProducto = fila.cells[2].textContent; // Obtener el tipo de producto
    var talle = fila.cells[3].textContent; // Obtener el talle
    var cantidad = fila.cells[4].textContent; // Obtener la cantidad
    var precio = fila.cells[5].textContent.replace('$', '').replace(',', '.'); // Obtener el precio sin símbolo
    var tienda = fila.cells[6].textContent; // Obtener la tienda
    var color = fila.cells[7].querySelector('div').style.backgroundColor; // Obtener el color
    var imagen = fila.cells[8].querySelector('img').src; // Obtener la imagen

    // Asigna los valores a los campos del formulario
    document.getElementById('codigo').value = codigo; // Mantener el código para la edición
    document.getElementById('producto').value = producto;    
    document.getElementById('tipoProducto').value = tipoProducto;
    document.getElementById('talle').value = talle;
    document.getElementById('cantidad').value = cantidad; // Solo cantidad se puede editar
    document.getElementById('precio').value = precio; // Solo precio se puede editar
    document.getElementById('tienda').value = tienda;
    document.getElementById('color').value = color; // Mantener el color (si es necesario)
    
    // Eliminar la fila para evitar duplicados al agregar de nuevo
    fila.remove();
}


		
// Función para validar que los inputs de cantidad y precio no permitan valores cero
function validarCero(input) {
    if (input.value === '0' || input.value === '0.00') {
        input.value = '1';
    }
}


// Filtros

function aplicarFiltros() {
    const tipoProductoSeleccionado = document.getElementById('filtroTipoProducto').value.toLowerCase();
    const filtroTienda = document.getElementById('filtroTienda').value.toLowerCase();
    const precioMinimo = parseFloat(document.getElementById('precioMinimo').value) || 0;
    const precioMaximo = parseFloat(document.getElementById('precioMaximo').value) || Infinity;

    const tabla = document.getElementById('tablaProductos').getElementsByTagName('tbody')[0];
    const filas = tabla.getElementsByTagName('tr');

    for (const fila of filas) {
        const tipoProducto = fila.cells[2].textContent.toLowerCase();
        const tienda = fila.cells[6].textContent.toLowerCase();
        const precio = parseFloat(fila.cells[5].textContent.replace('$', '').replace(',', '.'));

        const cumpleTipoProducto = tipoProductoSeleccionado === "" || tipoProducto === tipoProductoSeleccionado;
        const cumpleTienda = filtroTienda === "" || tienda === filtroTienda;
        const cumpleRangoPrecio = precio >= precioMinimo && precio <= precioMaximo;

        // Solo muestra la fila si cumple todas las condiciones
        if (cumpleTipoProducto && cumpleTienda && cumpleRangoPrecio) {
            fila.style.display = "";
        } else {
            fila.style.display = "none";
        }
    }
}



/*
function filtrarPorTipoProducto() {
    const tipoProductoSeleccionado = document.getElementById('filtroTipoProducto').value.toLowerCase();
    const tabla = document.getElementById('tablaProductos').getElementsByTagName('tbody')[0];
    const filas = tabla.getElementsByTagName('tr');

    for (const fila of filas) {
        const tipoProducto = fila.cells[2].textContent.toLowerCase();

        // Mostrar u ocultar la fila según el filtro seleccionado
        if (tipoProductoSeleccionado === "" || tipoProducto === tipoProductoSeleccionado) {
            fila.style.display = "";
        } else {
            fila.style.display = "none";
        }
    }
}

function filtrarPorTienda() {
    const filtroTienda = document.getElementById('filtroTienda').value;
    const filas = document.querySelectorAll('#tablaProductos tbody tr');
    filas.forEach(fila => {
        const tienda = fila.cells[6].textContent;
        fila.style.display = (filtroTienda === '' || tienda === filtroTienda) ? '' : 'none';
    });
}

function filtrarPorPrecio() {
    const precioMinimo = parseFloat(document.getElementById('precioMinimo').value) || 0;
    const precioMaximo = parseFloat(document.getElementById('precioMaximo').value) || Infinity;
    const filas = document.querySelectorAll('#tablaProductos tbody tr');
    filas.forEach(fila => {
        const precio = parseFloat(fila.cells[5].textContent.replace('$', ''));
        fila.style.display = (precio >= precioMinimo && precio <= precioMaximo) ? '' : 'none';
    });
}

*/


// Archivo CSV
function descargarCSV() {
    const tabla = document.getElementById('tablaProductos').querySelector('tbody');
    let csvContent = "data:text/csv;charset=utf-8,";

    // Encabezados del CSV
    const encabezados = ["Código", "Producto", "Tipo", "Talle", "Cantidad", "Precio", "Tienda", "Color", "Imagen"];
    csvContent += encabezados.join(",") + "\r\n";

    // Recorrer cada fila de la tabla y agregar los datos al CSV
    Array.from(tabla.rows).forEach(row => {
        const celdas = Array.from(row.cells).map(cell => {
			
            if (cell.querySelector('img')) {
                return "imagen"; 
            }
            return cell.textContent.trim(); 
        });

        csvContent += celdas.join(",") + "\r\n"; // Agregar la fila al contenido CSV
    });

    // Crear un enlace para la descarga del CSV
	
    const link = document.createElement('a');
    link.setAttribute('href', encodeURI(csvContent));
    link.setAttribute('download', 'productos.csv');
    document.body.appendChild(link); 
    link.click();
    document.body.removeChild(link); 
}
