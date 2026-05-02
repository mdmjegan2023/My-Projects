import React, { useState } from 'react';
import { Outlet, useNavigate, useLocation } from 'react-router-dom';
import {
  Box, Drawer, AppBar, Toolbar, Typography, IconButton, List, ListItem,
  ListItemButton, ListItemIcon, ListItemText, Collapse, Avatar, Chip,
  Divider, Tooltip, useMediaQuery, useTheme
} from '@mui/material';
import {
  Menu as MenuIcon, Dashboard, AccountBalance, Receipt, Payment,
  Assessment, People, ExpandLess, ExpandMore, Logout, Inventory,
  ShoppingCart, FileUpload, Store, Article, MoneyOff, CardGiftcard,
  Business, Category, Person, CurrencyRupee, LocalAtm, ReceiptLong,
  SwapHoriz, ChevronLeft
} from '@mui/icons-material';
import { useAuth } from '../../context/AuthContext';

const DRAWER_WIDTH = 265;

const navItems = [
  { label: 'Dashboard', icon: <Dashboard />, path: '/' },
  {
    label: 'Masters', icon: <Inventory />, children: [
      { label: 'Bank',          icon: <AccountBalance />,  path: '/masters/banks' },
      { label: 'Category',      icon: <Category />,        path: '/masters/categories' },
      { label: 'Client',        icon: <Person />,          path: '/masters/clients' },
      { label: 'Company',       icon: <Business />,        path: '/masters/companies' },
      { label: 'Currency',      icon: <CurrencyRupee />,   path: '/masters/currencies' },
      { label: 'Receipt Name',  icon: <ReceiptLong />,     path: '/masters/receipt-names' },
      { label: 'Receipt Type',  icon: <ReceiptLong />,     path: '/masters/receipt-types' },
      { label: 'Supplier',      icon: <Store />,           path: '/masters/suppliers' },
      { label: 'Transaction',   icon: <SwapHoriz />,       path: '/masters/transactions' },
    ]
  },
  {
    label: 'Bill Entries', icon: <ShoppingCart />, children: [
      { label: 'Purchase Bill',        icon: <ShoppingCart />, path: '/invoices/purchase' },
      { label: 'Export Sale Invoice',  icon: <FileUpload />,   path: '/invoices/export' },
      { label: 'Retail Sale Invoice',  icon: <Store />,        path: '/invoices/retail' },
      { label: 'Debit Note',           icon: <MoneyOff />,     path: '/invoices/debit-note' },
    ]
  },
  {
    label: 'Receipts', icon: <Receipt />, children: [
      { label: 'Receipt Entries',       icon: <Receipt />,      path: '/receipts/general' },
      { label: 'Retail Sale Receipt',   icon: <Store />,        path: '/receipts/retail' },
      { label: 'Export Sale Receipt',   icon: <FileUpload />,   path: '/receipts/export' },
      { label: 'Salexport Incentives',  icon: <CardGiftcard />, path: '/receipts/incentives' },
    ]
  },
  { label: 'Payments', icon: <Payment />, path: '/payments' },
  {
    label: 'Reports', icon: <Assessment />, children: [
      { label: 'Payments Report',        icon: <Article />, path: '/reports/payments' },
      { label: 'Receipts Report',        icon: <Article />, path: '/reports/receipts' },
      { label: 'Purchase Invoice',       icon: <Article />, path: '/reports/purchase-invoices' },
      { label: 'Due Date Crossed',       icon: <Article />, path: '/reports/due-date-crossed' },
      { label: 'Export Invoice',         icon: <Article />, path: '/reports/export-invoices' },
      { label: 'Retail Invoice',         icon: <Article />, path: '/reports/retail-invoices' },
      { label: 'Export Receipt',         icon: <Article />, path: '/reports/export-receipts' },
      { label: 'Retail Receipt',         icon: <Article />, path: '/reports/retail-receipts' },
      { label: 'Debit Notes',            icon: <Article />, path: '/reports/debit-notes' },
      { label: 'Incentives',             icon: <Article />, path: '/reports/incentives' },
      { label: 'Supplier Advance',       icon: <Article />, path: '/reports/supplier-advance' },
      { label: 'Supplier Credit',        icon: <Article />, path: '/reports/supplier-credit' },
      { label: 'Supplier Ledger',        icon: <Article />, path: '/reports/supplier-ledger' },
    ]
  },
  { label: 'User Registration', icon: <People />, path: '/users', adminOnly: true },
];

export default function Layout() {
  const { user, logout, hasRole } = useAuth();
  const navigate = useNavigate();
  const location = useLocation();
  const theme = useTheme();
  const isMobile = useMediaQuery(theme.breakpoints.down('md'));
  const [mobileOpen, setMobileOpen] = useState(false);
  const [open, setOpen] = useState<Record<string, boolean>>({});

  const toggle = (label: string) => setOpen(prev => ({ ...prev, [label]: !prev[label] }));
  const isActive = (path: string) => location.pathname === path;

  const drawer = (
    <Box sx={{ height: '100%', display: 'flex', flexDirection: 'column' }}>
      {/* Logo */}
      <Box sx={{ p: 2.5, display: 'flex', alignItems: 'center', gap: 1.5, borderBottom: '1px solid rgba(255,255,255,0.1)' }}>
        <Box sx={{ width: 38, height: 38, borderRadius: 2, bgcolor: '#1565C0', display: 'flex', alignItems: 'center', justifyContent: 'center' }}>
          <LocalAtm sx={{ color: '#fff', fontSize: 22 }} />
        </Box>
        <Box>
          <Typography variant="subtitle1" sx={{ color: '#fff', fontWeight: 800, lineHeight: 1 }}>FundTracker</Typography>
          <Typography variant="caption" sx={{ color: 'rgba(255,255,255,0.5)' }}>Enterprise</Typography>
        </Box>
      </Box>

      {/* Nav */}
      <List sx={{ flex: 1, overflow: 'auto', py: 1, px: 1 }}>
        {navItems.map(item => {
          if ((item as any).adminOnly && !hasRole('administrator')) return null;
          if (!item.children) {
            return (
              <ListItem key={item.label} disablePadding sx={{ mb: 0.3 }}>
                <ListItemButton
                  onClick={() => { navigate(item.path!); if (isMobile) setMobileOpen(false); }}
                  selected={isActive(item.path!)}
                  sx={{ borderRadius: 2, '&.Mui-selected': { bgcolor: 'rgba(255,255,255,0.15)', '&:hover': { bgcolor: 'rgba(255,255,255,0.2)' } }, '&:hover': { bgcolor: 'rgba(255,255,255,0.08)' }, color: '#fff' }}
                >
                  <ListItemIcon sx={{ color: isActive(item.path!) ? '#90CAF9' : 'rgba(255,255,255,0.6)', minWidth: 38 }}>{item.icon}</ListItemIcon>
                  <ListItemText primary={item.label} primaryTypographyProps={{ fontSize: 13.5, fontWeight: isActive(item.path!) ? 700 : 400 }} />
                </ListItemButton>
              </ListItem>
            );
          }
          return (
            <React.Fragment key={item.label}>
              <ListItem disablePadding sx={{ mb: 0.3 }}>
                <ListItemButton onClick={() => toggle(item.label)} sx={{ borderRadius: 2, '&:hover': { bgcolor: 'rgba(255,255,255,0.08)' }, color: '#fff' }}>
                  <ListItemIcon sx={{ color: 'rgba(255,255,255,0.6)', minWidth: 38 }}>{item.icon}</ListItemIcon>
                  <ListItemText primary={item.label} primaryTypographyProps={{ fontSize: 13.5 }} />
                  {open[item.label] ? <ExpandLess sx={{ color: 'rgba(255,255,255,0.5)', fontSize: 18 }} /> : <ExpandMore sx={{ color: 'rgba(255,255,255,0.5)', fontSize: 18 }} />}
                </ListItemButton>
              </ListItem>
              <Collapse in={open[item.label]} timeout="auto" unmountOnExit>
                <List disablePadding sx={{ pl: 1 }}>
                  {item.children!.map(child => (
                    <ListItem key={child.label} disablePadding sx={{ mb: 0.2 }}>
                      <ListItemButton
                        onClick={() => { navigate(child.path); if (isMobile) setMobileOpen(false); }}
                        selected={isActive(child.path)}
                        sx={{ borderRadius: 2, pl: 2, '&.Mui-selected': { bgcolor: 'rgba(255,255,255,0.15)' }, '&:hover': { bgcolor: 'rgba(255,255,255,0.06)' }, color: '#fff' }}
                      >
                        <ListItemIcon sx={{ color: isActive(child.path) ? '#90CAF9' : 'rgba(255,255,255,0.45)', minWidth: 32, '& svg': { fontSize: 17 } }}>{child.icon}</ListItemIcon>
                        <ListItemText primary={child.label} primaryTypographyProps={{ fontSize: 12.5, fontWeight: isActive(child.path) ? 600 : 400 }} />
                      </ListItemButton>
                    </ListItem>
                  ))}
                </List>
              </Collapse>
            </React.Fragment>
          );
        })}
      </List>

      {/* User info */}
      <Divider sx={{ borderColor: 'rgba(255,255,255,0.1)' }} />
      <Box sx={{ p: 2, display: 'flex', alignItems: 'center', gap: 1.5 }}>
        <Avatar sx={{ width: 34, height: 34, bgcolor: '#1565C0', fontSize: 14, fontWeight: 700 }}>
          {user?.username?.[0]?.toUpperCase()}
        </Avatar>
        <Box sx={{ flex: 1, overflow: 'hidden' }}>
          <Typography variant="body2" sx={{ color: '#fff', fontWeight: 600, lineHeight: 1.2, whiteSpace: 'nowrap', overflow: 'hidden', textOverflow: 'ellipsis' }}>{user?.username}</Typography>
          <Chip label={user?.userType} size="small" sx={{ height: 16, fontSize: 10, bgcolor: 'rgba(255,255,255,0.15)', color: '#90CAF9', mt: 0.3 }} />
        </Box>
        <Tooltip title="Logout">
          <IconButton onClick={logout} size="small" sx={{ color: 'rgba(255,255,255,0.6)', '&:hover': { color: '#ef5350' } }}>
            <Logout fontSize="small" />
          </IconButton>
        </Tooltip>
      </Box>
    </Box>
  );

  return (
    <Box sx={{ display: 'flex', minHeight: '100vh' }}>
      <AppBar position="fixed" elevation={0} sx={{ zIndex: theme.zIndex.drawer + 1, bgcolor: '#fff', borderBottom: '1px solid #e0e0e0' }}>
        <Toolbar sx={{ minHeight: '58px !important' }}>
          <IconButton edge="start" onClick={() => setMobileOpen(!mobileOpen)} sx={{ mr: 1, display: { md: 'none' }, color: '#1565C0' }}>
            <MenuIcon />
          </IconButton>
          <Typography variant="h6" sx={{ color: '#1565C0', fontWeight: 800, letterSpacing: -0.5 }}>FundTracker</Typography>
          <Typography variant="body2" sx={{ color: '#888', ml: 1, display: { xs: 'none', sm: 'block' } }}>Enterprise Edition</Typography>
          <Box sx={{ flex: 1 }} />
          <Chip label={`UID: ${user?.uid}`} size="small" variant="outlined" sx={{ mr: 1, display: { xs: 'none', sm: 'flex' } }} />
          <IconButton onClick={logout} sx={{ color: '#f44336' }}>
            <Logout />
          </IconButton>
        </Toolbar>
      </AppBar>

      {/* Mobile drawer */}
      <Drawer variant="temporary" open={mobileOpen} onClose={() => setMobileOpen(false)}
        ModalProps={{ keepMounted: true }}
        sx={{ display: { xs: 'block', md: 'none' }, '& .MuiDrawer-paper': { width: DRAWER_WIDTH, boxSizing: 'border-box' } }}>
        {drawer}
      </Drawer>

      {/* Desktop drawer */}
      <Drawer variant="permanent"
        sx={{ display: { xs: 'none', md: 'block' }, width: DRAWER_WIDTH, flexShrink: 0, '& .MuiDrawer-paper': { width: DRAWER_WIDTH, boxSizing: 'border-box', top: '58px', height: 'calc(100% - 58px)' } }}>
        {drawer}
      </Drawer>

      <Box component="main" sx={{ flexGrow: 1, p: 3, mt: '58px', ml: { md: `${DRAWER_WIDTH}px` }, minHeight: 'calc(100vh - 58px)', bgcolor: '#F0F4F8' }}>
        <Outlet />
      </Box>
    </Box>
  );
}
