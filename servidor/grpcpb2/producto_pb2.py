# -*- coding: utf-8 -*-
# Generated by the protocol buffer compiler.  DO NOT EDIT!
# NO CHECKED-IN PROTOBUF GENCODE
# source: producto.proto
# Protobuf Python Version: 5.27.2
"""Generated protocol buffer code."""
from google.protobuf import descriptor as _descriptor
from google.protobuf import descriptor_pool as _descriptor_pool
from google.protobuf import runtime_version as _runtime_version
from google.protobuf import symbol_database as _symbol_database
from google.protobuf.internal import builder as _builder
_runtime_version.ValidateProtobufRuntimeVersion(
    _runtime_version.Domain.PUBLIC,
    5,
    27,
    2,
    '',
    'producto.proto'
)
# @@protoc_insertion_point(imports)

_sym_db = _symbol_database.Default()


import empty_pb2 as empty__pb2


DESCRIPTOR = _descriptor_pool.Default().AddSerializedFile(b'\n\x0eproducto.proto\x1a\x0b\x65mpty.proto\"v\n\x08Producto\x12\n\n\x02id\x18\x01 \x01(\x03\x12\x0e\n\x06nombre\x18\x02 \x01(\t\x12\x0e\n\x06\x63odigo\x18\x03 \x01(\t\x12\r\n\x05talle\x18\x04 \x01(\t\x12\x0c\n\x04\x66oto\x18\x05 \x01(\t\x12\r\n\x05\x63olor\x18\x06 \x01(\t\x12\x12\n\ntienda_ids\x18\x07 \x03(\t\",\n\x0cProductoList\x12\x1c\n\tproductos\x18\x01 \x03(\x0b\x32\t.Producto2\xd6\x01\n\x0fProductoService\x12&\n\x0e\x43reateProducto\x12\t.Producto\x1a\t.Producto\x12#\n\x0bGetProducto\x12\t.Producto\x1a\t.Producto\x12&\n\x0eUpdateProducto\x12\t.Producto\x1a\t.Producto\x12&\n\x0e\x44\x65leteProducto\x12\t.Producto\x1a\t.Producto\x12&\n\rListProductos\x12\x06.Empty\x1a\r.ProductoListb\x06proto3')

_globals = globals()
_builder.BuildMessageAndEnumDescriptors(DESCRIPTOR, _globals)
_builder.BuildTopDescriptorsAndMessages(DESCRIPTOR, 'producto_pb2', _globals)
if not _descriptor._USE_C_DESCRIPTORS:
  DESCRIPTOR._loaded_options = None
  _globals['_PRODUCTO']._serialized_start=31
  _globals['_PRODUCTO']._serialized_end=149
  _globals['_PRODUCTOLIST']._serialized_start=151
  _globals['_PRODUCTOLIST']._serialized_end=195
  _globals['_PRODUCTOSERVICE']._serialized_start=198
  _globals['_PRODUCTOSERVICE']._serialized_end=412
# @@protoc_insertion_point(module_scope)