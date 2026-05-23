import { Button } from '@/components/ui/button';
import { navSections } from '@/config/navigation';
import { Plus } from 'lucide-react';
import { useLocation, useNavigate } from 'react-router-dom';

export default function Topbar() {
  const location = useLocation();
  const navigate = useNavigate();
  const currentPage =
    navSections
      .flatMap((s) => s.items)
      .find((item) => item.path === location.pathname)?.label ?? 'Dashboard';

  const showNewBill =
    location.pathname !== '/analytics' &&
    location.pathname !== '/inventory/low-stock';

  return (
    <div className="h-14 px-6 flex items-center justify-between border-b border-white/10 bg-background sticky top-0 z-10">
      <span>{currentPage}</span>
      {showNewBill && (
        <Button onClick={() => navigate('/billing')}>
          <Plus />
          New Bill
        </Button>
      )}
    </div>
  );
}
