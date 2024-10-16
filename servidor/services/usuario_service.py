from grpcpb2 import usuario_pb2
from grpcpb2 import usuario_pb2_grpc
from sqlalchemy.orm import Session
from models import Usuario

class UsuarioService(usuario_pb2_grpc.UsuarioServiceServicer):
    def __init__(self, db_session: Session):
        self.db_session = db_session

    def CreateUsuario(self, request, context):
        nuevo_usuario = Usuario(
            nombre_usuario=request.nombre_usuario,
            contrasena=request.contrasena,
            tienda_id=request.tienda_id,
            nombre=request.nombre,
            apellido=request.apellido,
            habilitado=request.habilitado
        )
        self.db_session.add(nuevo_usuario)
        self.db_session.commit()
        return request
    
    def GetUsuario(self, request, context):
        usuario = self.db_session.query(Usuario).filter_by(
            nombre_usuario=request.nombreUsuario,
            contrasena=request.contrasena
        ).first()
        
        if usuario:
            return usuario_pb2.Usuario(
                id=usuario.id, 
                nombreUsuario=usuario.nombre_usuario, 
                contrasena=usuario.contrasena, 
                tienda_id=usuario.tienda_id,
                nombre=usuario.nombre, 
                apellido=usuario.apellido, 
                habilitado=usuario.habilitado
            )
        else:
            return usuario_pb2.Usuario()
        
    def UpdateUsuario(self, request, context):
        usuario = self.db_session.query(Usuario).get(request.id)
        if usuario:
            usuario.nombre_usuario = request.nombreUsuario
            usuario.contrasena = request.contrasena
            usuario.tienda_id = request.tienda_id
            usuario.nombre = request.nombre
            usuario.apellido = request.apellido
            usuario.habilitado = request.habilitado
            self.db_session.commit()
            return request
        else:
            context.set_details("Usuario no encontrado.")
            return usuario_pb2.Usuario()

    def DeleteUsuario(self, request, context):
        usuario = self.db_session.query(Usuario).get(request.id)
        if usuario:
            self.db_session.delete(usuario)
            self.db_session.commit()
            return request
        else:
            context.set_details("Usuario no encontrado.")
            return usuario_pb2.Usuario()

    def ListUsuarios(self, request, context):
        usuarios = self.db_session.query(Usuario).all()
        usuarios_list = [
            usuario_pb2.Usuario(
                id=usuario.id, 
                nombreUsuario=usuario.nombre_usuario, 
                contrasena=usuario.contrasena, 
                tienda_id=usuario.tienda_id,
                nombre=usuario.nombre, 
                apellido=usuario.apellido, 
                habilitado=usuario.habilitado
            ) for usuario in usuarios
        ]
        return usuario_pb2.UsuarioList(usuarios=usuarios_list)
