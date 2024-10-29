import { create } from "zustand";
import type { PurchaseOrder, SavedFilter, Catalog, Product } from "../types";

interface CatalogProduct {
  id: number;
  catalogoId: number;
  nombreProducto: string;
  precio: number;
  descripcion: string;
}

interface Store {
  purchaseOrders: PurchaseOrder[];
  savedFilters: SavedFilter[];
  catalogs: Catalog[];
  products: Product[];
  catalogProducts: { [catalogId: number]: CatalogProduct[] };
  user: { role: "STORE" | "CENTRAL"; storeCode: string } | null;
  setPurchaseOrders: (orders: PurchaseOrder[]) => void;
  setSavedFilters: (filters: SavedFilter[]) => void;
  setCatalogs: (catalogs: Catalog[]) => void;
  addCatalog: (
    catalog: Omit<Catalog, "id" | "fechaCreacion"> & { productIds: number[] }
  ) => Promise<void>;
  updateCatalog: (id: number, catalog: Partial<Catalog>) => Promise<void>;
  deleteCatalog: (id: number) => Promise<void>;
  loadCatalogs: () => Promise<void>;
  loadCatalogProducts: (catalogoId: number) => Promise<void>;
  loadProducts: () => Promise<void>;
}

export const useStore = create<Store>((set, get) => ({
  purchaseOrders: [],
  savedFilters: [],
  catalogs: [],
  products: [],
  catalogProducts: {}, // Guardará productos por cada catálogo
  user: { role: "STORE", storeCode: "STORE1" },

  setPurchaseOrders: (orders) => set({ purchaseOrders: orders }),
  setSavedFilters: (filters) => set({ savedFilters: filters }),
  setCatalogs: (catalogs) => set({ catalogs }),

  loadCatalogs: async () => {
    try {
      const response = await fetch("http://localhost:8081/api/catalogo");
      if (!response.ok) throw new Error("Error loading catalogs");
      const catalogs = await response.json();
      set({ catalogs });
    } catch (error) {
      console.error("Failed to load catalogs:", error);
    }
  },

  addCatalog: async (catalogData) => {
    try {
      // First, create the catalog
      const catalogResponse = await fetch(
        "http://localhost:8081/api/catalogo/crear",
        {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
          },
          body: JSON.stringify({
            id: 0,
            tiendaId: catalogData.tiendaId,
            nombreCatalogo: catalogData.nombreCatalogo,
            fechaCreacion: new Date().toISOString(),
          }),
        }
      );
  
      if (!catalogResponse.ok) throw new Error("Error adding catalog");
  
      const newCatalog = await catalogResponse.json();
  
      // Now, add each selected product to the new catalog
      let { productIds } = catalogData;
    // Check if productIds contains objects and extract IDs if necessary
    if (productIds.length > 0 && typeof productIds[0] === 'object') {
      productIds = productIds.map((product) => product.id);
    }
      // Access the products from the store
      const productsInStore = get().products;
  debugger
      // Get the full product data based on the IDs
      const selectedProducts = productsInStore.filter((product) =>
        productIds.includes(product.id)
      );
  
  
      // Use Promise.all to add products concurrently
      await Promise.all(
        selectedProducts.map(async (product) => {
          const productResponse = await fetch(
            "http://localhost:8081/api/catalogoproducto/crear",
            {
              method: "POST",
              headers: {
                "Content-Type": "application/json",
              },
              body: JSON.stringify({
                id: product.codigo,
                catalogoId: newCatalog,
                nombreProducto: product.nombre,
                precio: product.precio,
                descripcion: product.descripcion,
              }),
            }
          );
  
          if (!productResponse.ok)
            throw new Error("Error adding product to catalog");
        })
      );
  
      // Update the catalogs state
      set((state) => ({
        catalogs: [...state.catalogs, newCatalog],
      }));
    } catch (error) {
      console.error("Failed to add catalog:", error);
    }
  },

  updateCatalog: async (id, catalog) => {
    try {
      const response = await fetch(
        `http://localhost:8081/api/catalogo/actualizar`,
        {
          method: "PUT",
          headers: {
            "Content-Type": "application/json",
          },
          body: JSON.stringify({ id, ...catalog }),
        }
      );
      if (!response.ok) throw new Error("Error updating catalog");
      const updatedCatalog = await response.json();
      set((state) => ({
        catalogs: state.catalogs.map((c) => (c.id === id ? updatedCatalog : c)),
      }));
    } catch (error) {
      console.error("Failed to update catalog:", error);
    }
  },

  deleteCatalog: async (id) => {
    try {
      const response = await fetch(`http://localhost:8081/api/catalogo/${id}`, {
        method: "DELETE",
      });
      if (!response.ok) throw new Error("Error deleting catalog");
      set((state) => ({
        catalogs: state.catalogs.filter((c) => c.id !== id),
      }));
    } catch (error) {
      console.error("Failed to delete catalog:", error);
    }
  },

  loadCatalogProducts: async (catalogoId) => {
    try {
      // Fetch catalog products for the given catalog ID
      const response = await fetch(
        `http://localhost:8081/api/catalogoproducto/catalogo/${catalogoId}`
      );
      if (!response.ok) throw new Error("Error loading catalog products");
      const catalogProducts = await response.json();

      // Store products in catalogProducts object by catalogId
      set((state) => ({
        catalogProducts: {
          ...state.catalogProducts,
          [catalogoId]: catalogProducts,
        },
      }));
    } catch (error) {
      console.error("Failed to load catalog products:", error);
    }
  },
  loadProducts: async () => {
    try {
      const response = await fetch("http://localhost:8081/api/producto/todos");
      if (!response.ok) throw new Error("Error loading products");
      const products = await response.json();
      set({ products });
    } catch (error) {
      console.error("Failed to load products:", error);
    }
  },
}));
