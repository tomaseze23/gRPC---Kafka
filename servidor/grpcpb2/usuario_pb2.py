# -*- coding: utf-8 -*-
# Generated by the protocol buffer compiler.  DO NOT EDIT!
# NO CHECKED-IN PROTOBUF GENCODE
# source: usuario.proto
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
    'usuario.proto'
)
# @@protoc_insertion_point(imports)

_sym_db = _symbol_database.Default()


from grpcpb2 import empty_pb2 as empty__pb2


DESCRIPTOR = _descriptor_pool.Default().AddSerializedFile(b'\n\rusuario.proto\x12\x07grpcpb2\x1a\x0b\x65mpty.proto\"\x89\x01\n\x07Usuario\x12\n\n\x02id\x18\x01 \x01(\x03\x12\x15\n\rnombreUsuario\x18\x02 \x01(\t\x12\x12\n\ncontrasena\x18\x03 \x01(\t\x12\x11\n\ttienda_id\x18\x04 \x01(\t\x12\x0e\n\x06nombre\x18\x05 \x01(\t\x12\x10\n\x08\x61pellido\x18\x06 \x01(\t\x12\x12\n\nhabilitado\x18\x07 \x01(\x08\"1\n\x0bUsuarioList\x12\"\n\x08usuarios\x18\x01 \x03(\x0b\x32\x10.grpcpb2.Usuario\"9\n\x0cLoginRequest\x12\x15\n\rnombreUsuario\x18\x01 \x01(\t\x12\x12\n\ncontrasena\x18\x02 \x01(\t\" \n\rLoginResponse\x12\x0f\n\x07success\x18\x01 \x01(\x08\x32\xcf\x02\n\x0eUsuarioService\x12\x33\n\rCreateUsuario\x12\x10.grpcpb2.Usuario\x1a\x10.grpcpb2.Usuario\x12\x30\n\nGetUsuario\x12\x10.grpcpb2.Usuario\x1a\x10.grpcpb2.Usuario\x12\x33\n\rUpdateUsuario\x12\x10.grpcpb2.Usuario\x1a\x10.grpcpb2.Usuario\x12\x33\n\rDeleteUsuario\x12\x10.grpcpb2.Usuario\x1a\x10.grpcpb2.Usuario\x12\x34\n\x0cListUsuarios\x12\x0e.grpcpb2.Empty\x1a\x14.grpcpb2.UsuarioList\x12\x36\n\x05Login\x12\x15.grpcpb2.LoginRequest\x1a\x16.grpcpb2.LoginResponseb\x06proto3')

_globals = globals()
_builder.BuildMessageAndEnumDescriptors(DESCRIPTOR, _globals)
_builder.BuildTopDescriptorsAndMessages(DESCRIPTOR, 'usuario_pb2', _globals)
if not _descriptor._USE_C_DESCRIPTORS:
  DESCRIPTOR._loaded_options = None
  _globals['_USUARIO']._serialized_start=40
  _globals['_USUARIO']._serialized_end=177
  _globals['_USUARIOLIST']._serialized_start=179
  _globals['_USUARIOLIST']._serialized_end=228
  _globals['_LOGINREQUEST']._serialized_start=230
  _globals['_LOGINREQUEST']._serialized_end=287
  _globals['_LOGINRESPONSE']._serialized_start=289
  _globals['_LOGINRESPONSE']._serialized_end=321
  _globals['_USUARIOSERVICE']._serialized_start=324
  _globals['_USUARIOSERVICE']._serialized_end=659
# @@protoc_insertion_point(module_scope)
