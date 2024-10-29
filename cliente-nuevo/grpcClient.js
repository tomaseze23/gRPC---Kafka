const loadGrpcService = require('./utils');

const greeterClient = loadGrpcService('helloworld.proto', 'helloworld', 'Greeter');
const usuarioClient = loadGrpcService('usuario.proto', 'usuario', 'UsuarioService');
const tiendaClient = loadGrpcService('tienda.proto', 'tienda', 'TiendaService');
const productoClient = loadGrpcService('producto.proto', 'producto', 'ProductoService');
const productoTiendaClient = loadGrpcService('producto_tienda.proto', 'producto_tienda', 'ProductoTiendaService');


module.exports = {
  greeterClient,
  usuarioClient,
  productoClient,
  tiendaClient,
  productoTiendaClient,
};
