import React, { useState } from 'react';
import { Upload, AlertCircle } from 'lucide-react';
import Papa from 'papaparse';

type UploadError = {
  line: number;
  message: string;
};

export function UserUpload() {
  const [errors, setErrors] = useState<UploadError[]>([]);
  const [isUploading, setIsUploading] = useState(false);

  const handleFileUpload = async (event: React.ChangeEvent<HTMLInputElement>) => {
    const file = event.target.files?.[0];
    if (!file) return;

    setIsUploading(true);
    setErrors([]);

    Papa.parse(file, {
      complete: async (results) => {
        const users = results.data.slice(1).map((row: any) => ({
          username: row[0],
          password: row[1],
          name: row[2],
          lastName: row[3],
          storeCode: row[4],
        }));

        try {
          const uploadErrors: UploadError[] = [];
          users.forEach((user: any, index: number) => {
            if (!user.username || !user.password || !user.name || !user.lastName || !user.storeCode) {
              uploadErrors.push({
                line: index + 2,
                message: 'All fields are required',
              });
            }
          });

          setErrors(uploadErrors);
        } catch (error) {
          console.error('Error uploading users:', error);
        } finally {
          setIsUploading(false);
        }
      },
      header: false,
    });
  };

  return (
    <div className="space-y-6">
      <div className="bg-white shadow rounded-lg p-6">
        <h2 className="text-lg font-medium mb-4">Bulk User Upload</h2>

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
              {isUploading ? 'Uploading...' : 'Click to upload CSV file'}
            </span>
          </label>
          <p className="mt-1 text-xs text-gray-500">
            Format: username;password;name;lastName;storeCode
          </p>
        </div>

        {errors.length > 0 && (
          <div className="mt-6">
            <h3 className="text-sm font-medium text-red-800 mb-2">Upload Errors</h3>
            <div className="bg-red-50 rounded-lg p-4">
              {errors.map((error, index) => (
                <div key={index} className="flex items-start space-x-2 text-sm text-red-700">
                  <AlertCircle className="h-4 w-4 mt-0.5 flex-shrink-0" />
                  <span>
                    Line {error.line}: {error.message}
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