import React, { useEffect, useState } from 'react';
import { useStore } from '../store/useStore';
import { X } from 'lucide-react';

type CatalogFormProps = {
  onClose: () => void;
  catalog?: {
    id: number;
    nombreCatalogo: string;
    tiendaId: string;
  };
};

export function CatalogForm({ onClose, catalog }: CatalogFormProps) {
  const addCatalog = useStore((state) => state.addCatalog);
  const updateCatalog = useStore((state) => state.updateCatalog);
  const loadProducts = useStore((state) => state.loadProducts); // Load products action


  const [nombreCatalogo, setNombreCatalogo] = useState(catalog?.nombreCatalogo || '');
  const [selectedProducts, setSelectedProducts] = useState<number[]>([]);
  const [tiendas, setTiendas] = useState<{ codigo: string; direccion: string }[]>([]);
  const [productos, setProductos] = useState<{ id: number; nombre: string }[]>([]);
  const [selectedTienda, setSelectedTienda] = useState<string>(catalog?.tiendaId || '');

  useEffect(() => {
    // Fetch stores (tiendas)
    const fetchTiendas = async () => {
      try {
        const response = await fetch('http://localhost:8081/api/tienda');
        if (!response.ok) throw new Error('Error loading stores');
        const tiendasData = await response.json();
        setTiendas(tiendasData);
      } catch (error) {
        console.error('Failed to load stores:', error);
      }
    };

    // Fetch products (productos)
    const fetchProductos = async () => {
      try {
        const response = await fetch('http://localhost:8081/api/producto/todos');
        if (!response.ok) throw new Error('Error loading products');
        const productosData = await response.json();
        setProductos(productosData.map((p: any) => ({ id: p.id, nombre: p.nombre })));
      } catch (error) {
        console.error('Failed to load products:', error);
      }
    };

    fetchTiendas();
    fetchProductos();
    loadProducts(); // Load products into the store
  }, [loadProducts]);

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();

    const catalogData = {
      nombreCatalogo,
      tiendaId: selectedTienda,
      products: productos.filter((p) => selectedProducts.includes(p.id)),
    };

    if (catalog) {
      updateCatalog(catalog.id, catalogData);
    } else {
      addCatalog(catalogData);
    }
    onClose();
  };

  const toggleProduct = (productId: number) => {
    setSelectedProducts((prev) =>
      prev.includes(productId)
        ? prev.filter((id) => id !== productId)
        : [...prev, productId]
    );
  };

  return (
    <div className="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center">
      <div className="bg-white rounded-lg p-6 w-full max-w-md">
        <div className="flex justify-between items-center mb-4">
          <h3 className="text-lg font-medium">
            {catalog ? 'Edit Catalog' : 'New Catalog'}
          </h3>
          <button onClick={onClose}>
            <X className="h-5 w-5" />
          </button>
        </div>

        <form onSubmit={handleSubmit}>
          <div className="mb-4">
            <label className="block text-sm font-medium text-gray-700 mb-1">
              Catalog Name
            </label>
            <input
              type="text"
              value={nombreCatalogo}
              onChange={(e) => setNombreCatalogo(e.target.value)}
              className="w-full border rounded p-2"
              required
            />
          </div>

          <div className="mb-4">
            <label className="block text-sm font-medium text-gray-700 mb-1">
              Store
            </label>
            <select
              value={selectedTienda}
              onChange={(e) => setSelectedTienda(e.target.value)}
              className="w-full border rounded p-2"
              required
            >
              <option value="" disabled>Select a store</option>
              {tiendas.map((tienda) => (
                <option key={tienda.codigo} value={tienda.codigo}>
                  {tienda.codigo}
                </option>
              ))}
            </select>
          </div>

          <div className="mb-4">
            <label className="block text-sm font-medium text-gray-700 mb-1">
              Products
            </label>
            <div className="space-y-2 max-h-60 overflow-y-auto">
              {productos.map((product) => (
                <label
                  key={product.id}
                  className="flex items-center space-x-2 p-2 hover:bg-gray-50 rounded"
                >
                  <input
                    type="checkbox"
                    checked={selectedProducts.includes(product.id)}
                    onChange={() => toggleProduct(product.id)}
                    className="rounded text-indigo-600"
                  />
                  <span>{product.nombre}</span>
                </label>
              ))}
            </div>
          </div>

          <div className="flex justify-end space-x-2">
            <button
              type="button"
              onClick={onClose}
              className="px-4 py-2 text-sm font-medium text-gray-700 bg-gray-100 rounded hover:bg-gray-200"
            >
              Cancel
            </button>
            <button
              type="submit"
              className="px-4 py-2 text-sm font-medium text-white bg-indigo-600 rounded hover:bg-indigo-700"
            >
              {catalog ? 'Update' : 'Create'}
            </button>
          </div>
        </form>
      </div>
    </div>
  );
}
