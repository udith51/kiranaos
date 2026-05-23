import { BrowserRouter, Route, Routes } from 'react-router-dom';
import ProtectedRoute from './components/ProtectedRoute';
import Login from '@/pages/Login';
import Register from '@/pages/Register';
import Dashboard from '@/pages/Dashboard';
import Billing from '@/pages/Billing';
import Inventory from '@/pages/Products';
import Bills from '@/pages/Bills';
// import StoreGuard from './components/StoreGuard';
import StoreSetup from './pages/StoreSetup';
import { Toaster } from './components/ui/sonner';
import AppLayout from './layout/AppLayout';
import LowStock from './pages/LowStock';
import Analytics from './pages/Analytics';

function App() {
  return (
    <div className="min-h-screen">
      <BrowserRouter>
        <Routes>
          <Route path="/login" element={<Login />} />
          <Route path="/register" element={<Register />} />

          <Route element={<ProtectedRoute />}>
            <Route path="/store/setup" element={<StoreSetup />} />
            {/* <Route element={<StoreGuard />}> */}
            <Route element={<AppLayout />}>
              <Route path="/" element={<Dashboard />} />
              <Route path="/billing" element={<Billing />} />
              <Route path="/bills" element={<Bills />} />
              <Route path="/inventory" element={<Inventory />} />
              <Route path="/inventory/low-stock" element={<LowStock />} />
              <Route path="/analytics" element={<Analytics />} />
            </Route>
            {/* </Route> */}
          </Route>
        </Routes>
      </BrowserRouter>
      <Toaster />
    </div>
  );
}

export default App;
