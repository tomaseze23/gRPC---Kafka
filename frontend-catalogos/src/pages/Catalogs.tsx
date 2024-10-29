import { Edit2, FileDown, Plus, Trash2 } from 'lucide-react';
import { useEffect, useState } from 'react';
import { CatalogForm } from '../components/CatalogForm';
import { useStore } from '../store/useStore';
import type { Catalog } from '../types';

export function Catalogs() {
  const catalogs = useStore((state) => state.catalogs);
  const catalogProducts = useStore((state) => state.catalogProducts);
  const loadCatalogs = useStore((state) => state.loadCatalogs);
  const loadCatalogProducts = useStore((state) => state.loadCatalogProducts);
  const deleteCatalog = useStore((state) => state.deleteCatalog);

  const [selectedCatalog, setSelectedCatalog] = useState<Catalog | null>(null);
  const [showCatalogForm, setShowCatalogForm] = useState(false);

  useEffect(() => {
    loadCatalogs();
  }, [loadCatalogs]);

  useEffect(() => {
    catalogs.forEach((catalog) => {
      loadCatalogProducts(catalog.id);
    });
  }, [catalogs, loadCatalogProducts]);

  const handleExportPDF = async (catalog: Catalog) => {
    console.log('Exporting catalog:', catalog.nombreCatalogo);
  };

  return (
    <div className="space-y-6">
      <div className="bg-white shadow rounded-lg p-6">
        <div className="flex justify-between items-center mb-6">
          <h2 className="text-lg font-medium">Product Catalogs</h2>
          <button
            className="bg-indigo-600 text-white px-4 py-2 rounded hover:bg-indigo-700 flex items-center"
            onClick={() => setShowCatalogForm(true)}
          >
            <Plus className="h-4 w-4 mr-2" />
            New Catalog
          </button>
        </div>

        <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
          {catalogs.map((catalog) => (
            <div key={catalog.id} className="bg-gray-50 rounded-lg p-4">
              <div className="flex justify-between items-start mb-4">
                <h3 className="text-lg font-medium">{catalog.nombreCatalogo}</h3>
                <div className="flex space-x-2">
                  <button
                    className="text-gray-600 hover:text-gray-800"
                    onClick={() => setSelectedCatalog(catalog)}
                  >
                    <Edit2 className="h-4 w-4" />
                  </button>
                  <button
                    className="text-gray-600 hover:text-gray-800"
                    onClick={() => handleExportPDF(catalog)}
                  >
                    <FileDown className="h-4 w-4" />
                  </button>
                  <button
                    className="text-red-600 hover:text-red-800"
                    onClick={() => deleteCatalog(catalog.id)}
                  >
                    <Trash2 className="h-4 w-4" />
                  </button>
                </div>
              </div>

              <div className="space-y-2">
                {catalogProducts[catalog.id]?.length > 0 ? (
                  catalogProducts[catalog.id].map((product) => (
                    <div key={product.id} className="bg-white p-3 rounded shadow-sm">
                      <div className="flex items-center space-x-3">
                        <img
                          src={product.foto}
                          alt={product.nombreProducto}
                          className="h-16 w-16 object-cover rounded"
                        />
                        <div>
                          <h4 className="font-medium">{product.nombreProducto}</h4>
                          <p className="text-sm text-gray-500">
                            Precio: ${product.precio.toFixed(2)}
                          </p>
                          <p className="text-sm text-gray-500">
                            Descripción: {product.descripcion}
                          </p>
                        </div>
                      </div>
                    </div>
                  ))
                ) : (
                  <p className="text-sm text-gray-500">No products available for this catalog.</p>
                )}
              </div>
            </div>
          ))}
        </div>
      </div>

      {(showCatalogForm || selectedCatalog) && (
        <CatalogForm
          catalog={selectedCatalog || undefined}
          onClose={() => {
            setShowCatalogForm(false);
            setSelectedCatalog(null);
          }}
        />
      )}
    </div>
  );
}
