const express = require('express');
const swaggerUi = require('swagger-ui-express');
const multer = require('multer');
const cors = require('cors');
const fs = require('fs');
const csv = require('csv-parser');
const { Kafka } = require('kafkajs');
const swaggerDocument = require('.swagger.json');
const { tiendasHabilitadas, kafkaConfig } = require('.config');

const app = express();
const upload = multer({ dest 'uploads' });
app.use(cors());  Habilita CORS

const kafka = new Kafka({ brokers kafkaConfig.brokers });
const producer = kafka.producer();

app.use('api-docs', swaggerUi.serve, swaggerUi.setup(swaggerDocument));

app.post('upload', upload.single('file'), async (req, res) = {
    if (!req.file) {
        return res.status(400).json({ error 'Por favor, sube un archivo CSV.' });
    }

    const ordenesPorTienda = {};
    const tiendasConOrden = new Set();

    fs.createReadStream(req.file.path)
        .pipe(csv(['tienda', 'codigo articulo', 'color', 'talle', 'cantidad solicitada']))
        .on('data', (row) = {
            const { tienda, 'codigo articulo' codigoArticulo, color, talle, 'cantidad solicitada' cantidadSolicitada } = row;

             Validar que la tienda esté habilitada y tenga el producto
            if (tiendasHabilitadas[tienda] && tiendasHabilitadas[tienda].includes(codigoArticulo)) {
                if (!ordenesPorTienda[tienda]) {
                    ordenesPorTienda[tienda] = [];
                }
                 Agregar producto a la orden de la tienda
                ordenesPorTienda[tienda].push({
                    codigoArticulo,
                    color,
                    talle,
                    cantidadSolicitada parseInt(cantidadSolicitada, 10)
                });
                tiendasConOrden.add(tienda);
            }
        })
        .on('end', async () = {
            await producer.connect();
            for (const tienda in ordenesPorTienda) {
                const orden = {
                    tienda,
                    productos ordenesPorTienda[tienda]
                };

                 Enviar cada orden al topic de Kafka correspondiente
                await producer.send({
                    topic kafkaConfig.topic,
                    messages [{ value JSON.stringify(orden) }]
                });
            }

            await producer.disconnect();
            fs.unlinkSync(req.file.path);
            res.status(200).json({
                message 'Órdenes de compra creadas correctamente.',
                tiendas Array.from(tiendasConOrden)
            });
        })
        .on('error', (error) = {
            console.error('Error al procesar el archivo CSV', error);
            res.status(500).json({ error 'Error al procesar el archivo CSV.' });
        });
});

const PORT = process.env.PORT  3000;
app.listen(PORT, () = {
    console.log(`Servidor ejecutándose en httplocalhost${PORT}api-docs`);
});
