import { useAuthStore } from '@/store/AuthStore';
import { Navigate, Outlet } from 'react-router-dom';

function ProtectedRoute() {
  const { isAuthenticated } = useAuthStore();
  // TODO
  return (
    <>
      {isAuthenticated == true ? (
        <Outlet />
      ) : (
        <Navigate to="/login" replace={true} />
      )}
    </>
  );
}

export default ProtectedRoute;
