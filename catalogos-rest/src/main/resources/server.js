const express = require('express');
const path = require('path');
const fileUpload = require('express-fileupload');  // Para manejar la subida de archivos
const fs = require('fs');
const XLSX = require('xlsx');  // Para leer el archivo Excel
const Papa = require('papaparse'); // Para procesar el CSV

const app = express();
const port = 3000;

// Crear la carpeta de uploads si no existe
const uploadDir = path.join(__dirname, 'uploads');
if (!fs.existsSync(uploadDir)){
    fs.mkdirSync(uploadDir);
}

// Middleware para manejar archivos subidos
app.use(fileUpload());

// Rutas estáticas para archivos públicos (como el HTML y JS)
app.use(express.static(path.join(__dirname, 'public')));

// Ruta para cargar el archivo CSV
app.post('/upload', (req, res) => {
    // Verifica si el archivo fue subido
    if (!req.files || Object.keys(req.files).length === 0) {
        return res.status(400).send('No hay archivos subidos.');
    }

    let uploadedFile = req.files.csvFile;

    // Renombrar el archivo subido para evitar conflictos
    const uploadedFileName = Date.now() + '-' + uploadedFile.name;
    const filePath = path.join(uploadDir, uploadedFileName);

    // Guarda el archivo CSV temporalmente
    uploadedFile.mv(filePath, (err) => {
        if (err) {
            return res.status(500).send(err);
        }

        // Procesa el archivo con PapaParse
        Papa.parse(fs.createReadStream(filePath), {
            header: true,
            skipEmptyLines: true,
            complete: (result) => {
                console.log(result.data); // Imprimir datos para verificación
                res.json(result.data); // Enviar los datos procesados como respuesta
            },
            error: (error) => {
                console.error('Error al procesar CSV:', error);
                res.status(500).send('Error al procesar el archivo CSV.');
            }
        });
    });
});

// Ruta para cargar el archivo de la base de datos (excel)
app.get('/static/BD%20de%20Prueba.xlsx', (req, res) => {
    const filePath = path.join(__dirname, 'static', 'BD de Prueba.xlsx');
    res.sendFile(filePath);
});

// Iniciar el servidor
app.listen(port, () => {
    console.log(`Servidor corriendo en http://localhost:${port}`);
});
