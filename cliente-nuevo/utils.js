const grpc = require('@grpc/grpc-js');
const protoLoader = require('@grpc/proto-loader');
const path = require('path');
const config = require('./config/config');

/**
 * Carga un servicio gRPC desde un archivo de definici n de protocolo (.proto)
 * y devuelve una instancia de ese servicio.
 *
 * @param {string} protoFileName - nombre del archivo .proto que contiene el
 *      servicio.
 * @param {string} packageName - nombre del paquete en el que se encuentra el
 *      servicio.
 * @param {string} serviceName - nombre del servicio que se va a cargar.
 *
 * @throws {Error} si el servicio no se encuentra en el paquete.
 *
 * @returns {Object} una instancia del servicio.
 */
function initializeGrpcClient(protoFilename, protoPackage, clientService) {
    const PROTO_FILE_PATH = path.join(__dirname, 'proto', protoFilename);
    const protoOptions = protoLoader.loadSync(PROTO_FILE_PATH, {
        keepCase: true,
        longs: String,
        enums: String,
        defaults: true,
        oneofs: true
    });

    const grpcProto = grpc.loadPackageDefinition(protoOptions)[protoPackage];

    if (!grpcProto || !grpcProto[clientService]) {
        throw new Error(`No se encontró el servicio ${clientService} en el paquete ${protoPackage}`);
    }

    const { host: grpcHost, port: grpcPort } = config.grpcServer;
    return new grpcProto[clientService](`${grpcHost}:${grpcPort}`, grpc.credentials.createInsecure());
}

module.exports = initializeGrpcClient;