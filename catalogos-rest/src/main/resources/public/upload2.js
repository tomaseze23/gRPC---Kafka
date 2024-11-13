// Inicializar archivosProcesados desde el localStorage
let archivosProcesados = JSON.parse(localStorage.getItem('archivosProcesados')) || [];

// Mapeo de las columnas entre el archivo CSV y el archivo Excel
const columnasCSV = {
    "cantidad solicitada": "CANTIDAD SOLICITADA",
    "codigo de articulo": "CODIGO DE ARTICULO"
};

const columnasExcel = {
    "cantidad solicitada": "STOCK",  // Aquí "STOCK" es la columna donde se encuentra la cantidad en la BD de Excel
    "codigo de articulo": "CODIGO DE ARTICULO"
};

// Función para cargar la base de datos de productos desde un archivo Excel
function cargarBaseDeDatos() {
    return new Promise((resolve, reject) => {
        const fileInput = document.getElementById('fileInput');
        const file = fileInput.files[0];

        if (!file) {
            reject("No se ha seleccionado ningún archivo.");
            return;
        }

        const reader = new FileReader();
        reader.onload = function(e) {
            const data = e.target.result;
            // Usar SheetJS para leer el archivo Excel
            const workbook = XLSX.read(data, { type: 'binary' });

            const sheetName = workbook.SheetNames[0];
            const sheet = workbook.Sheets[sheetName];
            const productosStock = XLSX.utils.sheet_to_json(sheet, { header: 1 });

            const headers = productosStock[0];
            const productos = productosStock.slice(1).map(row => {
                const producto = {};
                headers.forEach((header, index) => {
                    producto[header] = row[index];
                });
                return producto;
            });

            resolve(productos);
        };

        reader.onerror = function(error) {
            reject("Error al leer el archivo Excel: " + error);
        };

        reader.readAsBinaryString(file); // Leer el archivo como una cadena binaria
    });
}

// Función para generar el hash del archivo
function generarHashArchivo(file, callback) {
    const reader = new FileReader();

    reader.onload = function(e) {
        const arrayBuffer = e.target.result;

        // Generar el hash con SHA-256
        crypto.subtle.digest('SHA-256', arrayBuffer)
            .then(hashBuffer => {
                const hashArray = Array.from(new Uint8Array(hashBuffer));
                const hashHex = hashArray.map(byte => byte.toString(16).padStart(2, '0')).join('');
                callback(hashHex);  // Llamar al callback con el hash generado
            })
            .catch(error => {
                console.error("Error al generar el hash:", error);
            });
    };

    reader.readAsArrayBuffer(file);  // Leer el archivo como ArrayBuffer
}

// Función para procesar el archivo CSV y verificar stock
document.getElementById('uploadForm').addEventListener('submit', (e) => {
    e.preventDefault();  // Prevenir el comportamiento por defecto del formulario

    const fileInput = document.getElementById('fileInput');
    const file = fileInput.files[0];

    if (!file) {
        alert("Por favor, selecciona un archivo CSV.");
        return;
    }

    if (!file.name.endsWith(".csv")) {
        alert("Por favor, selecciona un archivo CSV.");
        return;
    }

    // Generar el hash del archivo antes de procesarlo
    generarHashArchivo(file, (hash) => {
        // Verificar si el archivo ya ha sido procesado
        if (archivosProcesados.includes(hash)) {
            alert("Este archivo ya ha sido cargado anteriormente.");
            return;
        }

        // Cargar la base de datos de productos (debe estar definida previamente)
        cargarBaseDeDatos().then((productosStock) => {
            // Usar PapaParse para leer y procesar el archivo CSV
            Papa.parse(file, {
                header: true,  // Usar la primera fila como encabezado
                skipEmptyLines: true,
                delimiter: ",",  // Delimitador del CSV
                complete: function(results) {
                    const data = results.data;  // Datos extraídos del archivo CSV

                    console.log("Datos del archivo CSV cargado:", data);

                    if (data.length === 0) {
                        alert("No se encontraron datos en el archivo CSV.");
                        return;
                    }

                    // Aquí ya no se crea la tabla, solo verificamos el stock de los productos

                    data.forEach(row => {
                        const producto = row[columnasCSV["codigo de articulo"]];  // Usar el mapeo para la columna
                        const cantidadSolicitada = parseInt(row[columnasCSV["cantidad solicitada"]]);

                        if (!producto || isNaN(cantidadSolicitada)) {
                            console.log("Datos incompletos para la orden de compra.");
                            return;
                        }

                        // Buscar el producto en la base de datos
                        const productoEnStock = productosStock.find(p => p[columnasExcel["codigo de articulo"]] === producto);
                        
                        console.log(`Producto: ${producto}, Datos encontrados en base de datos:`, productoEnStock);

                        let mensajeStock = "";

                        if (productoEnStock) {
                            const cantidadEnStock = parseInt(productoEnStock[columnasExcel["cantidad solicitada"]]);  // También usando el mapeo para la columna de stock
                            console.log(`Cantidad en stock de ${producto}: ${cantidadEnStock}`);

                            if (cantidadEnStock >= cantidadSolicitada) {
                                mensajeStock = `Producto: ${producto} - Estado: En stock`;
                            } else {
                                mensajeStock = `Producto: ${producto} - Estado: No hay suficiente stock (Solo ${cantidadEnStock} disponibles)`;
                            }
                        } else {
                            mensajeStock = `Producto: ${producto} - Estado: No se encuentra en la base de datos`;
                        }

                        console.log(mensajeStock);  // Mostramos el mensaje en la consola
                    });

                    // Almacenar el archivo procesado con su hash
                    archivosProcesados.push(hash);
                    localStorage.setItem('archivosProcesados', JSON.stringify(archivosProcesados));
                },
                error: function(error) {
                    console.error('Error al procesar el archivo:', error);
                    alert("Ocurrió un error al intentar leer el archivo.");
                }
            });
        }).catch(error => {
            console.error(error);
            alert(error);
        });
    });
});
