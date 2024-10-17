package com.example.demo;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.config.annotation.WsConfigurerAdapter;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;

@EnableWs
@Configuration
public class WebServiceConfig extends WsConfigurerAdapter {

    @Bean
    public ServletRegistrationBean<MessageDispatcherServlet> messageDispatcherServlet(
            ApplicationContext applicationContext) {
        MessageDispatcherServlet servlet = new MessageDispatcherServlet();
        servlet.setApplicationContext(applicationContext);
        servlet.setTransformWsdlLocations(true);
        return new ServletRegistrationBean<>(servlet, "/ws/*");
    }

    @Bean(name = "usuario")
    public DefaultWsdl11Definition defaultWsdl11DefinitionUsuarios(XsdSchema usuariosSchema) {
        DefaultWsdl11Definition definition = new DefaultWsdl11Definition();
        definition.setPortTypeName("UsuariosPort");
        definition.setLocationUri("/ws");
        definition.setTargetNamespace("http://example.com/usuarios");
        definition.setSchema(usuariosSchema);
        return definition;
    }

    @Bean
    public XsdSchema usuariosSchema() {
        return new SimpleXsdSchema(new ClassPathResource("usuario.xsd"));
    }

    @Bean(name = "tienda")
    public DefaultWsdl11Definition defaultWsdl11DefinitionTiendas(XsdSchema tiendasSchema) {
        DefaultWsdl11Definition definition = new DefaultWsdl11Definition();
        definition.setPortTypeName("TiendasPort");
        definition.setLocationUri("/ws");
        definition.setTargetNamespace("http://www.example.com/tiendas");
        definition.setSchema(tiendasSchema);
        return definition;
    }

    @Bean
    public XsdSchema tiendasSchema() {
        return new SimpleXsdSchema(new ClassPathResource("tienda.xsd"));
    }

    @Bean(name = "producto") // Agregar el bean para el WSDL de Producto
    public DefaultWsdl11Definition defaultWsdl11DefinitionProductos(XsdSchema productosSchema) {
        DefaultWsdl11Definition definition = new DefaultWsdl11Definition();
        definition.setPortTypeName("ProductosPort");
        definition.setLocationUri("/ws");
        definition.setTargetNamespace("http://www.example.com/productos");
        definition.setSchema(productosSchema);
        return definition;
    }

    @Bean
    public XsdSchema productosSchema() { // Agregar el bean para el esquema de Producto
        return new SimpleXsdSchema(new ClassPathResource("producto.xsd")); // Asegúrate de que producto.xsd exista en la ruta especificada
    }

    @Bean(name = "catalogo") // Agregar el bean para el WSDL de Catalogo
    public DefaultWsdl11Definition defaultWsdl11DefinitionCatalogo(XsdSchema catalogoSchema) {
        DefaultWsdl11Definition definition = new DefaultWsdl11Definition();
        definition.setPortTypeName("CatalogoPort");
        definition.setLocationUri("/ws");
        definition.setTargetNamespace("http://www.example.com/catalogo");
        definition.setSchema(catalogoSchema);
        return definition;
    }

    @Bean
    public XsdSchema catalogoSchema() { // Agregar el bean para el esquema de Catalogo
        return new SimpleXsdSchema(new ClassPathResource("catalogo.xsd")); // Asegúrate de que catalogo.xsd exista en la ruta especificada
    }

    @Bean(name = "catalogoProducto")
    public DefaultWsdl11Definition defaultWsdl11DefinitionCatalogoProducto(XsdSchema catalogoProductoSchema) {
        DefaultWsdl11Definition definition = new DefaultWsdl11Definition();
        definition.setPortTypeName("CatalogoProductoPort");
        definition.setLocationUri("/ws/catalogo_producto");
        definition.setTargetNamespace("http://www.example.com/catalogo_producto");
        definition.setSchema(catalogoProductoSchema);
        return definition;
    }

    @Bean
    public XsdSchema catalogoProductoSchema() {
        return new SimpleXsdSchema(new ClassPathResource("catalogo_producto.xsd")); // Asegúrate de que el nombre del archivo sea correcto
    }

    @Bean(name = "ordenCompra")
    public DefaultWsdl11Definition defaultWsdl11DefinitionOrdenCompra(XsdSchema ordenCompraSchema) {
        DefaultWsdl11Definition definition = new DefaultWsdl11Definition();
        definition.setPortTypeName("OrdenCompraPort");
        definition.setLocationUri("/ws/ordenesCompra");
        definition.setTargetNamespace("http://www.example.com/ordenes_compra");
        definition.setSchema(ordenCompraSchema);
        return definition;
    }

    @Bean
    public XsdSchema ordenCompraSchema() {
        return new SimpleXsdSchema(new ClassPathResource("ordenes_compra.xsd"));
    }

    @Bean(name = "filtroUsuario")
    public DefaultWsdl11Definition defaultWsdl11DefinitionFiltroUsuario(XsdSchema filtroUsuarioSchema) {
        DefaultWsdl11Definition definition = new DefaultWsdl11Definition();
        definition.setPortTypeName("FiltroUsuarioPort");
        definition.setLocationUri("/ws/filtroUsuario");
        definition.setTargetNamespace("http://www.example.com/filtroUsuario");
        definition.setSchema(filtroUsuarioSchema);
        return definition;
    }

    @Bean
    public XsdSchema filtroUsuarioSchema() {
        return new SimpleXsdSchema(new ClassPathResource("filtroUsuario.xsd"));
    }   

}
