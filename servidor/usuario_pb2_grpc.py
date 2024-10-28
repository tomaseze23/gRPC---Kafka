# Generated by the gRPC Python protocol compiler plugin. DO NOT EDIT!
"""Client and server classes corresponding to protobuf-defined services."""
import grpc
import warnings

import empty_pb2 as empty__pb2
import usuario_pb2 as usuario__pb2

GRPC_GENERATED_VERSION = '1.66.1'
GRPC_VERSION = grpc.__version__
_version_not_supported = False

try:
    from grpc._utilities import first_version_is_lower
    _version_not_supported = first_version_is_lower(GRPC_VERSION, GRPC_GENERATED_VERSION)
except ImportError:
    _version_not_supported = True

if _version_not_supported:
    raise RuntimeError(
        f'The grpc package installed is at version {GRPC_VERSION},'
        + f' but the generated code in usuario_pb2_grpc.py depends on'
        + f' grpcio>={GRPC_GENERATED_VERSION}.'
        + f' Please upgrade your grpc module to grpcio>={GRPC_GENERATED_VERSION}'
        + f' or downgrade your generated code using grpcio-tools<={GRPC_VERSION}.'
    )


class UsuarioServiceStub(object):
    """Servicios para Usuario
    """

    def __init__(self, channel):
        """Constructor.

        Args:
            channel: A grpc.Channel.
        """
        self.CreateUsuario = channel.unary_unary(
                '/UsuarioService/CreateUsuario',
                request_serializer=usuario__pb2.Usuario.SerializeToString,
                response_deserializer=usuario__pb2.Usuario.FromString,
                _registered_method=True)
        self.GetUsuario = channel.unary_unary(
                '/UsuarioService/GetUsuario',
                request_serializer=usuario__pb2.Usuario.SerializeToString,
                response_deserializer=usuario__pb2.Usuario.FromString,
                _registered_method=True)
        self.UpdateUsuario = channel.unary_unary(
                '/UsuarioService/UpdateUsuario',
                request_serializer=usuario__pb2.Usuario.SerializeToString,
                response_deserializer=usuario__pb2.Usuario.FromString,
                _registered_method=True)
        self.DeleteUsuario = channel.unary_unary(
                '/UsuarioService/DeleteUsuario',
                request_serializer=usuario__pb2.Usuario.SerializeToString,
                response_deserializer=usuario__pb2.Usuario.FromString,
                _registered_method=True)
        self.ListUsuarios = channel.unary_unary(
                '/UsuarioService/ListUsuarios',
                request_serializer=empty__pb2.Empty.SerializeToString,
                response_deserializer=usuario__pb2.UsuarioList.FromString,
                _registered_method=True)
        self.Login = channel.unary_unary(
                '/UsuarioService/Login',
                request_serializer=usuario__pb2.LoginRequest.SerializeToString,
                response_deserializer=usuario__pb2.LoginResponse.FromString,
                _registered_method=True)


class UsuarioServiceServicer(object):
    """Servicios para Usuario
    """

    def CreateUsuario(self, request, context):
        """Missing associated documentation comment in .proto file."""
        context.set_code(grpc.StatusCode.UNIMPLEMENTED)
        context.set_details('Method not implemented!')
        raise NotImplementedError('Method not implemented!')

    def GetUsuario(self, request, context):
        """Missing associated documentation comment in .proto file."""
        context.set_code(grpc.StatusCode.UNIMPLEMENTED)
        context.set_details('Method not implemented!')
        raise NotImplementedError('Method not implemented!')

    def UpdateUsuario(self, request, context):
        """Missing associated documentation comment in .proto file."""
        context.set_code(grpc.StatusCode.UNIMPLEMENTED)
        context.set_details('Method not implemented!')
        raise NotImplementedError('Method not implemented!')

    def DeleteUsuario(self, request, context):
        """Missing associated documentation comment in .proto file."""
        context.set_code(grpc.StatusCode.UNIMPLEMENTED)
        context.set_details('Method not implemented!')
        raise NotImplementedError('Method not implemented!')

    def ListUsuarios(self, request, context):
        """Missing associated documentation comment in .proto file."""
        context.set_code(grpc.StatusCode.UNIMPLEMENTED)
        context.set_details('Method not implemented!')
        raise NotImplementedError('Method not implemented!')

    def Login(self, request, context):
        """Missing associated documentation comment in .proto file."""
        context.set_code(grpc.StatusCode.UNIMPLEMENTED)
        context.set_details('Method not implemented!')
        raise NotImplementedError('Method not implemented!')


def add_UsuarioServiceServicer_to_server(servicer, server):
    rpc_method_handlers = {
            'CreateUsuario': grpc.unary_unary_rpc_method_handler(
                    servicer.CreateUsuario,
                    request_deserializer=usuario__pb2.Usuario.FromString,
                    response_serializer=usuario__pb2.Usuario.SerializeToString,
            ),
            'GetUsuario': grpc.unary_unary_rpc_method_handler(
                    servicer.GetUsuario,
                    request_deserializer=usuario__pb2.Usuario.FromString,
                    response_serializer=usuario__pb2.Usuario.SerializeToString,
            ),
            'UpdateUsuario': grpc.unary_unary_rpc_method_handler(
                    servicer.UpdateUsuario,
                    request_deserializer=usuario__pb2.Usuario.FromString,
                    response_serializer=usuario__pb2.Usuario.SerializeToString,
            ),
            'DeleteUsuario': grpc.unary_unary_rpc_method_handler(
                    servicer.DeleteUsuario,
                    request_deserializer=usuario__pb2.Usuario.FromString,
                    response_serializer=usuario__pb2.Usuario.SerializeToString,
            ),
            'ListUsuarios': grpc.unary_unary_rpc_method_handler(
                    servicer.ListUsuarios,
                    request_deserializer=empty__pb2.Empty.FromString,
                    response_serializer=usuario__pb2.UsuarioList.SerializeToString,
            ),
            'Login': grpc.unary_unary_rpc_method_handler(
                    servicer.Login,
                    request_deserializer=usuario__pb2.LoginRequest.FromString,
                    response_serializer=usuario__pb2.LoginResponse.SerializeToString,
            ),
    }
    generic_handler = grpc.method_handlers_generic_handler(
            'UsuarioService', rpc_method_handlers)
    server.add_generic_rpc_handlers((generic_handler,))
    server.add_registered_method_handlers('UsuarioService', rpc_method_handlers)


 # This class is part of an EXPERIMENTAL API.
class UsuarioService(object):
    """Servicios para Usuario
    """

    @staticmethod
    def CreateUsuario(request,
            target,
            options=(),
            channel_credentials=None,
            call_credentials=None,
            insecure=False,
            compression=None,
            wait_for_ready=None,
            timeout=None,
            metadata=None):
        return grpc.experimental.unary_unary(
            request,
            target,
            '/UsuarioService/CreateUsuario',
            usuario__pb2.Usuario.SerializeToString,
            usuario__pb2.Usuario.FromString,
            options,
            channel_credentials,
            insecure,
            call_credentials,
            compression,
            wait_for_ready,
            timeout,
            metadata,
            _registered_method=True)

    @staticmethod
    def GetUsuario(request,
            target,
            options=(),
            channel_credentials=None,
            call_credentials=None,
            insecure=False,
            compression=None,
            wait_for_ready=None,
            timeout=None,
            metadata=None):
        return grpc.experimental.unary_unary(
            request,
            target,
            '/UsuarioService/GetUsuario',
            usuario__pb2.Usuario.SerializeToString,
            usuario__pb2.Usuario.FromString,
            options,
            channel_credentials,
            insecure,
            call_credentials,
            compression,
            wait_for_ready,
            timeout,
            metadata,
            _registered_method=True)

    @staticmethod
    def UpdateUsuario(request,
            target,
            options=(),
            channel_credentials=None,
            call_credentials=None,
            insecure=False,
            compression=None,
            wait_for_ready=None,
            timeout=None,
            metadata=None):
        return grpc.experimental.unary_unary(
            request,
            target,
            '/UsuarioService/UpdateUsuario',
            usuario__pb2.Usuario.SerializeToString,
            usuario__pb2.Usuario.FromString,
            options,
            channel_credentials,
            insecure,
            call_credentials,
            compression,
            wait_for_ready,
            timeout,
            metadata,
            _registered_method=True)

    @staticmethod
    def DeleteUsuario(request,
            target,
            options=(),
            channel_credentials=None,
            call_credentials=None,
            insecure=False,
            compression=None,
            wait_for_ready=None,
            timeout=None,
            metadata=None):
        return grpc.experimental.unary_unary(
            request,
            target,
            '/UsuarioService/DeleteUsuario',
            usuario__pb2.Usuario.SerializeToString,
            usuario__pb2.Usuario.FromString,
            options,
            channel_credentials,
            insecure,
            call_credentials,
            compression,
            wait_for_ready,
            timeout,
            metadata,
            _registered_method=True)

    @staticmethod
    def ListUsuarios(request,
            target,
            options=(),
            channel_credentials=None,
            call_credentials=None,
            insecure=False,
            compression=None,
            wait_for_ready=None,
            timeout=None,
            metadata=None):
        return grpc.experimental.unary_unary(
            request,
            target,
            '/UsuarioService/ListUsuarios',
            empty__pb2.Empty.SerializeToString,
            usuario__pb2.UsuarioList.FromString,
            options,
            channel_credentials,
            insecure,
            call_credentials,
            compression,
            wait_for_ready,
            timeout,
            metadata,
            _registered_method=True)

    @staticmethod
    def Login(request,
            target,
            options=(),
            channel_credentials=None,
            call_credentials=None,
            insecure=False,
            compression=None,
            wait_for_ready=None,
            timeout=None,
            metadata=None):
        return grpc.experimental.unary_unary(
            request,
            target,
            '/UsuarioService/Login',
            usuario__pb2.LoginRequest.SerializeToString,
            usuario__pb2.LoginResponse.FromString,
            options,
            channel_credentials,
            insecure,
            call_credentials,
            compression,
            wait_for_ready,
            timeout,
            metadata,
            _registered_method=True)