import React from 'react';
import { BrowserRouter, Routes, Route, Navigate } from 'react-router-dom';
import { ThemeProvider, createTheme, CssBaseline } from '@mui/material';
import { Toaster } from 'react-hot-toast';
import { AuthProvider, useAuth } from './context/AuthContext';
import Layout from './components/layout/Layout';
import LoginPage from './pages/LoginPage';
import DashboardPage from './pages/DashboardPage';
import { MasterPage } from './pages/MasterPage';
import { PurchaseInvoicePage } from './pages/invoices/PurchaseInvoicePage';
import { ExportInvoicePage } from './pages/invoices/ExportInvoicePage';
import { RetailInvoicePage } from './pages/invoices/RetailInvoicePage';
import { DebitNotePage } from './pages/invoices/DebitNotePage';
import { ReceiptPage } from './pages/receipts/ReceiptPage';
import { RetailReceiptPage } from './pages/receipts/RetailReceiptPage';
import { ExportReceiptPage } from './pages/receipts/ExportReceiptPage';
import { IncentivesPage } from './pages/receipts/IncentivesPage';
import { PaymentPage } from './pages/PaymentPage';
import { ReportPage } from './pages/ReportPage';
import { UsersPage } from './pages/UsersPage';

const theme = createTheme({
  palette: {
    primary:    { main: '#1565C0' },
    secondary:  { main: '#0288D1' },
    background: { default: '#F0F4F8', paper: '#FFFFFF' },
  },
  typography: { fontFamily: '"Inter","Roboto","Helvetica Neue",sans-serif' },
  shape: { borderRadius: 10 },
  components: {
    MuiButton:    { styleOverrides: { root: { textTransform: 'none', fontWeight: 600 } } },
    MuiCard:      { styleOverrides: { root: { boxShadow: '0 2px 12px rgba(0,0,0,0.08)', borderRadius: 12 } } },
    MuiTableHead: { styleOverrides: { root: { '& .MuiTableCell-head': { backgroundColor: '#1565C0', color: '#fff', fontWeight: 700 } } } },
    MuiDrawer:    { styleOverrides: { paper: { backgroundColor: '#0D2E6B', color: '#fff' } } },
  },
});

const ProtectedRoute = ({ children }: { children: React.ReactNode }) => {
  const { isAuthenticated } = useAuth();
  return isAuthenticated ? <>{children}</> : <Navigate to="/login" replace />;
};

const AppRoutes = () => {
  const { isAuthenticated } = useAuth();
  return (
    <Routes>
      <Route path="/login" element={isAuthenticated ? <Navigate to="/" replace /> : <LoginPage />} />
      <Route path="/" element={<ProtectedRoute><Layout /></ProtectedRoute>}>
        <Route index element={<DashboardPage />} />
        <Route path="masters/:type"           element={<MasterPage />} />
        <Route path="invoices/purchase"       element={<PurchaseInvoicePage />} />
        <Route path="invoices/export"         element={<ExportInvoicePage />} />
        <Route path="invoices/retail"         element={<RetailInvoicePage />} />
        <Route path="invoices/debit-note"     element={<DebitNotePage />} />
        <Route path="receipts/general"        element={<ReceiptPage />} />
        <Route path="receipts/retail"         element={<RetailReceiptPage />} />
        <Route path="receipts/export"         element={<ExportReceiptPage />} />
        <Route path="receipts/incentives"     element={<IncentivesPage />} />
        <Route path="payments"                element={<PaymentPage />} />
        <Route path="reports/:type"           element={<ReportPage />} />
        <Route path="users"                   element={<UsersPage />} />
      </Route>
      <Route path="*" element={<Navigate to="/" replace />} />
    </Routes>
  );
};

export default function App() {
  return (
    <ThemeProvider theme={theme}>
      <CssBaseline />
      <Toaster position="top-right" toastOptions={{ duration: 3000 }} />
      <BrowserRouter>
        <AuthProvider>
          <AppRoutes />
        </AuthProvider>
      </BrowserRouter>
    </ThemeProvider>
  );
}
