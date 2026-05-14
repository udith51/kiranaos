import { useGetStore } from '@/hooks/useStore';
import { Navigate, Outlet } from 'react-router-dom';
import { Spinner } from './ui/spinner';

export default function StoreGuard() {
  const { data, isLoading, error } = useGetStore();

  if (isLoading) return <Spinner className="size-8" />;
  if (error) return <Navigate to="/store/setup" />;
  if (data) return <Outlet />;
}
