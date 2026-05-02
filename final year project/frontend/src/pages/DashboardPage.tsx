import React, { useEffect, useState } from 'react';
import { Box, Grid, Card, CardContent, Typography, Table, TableBody, TableCell, TableContainer, TableHead, TableRow, Paper, Chip, CircularProgress, Select, MenuItem, FormControl, InputLabel, Divider, Alert } from '@mui/material';
import { Warning, TrendingUp, Receipt, Payment, ShoppingCart, Store, People, ErrorOutline } from '@mui/icons-material';
import { dashboardApi, masterApi } from '../api';
import { DashboardSummary, BankBalance, SupplierCreditOpenClose, PurchaseInvoice, BankMaster, SupplierMaster } from '../types';
import { useAuth } from '../context/AuthContext';

const StatCard = ({ title, value, icon, color }: any) => (
  <Card sx={{ height: '100%' }}>
    <CardContent sx={{ display: 'flex', alignItems: 'center', gap: 2 }}>
      <Box sx={{ width: 52, height: 52, borderRadius: 2.5, bgcolor: `${color}18`, display: 'flex', alignItems: 'center', justifyContent: 'center', flexShrink: 0 }}>
        {React.cloneElement(icon, { sx: { color, fontSize: 28 } })}
      </Box>
      <Box>
        <Typography variant="h4" fontWeight={800} color={color}>{value}</Typography>
        <Typography variant="body2" color="text.secondary">{title}</Typography>
      </Box>
    </CardContent>
  </Card>
);

export default function DashboardPage() {
  const { user } = useAuth();
  const [summary, setSummary] = useState<DashboardSummary | null>(null);
  const [banks, setBanks] = useState<BankMaster[]>([]);
  const [suppliers, setSuppliers] = useState<SupplierMaster[]>([]);
  const [selectedBank, setSelectedBank] = useState('');
  const [selectedSup, setSelectedSup] = useState('');
  const [bankBal, setBankBal] = useState<BankBalance | null>(null);
  const [supCredit, setSupCredit] = useState<SupplierCreditOpenClose | null>(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    Promise.all([
      dashboardApi.summary(),
      masterApi.banks.getAll(),
      masterApi.suppliers.getAll(),
    ]).then(([s, b, sp]) => {
      setSummary(s.data.data);
      setBanks(b.data.data);
      setSuppliers(sp.data.data);
    }).finally(() => setLoading(false));
  }, []);

  useEffect(() => {
    if (selectedBank) {
      dashboardApi.bankBalance(selectedBank).then(r => setBankBal(r.data.data));
    } else { setBankBal(null); }
  }, [selectedBank]);

  useEffect(() => {
    if (selectedSup) {
      dashboardApi.supplierCredit(selectedSup).then(r => setSupCredit(r.data.data));
    } else { setSupCredit(null); }
  }, [selectedSup]);

  if (loading) return <Box sx={{ display: 'flex', justifyContent: 'center', mt: 10 }}><CircularProgress /></Box>;

  const alerts = summary?.paymentAlerts || [];

  return (
    <Box>
      <Typography variant="h5" fontWeight={800} mb={0.5}>Dashboard</Typography>
      <Typography variant="body2" color="text.secondary" mb={3}>Welcome back, {user?.username} — {new Date().toLocaleDateString('en-IN', { weekday: 'long', year: 'numeric', month: 'long', day: 'numeric' })}</Typography>

      {/* Stats */}
      <Grid container spacing={2.5} mb={3}>
        {[
          { title: 'Total Suppliers',       value: summary?.totalSuppliers,        icon: <People />,       color: '#7B1FA2' },
          { title: 'Purchase Invoices',     value: summary?.totalPurchaseInvoices, icon: <ShoppingCart />, color: '#1565C0' },
          { title: 'Export Invoices',       value: summary?.totalExportInvoices,   icon: <TrendingUp />,   color: '#00897B' },
          { title: 'Retail Invoices',       value: summary?.totalRetailInvoices,   icon: <Store />,        color: '#F57C00' },
          { title: 'Total Payments',        value: summary?.totalPayments,         icon: <Payment />,      color: '#C62828' },
          { title: 'Total Receipts',        value: summary?.totalReceipts,         icon: <Receipt />,      color: '#2E7D32' },
          { title: 'Overdue Bills',         value: summary?.overdueBills,          icon: <ErrorOutline />, color: '#D32F2F' },
          { title: 'Due Today/Tomorrow',    value: alerts.length,                  icon: <Warning />,      color: '#E65100' },
        ].map(s => (
          <Grid item xs={12} sm={6} md={3} key={s.title}>
            <StatCard {...s} />
          </Grid>
        ))}
      </Grid>

      <Grid container spacing={2.5}>
        {/* Payment Alert Table */}
        <Grid item xs={12} md={8}>
          <Card>
            <CardContent sx={{ pb: 1 }}>
              <Box sx={{ display: 'flex', alignItems: 'center', gap: 1, mb: 2 }}>
                <Warning color="warning" />
                <Typography variant="h6" fontWeight={700}>Payment Alerts</Typography>
                <Chip label={`${alerts.length} due`} size="small" color={alerts.length > 0 ? 'warning' : 'default'} sx={{ ml: 'auto' }} />
              </Box>
              {alerts.length === 0
                ? <Alert severity="success">No payments due today or tomorrow.</Alert>
                : (
                  <TableContainer component={Paper} elevation={0} sx={{ border: '1px solid #e0e0e0', borderRadius: 2 }}>
                    <Table size="small">
                      <TableHead>
                        <TableRow>
                          {['Bill No','Bill Date','Company','Category','Supplier','Amount','Due Date'].map(h =>
                            <TableCell key={h}>{h}</TableCell>)}
                        </TableRow>
                      </TableHead>
                      <TableBody>
                        {alerts.map((a: PurchaseInvoice) => (
                          <TableRow key={a.id} hover>
                            <TableCell>{a.invoiceNo}</TableCell>
                            <TableCell>{a.invoiceDate}</TableCell>
                            <TableCell>{a.company}</TableCell>
                            <TableCell>{a.catName}</TableCell>
                            <TableCell>{a.supName}</TableCell>
                            <TableCell><Typography fontWeight={700} color="error.main">₹{Number(a.invoiceAmount).toLocaleString('en-IN')}</Typography></TableCell>
                            <TableCell><Chip label={a.invoiceDueDate} size="small" color="warning" /></TableCell>
                          </TableRow>
                        ))}
                      </TableBody>
                    </Table>
                  </TableContainer>
                )
              }
            </CardContent>
          </Card>
        </Grid>

        {/* Bank Balance & Supplier Credit */}
        <Grid item xs={12} md={4}>
          <Card sx={{ mb: 2.5 }}>
            <CardContent>
              <Typography variant="subtitle1" fontWeight={700} mb={2}>Bank Balance</Typography>
              <FormControl fullWidth size="small" sx={{ mb: 2 }}>
                <InputLabel>Select Account No</InputLabel>
                <Select value={selectedBank} label="Select Account No" onChange={e => setSelectedBank(e.target.value as string)}>
                  <MenuItem value="">Select</MenuItem>
                  {banks.map(b => <MenuItem key={b.id} value={b.accNo}>{b.accNo}</MenuItem>)}
                </Select>
              </FormControl>
              {bankBal ? (
                <Box sx={{ bgcolor: '#E3F2FD', borderRadius: 2, p: 2 }}>
                  <Typography variant="body2" color="text.secondary">Bank: <strong>{bankBal.bankName}</strong></Typography>
                  <Typography variant="body2" color="text.secondary">Type: <strong>{bankBal.accType}</strong></Typography>
                  <Typography variant="body2" color="text.secondary">Branch: <strong>{bankBal.bankBranch}</strong></Typography>
                  <Divider sx={{ my: 1 }} />
                  <Typography variant="h5" fontWeight={800} color="primary.main">₹{Number(bankBal.balance).toLocaleString('en-IN')}</Typography>
                </Box>
              ) : <Typography variant="body2" color="text.secondary" textAlign="center" py={2}>Select an account to view balance</Typography>}
            </CardContent>
          </Card>

          <Card>
            <CardContent>
              <Typography variant="subtitle1" fontWeight={700} mb={2}>Supplier Credit</Typography>
              <FormControl fullWidth size="small" sx={{ mb: 2 }}>
                <InputLabel>Select Supplier</InputLabel>
                <Select value={selectedSup} label="Select Supplier" onChange={e => setSelectedSup(e.target.value as string)}>
                  <MenuItem value="">Select</MenuItem>
                  {suppliers.map(s => <MenuItem key={s.id} value={s.supName}>{s.supName}</MenuItem>)}
                </Select>
              </FormControl>
              {supCredit ? (
                <Box sx={{ bgcolor: '#FFF3E0', borderRadius: 2, p: 2 }}>
                  <Typography variant="body2" color="text.secondary">Phone: <strong>{supCredit.supPhone || '-'}</strong></Typography>
                  <Typography variant="body2" color="text.secondary">Address: <strong>{supCredit.supAddress || '-'}</strong></Typography>
                  <Divider sx={{ my: 1 }} />
                  <Typography variant="h5" fontWeight={800} color="warning.dark">₹{Number(supCredit.supCredit).toLocaleString('en-IN')}</Typography>
                </Box>
              ) : <Typography variant="body2" color="text.secondary" textAlign="center" py={2}>Select a supplier to view credit</Typography>}
            </CardContent>
          </Card>
        </Grid>
      </Grid>
    </Box>
  );
}
