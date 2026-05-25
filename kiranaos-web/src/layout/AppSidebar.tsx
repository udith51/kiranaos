import { Button } from '@/components/ui/button';
import {
  DropdownMenu,
  DropdownMenuContent,
  DropdownMenuGroup,
  DropdownMenuItem,
  DropdownMenuTrigger,
} from '@/components/ui/dropdown-menu';
import {
  Sidebar,
  SidebarContent,
  SidebarFooter,
  SidebarGroup,
  SidebarGroupLabel,
  SidebarHeader,
  SidebarMenu,
  SidebarMenuButton,
  SidebarMenuItem,
} from '@/components/ui/sidebar';
import { useGetStore } from '@/hooks/useStore';
import { useAuthStore } from '@/store/AuthStore';
import { LogOut, Settings, Store } from 'lucide-react';
import { NavLink, useLocation, useNavigate } from 'react-router-dom';
import { navSections } from '@/config/navigation';
import { useLogout } from '@/hooks/useAuth';

export default function AppSidebar() {
  const { data: store } = useGetStore();
  const navigate = useNavigate();
  const location = useLocation();
  const { mutate: logout } = useLogout(() => {
    navigate('/login', { replace: true });
  });
  const { refreshToken } = useAuthStore();
  const handleLogout = () => {
    if (!refreshToken) {
      return;
    }

    logout({ refreshToken });
  };
  return (
    <Sidebar>
      <SidebarHeader>
        <div className="flex items-center gap-2 px-2 py-1">
          <Store className="text-emerald-600" size={20} />
          <span className="font-semibold text-base">KiranaOS</span>
        </div>
      </SidebarHeader>
      <SidebarContent>
        {navSections.map((section) => (
          <SidebarGroup key={section.label}>
            <SidebarGroupLabel>{section.label}</SidebarGroupLabel>
            <SidebarMenu>
              {section.items.map((item) => (
                <SidebarMenuItem key={item.path}>
                  <SidebarMenuButton
                    asChild
                    isActive={location.pathname === item.path}
                  >
                    <NavLink to={item.path}>
                      <item.icon size={16} />
                      <span>{item.label}</span>
                    </NavLink>
                  </SidebarMenuButton>
                </SidebarMenuItem>
              ))}
            </SidebarMenu>
          </SidebarGroup>
        ))}
      </SidebarContent>
      <SidebarFooter>
        <DropdownMenu>
          <DropdownMenuTrigger asChild>
            <Button
              variant="ghost"
              className="w-full h-fit justify-start gap-2 py-1 border border-white/10 hover:border-white/20"
            >
              <div className="w-8 h-8 rounded-full bg-emerald-100 flex items-center justify-center text-xs font-medium text-emerald-700">
                {store?.name?.charAt(0).toUpperCase() || 'G'}
              </div>
              <span className="text-sm">{store?.name || 'General Store'}</span>
            </Button>
          </DropdownMenuTrigger>
          <DropdownMenuContent className="w-60" align="start">
            <DropdownMenuGroup>
              <DropdownMenuItem onClick={() => navigate('/store/setup')}>
                <Settings /> Store
              </DropdownMenuItem>
              <DropdownMenuItem onClick={handleLogout}>
                <LogOut />
                Logout
              </DropdownMenuItem>
            </DropdownMenuGroup>
          </DropdownMenuContent>
        </DropdownMenu>
      </SidebarFooter>
    </Sidebar>
  );
}
