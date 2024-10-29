import React, { useState } from 'react';
import { Upload, AlertCircle } from 'lucide-react';
import Papa from 'papaparse';

type UploadError = {
  line: number;
  message: string;
};

export function CargaMasiva() {
  const [errors, setErrors] = useState<UploadError[]>([]);
  const [isUploading, setIsUploading] = useState(false);

  const handleFileUpload = async (event: React.ChangeEvent<HTMLInputElement>) => {
    const file = event.target.files?.[0];
    if (!file) return;

    setIsUploading(true);
    setErrors([]);

    Papa.parse(file, {
      delimiter: ';',
      skipEmptyLines: true,
      complete: async (results) => {
        const users = results.data.slice(1).map((row: any) => ({
          nombreUsuario: row[0],
          contrasena: row[1],
          nombre: row[2],
          apellido: row[3],
          tiendaId: row[4],
          habilitado: true, // Asignamos `habilitado` como `true` por defecto
        }));

        try {
          const uploadErrors: UploadError[] = [];

          users.forEach((user: any, index: number) => {
            if (
              !user.nombreUsuario ||
              !user.contrasena ||
              !user.nombre ||
              !user.apellido ||
              !user.tiendaId
            ) {
              uploadErrors.push({
                line: index + 2,
                message: 'Todos los campos son obligatorios',
              });
            }
          });

          if (uploadErrors.length > 0) {
            setErrors(uploadErrors);
            setIsUploading(false);
            return;
          }

          const response = await fetch('http://localhost:8081/api/usuarios/carga-masiva', {
            method: 'POST',
            headers: {
              'Content-Type': 'application/json',
            },
            body: JSON.stringify(users),
          });

          const result = await response.json(); // Procesa el JSON de la respuesta

          if (result.errors && Array.isArray(result.errors)) {
            // Si hay errores en el contenido de la respuesta
            const serverErrors = result.errors.map((err: any) => ({
              line: err.line || 0, // Asegura que haya siempre un valor para 'line'
              message: err.message || 'Error desconocido',
            }));
            setErrors(serverErrors);
          } else {
            alert('¡Usuarios cargados exitosamente!');
          }
        } catch (error) {
          console.error('Error al cargar usuarios:', error);
          setErrors([
            {
              line: 0,
              message: 'Ocurrió un error inesperado. Inténtalo de nuevo.',
            },
          ]);
        } finally {
          setIsUploading(false);
          event.target.value = ''; // Resetea el input para permitir cargar el mismo archivo nuevamente
        }
      },
      header: false,
    });
  };

  return (
    <div className="space-y-6">
      <div className="bg-white shadow rounded-lg p-6">
        <h2 className="text-lg font-medium mb-4">Carga masiva de usuarios</h2>

        <div className="border-2 border-dashed border-gray-300 rounded-lg p-6 text-center">
          <input
            type="file"
            accept=".csv"
            onChange={handleFileUpload}
            className="hidden"
            id="file-upload"
          />
          <label
            htmlFor="file-upload"
            className="cursor-pointer inline-flex items-center justify-center space-x-2"
          >
            <Upload className="h-6 w-6 text-gray-400" />
            <span className="text-sm text-gray-600">
              {isUploading ? 'Cargando...' : 'Haga clic para cargar el archivo CSV'}
            </span>
          </label>
          <p className="mt-1 text-xs text-gray-500">
            Formato: nombreUsuario;contrasena;nombre;apellido;tiendaId
          </p>
        </div>

        {errors.length > 0 && (
          <div className="mt-6">
            <h3 className="text-sm font-medium text-red-800 mb-2">Errores de carga</h3>
            <div className="bg-red-50 rounded-lg p-4">
              {errors.map((error, index) => (
                <div key={index} className="flex items-start space-x-2 text-sm text-red-700">
                  <AlertCircle className="h-4 w-4 mt-0.5 flex-shrink-0" />
                  <span>
                    {error.line > 0 ? `Línea ${error.line}: ` : ''}
                    {error.message}
                  </span>
                </div>
              ))}
            </div>
          </div>
        )}
      </div>
    </div>
  );
}
