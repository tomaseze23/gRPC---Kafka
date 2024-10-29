import React from 'react';
import { BrowserRouter, Routes, Route, Navigate } from 'react-router-dom';
import { Layout } from './components/Layout';
import { PurchaseOrders } from './pages/PurchaseOrders';
import { Catalogs } from './pages/Catalogs';
import { UserUpload } from './pages/UserUpload';

function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<Layout />}>
          <Route index element={<Navigate to="/purchase-orders" replace />} />
          <Route path="purchase-orders" element={<PurchaseOrders />} />
          <Route path="catalogs" element={<Catalogs />} />
          <Route path="carga-masiva" element={<UserUpload />} />
        </Route>
      </Routes>
    </BrowserRouter>
  );
}

export default App;