from sqlalchemy import create_engine
from sqlalchemy.ext.declarative import declarative_base
from sqlalchemy.orm import sessionmaker

# Configuración del motor SQLAlchemy
DATABASE_URL = "postgresql+psycopg2://root:root@localhost:8001/stockearte_db"

# Crear el motor de conexión
engine = create_engine(DATABASE_URL)

# Crear una clase base para los modelos
Base = declarative_base()

# Crear una sesión para interactuar con la base de datos
SessionLocal = sessionmaker(autocommit=False, autoflush=False, bind=engine)

def get_db():
    """Generador de sesión para usar en las consultas."""
    db = SessionLocal()
    try:
        yield db
    finally:
        db.close()
