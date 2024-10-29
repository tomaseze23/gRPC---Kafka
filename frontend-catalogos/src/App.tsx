import React from 'react';
import { BrowserRouter, Routes, Route, Navigate } from 'react-router-dom';
import { Layout } from './components/Layout';
import { OrdenesDeCompra } from './pages/OrdenesDeCompra';
import { Catalogos } from './pages/Catalogos';
import { UserUpload } from './pages/UserUpload';

function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<Layout />}>
          <Route index element={<Navigate to="/ordenes-de-compra" replace />} />
          <Route path="ordenes-de-compra" element={<OrdenesDeCompra />} />
          <Route path="catalogos" element={<Catalogos />} />
          <Route path="carga-masiva" element={<UserUpload />} />
        </Route>
      </Routes>
    </BrowserRouter>
  );
}

export default App;