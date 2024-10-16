import psycopg2
from psycopg2 import OperationalError, sql

def create_connection(db_name, db_user, db_password, db_host, db_port):
    """Establece una conexión con la base de datos PostgreSQL."""
    try:
        connection = psycopg2.connect(
            database=db_name,
            user=db_user,
            password=db_password,
            host=db_host,
            port=db_port,
        )
        print("Connection to PostgreSQL DB successful")
        return connection
    except OperationalError as e:
        print(f"The error '{e}' occurred")
        return None

def execute_sql_file(connection, sql_file_path):
    """Ejecuta el contenido de un archivo .sql en la base de datos."""
    try:
        with open(sql_file_path, 'r') as file:
            sql_script = file.read()
        
        with connection.cursor() as cursor:
            cursor.execute(sql.SQL(sql_script))
            connection.commit()
            print("SQL script executed successfully")
    except Exception as e:
        print(f"An error occurred: {e}")