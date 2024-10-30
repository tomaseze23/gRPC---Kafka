const initializeGrpcClient = require('./utils');

const greeterClient = initializeGrpcClient('helloworld.proto', 'helloworld', 'Greeter');
const usuarioClient = initializeGrpcClient('usuario.proto', 'usuario', 'UsuarioService');
const tiendaClient = initializeGrpcClient('tienda.proto', 'tienda', 'TiendaService');
const productoClient = initializeGrpcClient('producto.proto', 'producto', 'ProductoService');
const productoTiendaClient = initializeGrpcClient('producto_tienda.proto', 'producto_tienda', 'ProductoTiendaService');


module.exports = {
  greeterClient,
  usuarioClient,
  productoClient,
  tiendaClient,
  productoTiendaClient,
};
