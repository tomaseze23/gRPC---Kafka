create table if not exists tiendas (
  codigo text primary key,
  direccion text,
  ciudad text,
  provincia text,
  habilitada boolean default true
);

create table if not exists usuarios (
  id bigint primary key generated always as identity,
  nombre_usuario text unique not null,
  contrasena text not null,
  tienda_id text,
  nombre text,
  apellido text,
  habilitado boolean default true,
  foreign key (tienda_id) references tiendas (codigo) on delete set null
);

create table if not exists productos (
  id bigint primary key generated always as identity,
  nombre text not null,
  codigo text unique not null,
  talle text,
  foto text,
  color text,
  cantidad_stock_proveedor int default 0
);

create table if not exists producto_tienda (
  id bigint primary key generated always as identity,
  producto_id bigint not null,
  tienda_id text not null,
  color text,
  talle text,
  cantidad int default 0,
  foreign key (producto_id) references productos (id) on delete cascade,
  foreign key (tienda_id) references tiendas (codigo) on delete cascade
);

create table if not exists ordenes_compra (
  id bigint primary key generated always as identity,
  tienda_id text not null,
  fecha_solicitud timestamp with time zone default current_timestamp,
  fecha_envio timestamp with time zone,
  fecha_recepcion timestamp with time zone,
  estado text not null check (
    estado in ('SOLICITADA', 'RECHAZADA', 'ACEPTADA', 'PAUSADA')
  ),
  observaciones text,
  foreign key (tienda_id) references tiendas (codigo) on delete cascade
);

create table if not exists items_orden_compra (
  id bigint primary key generated always as identity,
  orden_compra_id bigint not null,
  producto_id bigint not null,
  color text,
  talle text,
  cantidad int check (cantidad > 0),
  foreign key (orden_compra_id) references ordenes_compra (id) on delete cascade,
  foreign key (producto_id) references productos (id) on delete cascade
);

create table if not exists ordenes_despacho (
  id bigint primary key generated always as identity,
  orden_compra_id bigint not null,
  fecha_estimacion_envio timestamp with time zone,
  estado text not null check (estado in ('PENDIENTE', 'ENVIADO', 'RECIBIDO')),
  foreign key (orden_compra_id) references ordenes_compra (id) on delete cascade
);

create table if not exists filtros_usuario (
  id bigint primary key generated always as identity,
  usuario_id bigint not null,
  nombre_filtro text not null,
  filtro json not null,
  fecha_creacion timestamp with time zone default current_timestamp,
  foreign key (usuario_id) references usuarios (id) on delete cascade
);

create table if not exists catalogos (
  id bigint primary key generated always as identity,
  tienda_id text not null,
  nombre_catalogo text not null,
  fecha_creacion timestamp with time zone default current_timestamp,
  foreign key (tienda_id) references tiendas (codigo) on delete cascade
);

create table if not exists catalogo_producto (
  id bigint primary key generated always as identity,
  catalogo_id bigint not null,
  producto_id bigint not null,
  foreign key (catalogo_id) references catalogos (id) on delete cascade,
  foreign key (producto_id) references productos (id) on delete cascade
);