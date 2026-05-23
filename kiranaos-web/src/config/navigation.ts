import {
  AlertTriangle,
  BarChart2,
  ClipboardList,
  LayoutDashboard,
  Package,
  Receipt,
} from 'lucide-react';

export interface NavItem {
  label: string;
  path: string;
  icon: React.ElementType;
}

export interface NavSection {
  label: string;
  items: NavItem[];
}

export const navSections: NavSection[] = [
  {
    label: 'Main',
    items: [
      { label: 'Dashboard', path: '/', icon: LayoutDashboard },
      { label: 'Billing', path: '/billing', icon: Receipt },
      { label: 'Bills', path: '/bills', icon: ClipboardList },
    ],
  },
  {
    label: 'Inventory',
    items: [
      { label: 'Products', path: '/inventory', icon: Package },
      {
        label: 'Low Stock',
        path: '/inventory/low-stock',
        icon: AlertTriangle,
      },
    ],
  },
  {
    label: 'Reports',
    items: [{ label: 'Analytics', path: '/analytics', icon: BarChart2 }],
  },
];
