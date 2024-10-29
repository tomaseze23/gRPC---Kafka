export type PurchaseOrder = {
  id: string;
  productCode: string;
  productName: string;
  quantity: number;
  status: 'PENDING' | 'APPROVED' | 'REJECTED';
  storeCode: string;
  date: string;
};

export type SavedFilter = {
  id: string;
  name: string;
  productCode?: string;
  dateFrom?: string;
  dateTo?: string;
  status?: string;
  storeCode?: string;
};

export type Catalog = {
  id: number; 
  tiendaId: string; 
  nombreCatalogo: string; 
  fechaCreacion: string; 
};


export type Product = {
  id: number; 
  catalogoId: number; 
  nombreProducto: string; 
  precio: number; 
  descripcion: string; 
};
