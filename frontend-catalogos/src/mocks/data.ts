import type { PurchaseOrder, Catalog, Product } from '../types';

export const mockProducts: Product[] = [
  {
    id: '1',
    name: 'Classic T-Shirt',
    colors: ['White', 'Black', 'Navy'],
    sizes: ['S', 'M', 'L', 'XL'],
    imageUrl: 'https://images.unsplash.com/photo-1521572163474-6864f9cf17ab?w=800&auto=format&fit=crop&q=60',
  },
  {
    id: '2',
    name: 'Slim Fit Jeans',
    colors: ['Blue', 'Black', 'Gray'],
    sizes: ['30x32', '32x32', '34x32', '36x32'],
    imageUrl: 'https://images.unsplash.com/photo-1542272604-787c3835535d?w=800&auto=format&fit=crop&q=60',
  },
  {
    id: '3',
    name: 'Running Shoes',
    colors: ['Black/White', 'Blue/Gray', 'Red/Black'],
    sizes: ['7', '8', '9', '10', '11'],
    imageUrl: 'https://images.unsplash.com/photo-1542291026-7eec264c27ff?w=800&auto=format&fit=crop&q=60',
  },
];

export const mockCatalogs: Catalog[] = [
  {
    id: '1',
    name: 'Summer Collection',
    storeCode: 'STORE1',
    products: [mockProducts[0], mockProducts[1]],
  },
  {
    id: '2',
    name: 'Sports Essentials',
    storeCode: 'STORE1',
    products: [mockProducts[2]],
  },
];

export const mockPurchaseOrders: PurchaseOrder[] = [
  {
    id: '1',
    productCode: 'TSH001',
    productName: 'Classic T-Shirt',
    quantity: 100,
    status: 'PENDING',
    storeCode: 'STORE1',
    date: '2024-03-15',
  },
  {
    id: '2',
    productCode: 'TSH001',
    productName: 'Classic T-Shirt',
    quantity: 50,
    status: 'APPROVED',
    storeCode: 'STORE2',
    date: '2024-03-14',
  },
  {
    id: '3',
    productCode: 'JNS001',
    productName: 'Slim Fit Jeans',
    quantity: 75,
    status: 'APPROVED',
    storeCode: 'STORE1',
    date: '2024-03-13',
  },
  {
    id: '4',
    productCode: 'SHO001',
    productName: 'Running Shoes',
    quantity: 30,
    status: 'REJECTED',
    storeCode: 'STORE1',
    date: '2024-03-12',
  },
];