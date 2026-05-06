import { useAuthStore } from '@/store/AuthStore';
import { Navigate, Outlet } from 'react-router-dom';

function ProtectedRoute() {
  const { isAuthenticated } = useAuthStore();
  return <>{isAuthenticated ? <Outlet /> : <Navigate to="/login" />}</>;
}

export default ProtectedRoute;
