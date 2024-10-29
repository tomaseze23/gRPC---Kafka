export type PurchaseOrder = {
  id: string;
  estado: 'ACEPTADA' | 'RECHAZADA' | 'SOLICITADA' | 'PAUSADA'; // Cambiado para coincidir con el componente
  tiendaId: string; // Identificador de la tienda
  fechaSolicitud: string; // Fecha de solicitud
  fechaEnvio?: string; // Campo opcional para la fecha de envío
  fechaRecepcion?: string; // Campo opcional para la fecha de recepción
  observaciones?: string; // Campo opcional para observaciones
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
