import React, { useEffect, useState } from 'react';
import { Box, Card, CardContent, Grid, TextField, MenuItem, Button, CircularProgress, Checkbox, FormControlLabel, Divider } from '@mui/material';
import { invoiceApi, masterApi } from '../../api';
import DataTable from '../../components/common/DataTable';
import PageHeader from '../../components/common/PageHeader';
import toast from 'react-hot-toast';

const empty = () => ({ iPurCode:'', entryDate: new Date().toISOString().split('T')[0], company:'', catName:'', invoiceNo:'', invoiceDate: new Date().toISOString().split('T')[0], supName:'', supAddress:'', supPhone:'', supEmail:'', invoiceAmount:0, supAdvance:0, invoiceDueDate:'', remarks:'' });

export const PurchaseInvoicePage = () => {
  const [rows, setRows] = useState<any[]>([]);
  const [form, setForm] = useState<any>(empty());
  const [companies, setCompanies] = useState<any[]>([]);
  const [suppliers, setSuppliers] = useState<any[]>([]);
  const [hasDueDate, setHasDueDate] = useState(false);
  const [loading, setLoading] = useState(true);
  const [saving, setSaving] = useState(false);

  const load = () => {
    Promise.all([invoiceApi.purchase.getAll(), masterApi.companies.getAll(), masterApi.suppliers.getAll(), invoiceApi.purchase.docNo()])
      .then(([inv, co, sup, dn]) => {
        setRows(inv.data.data);
        setCompanies(co.data.data);
        setSuppliers(sup.data.data);
        setForm((f: any) => ({ ...f, iPurCode: dn.data.data }));
      }).finally(() => setLoading(false));
  };

  useEffect(() => { load(); }, []);

  const set = (k: string, v: any) => setForm((f: any) => ({ ...f, [k]: v }));

  const handleSupplierChange = (supName: string) => {
    set('supName', supName);
    const s = suppliers.find((x: any) => x.supName === supName);
    if (s) { set('supAddress', s.supAddress || ''); set('supPhone', s.supPhone || ''); set('supEmail', s.supEmail || ''); }
  };

  const handleSave = async () => {
    if (!form.company || !form.catName || !form.invoiceNo || !form.supName || !form.invoiceAmount) { toast.error("Please Fill The Detail's!"); return; }
    setSaving(true);
    try {
      const payload = { ...form, invoiceDueDate: hasDueDate ? form.invoiceDueDate : null };
      await invoiceApi.purchase.save(payload);
      toast.success('Booking Added.');
      const dn = await invoiceApi.purchase.docNo();
      setForm({ ...empty(), iPurCode: dn.data.data });
      setHasDueDate(false);
      load();
    } catch (e: any) { toast.error(e.response?.data?.message || 'Failed.'); }
    finally { setSaving(false); }
  };

  const handleClear = async () => {
    const dn = await invoiceApi.purchase.docNo();
    setForm({ ...empty(), iPurCode: dn.data.data });
    setHasDueDate(false);
  };

  return (
    <Box>
      <PageHeader title="Purchase Invoice" subtitle="Book purchase bill entries" breadcrumbs={[{ label: 'Bill Entries' }, { label: 'Purchase Bill' }]} />
      <Grid container spacing={2.5}>
        <Grid item xs={12} lg={5}>
          <Card>
            <CardContent>
              <Grid container spacing={2}>
                <Grid item xs={6}><TextField fullWidth size="small" label="Doc No" value={form.iPurCode} InputProps={{ readOnly: true }} /></Grid>
                <Grid item xs={6}><TextField fullWidth size="small" label="Entry Date" value={form.entryDate} InputProps={{ readOnly: true }} /></Grid>
                <Grid item xs={12}><TextField select fullWidth size="small" label="Company *" value={form.company} onChange={e => set('company', e.target.value)}><MenuItem value="">Select</MenuItem>{companies.map((c:any) => <MenuItem key={c.id} value={c.compName}>{c.compName}</MenuItem>)}</TextField></Grid>
                <Grid item xs={12}><TextField fullWidth size="small" label="Purchase Category" value={form.catName} onChange={e => set('catName', e.target.value)} /></Grid>
                <Grid item xs={6}><TextField fullWidth size="small" label="Bill No *" value={form.invoiceNo} onChange={e => set('invoiceNo', e.target.value)} /></Grid>
                <Grid item xs={6}><TextField fullWidth size="small" label="Bill Date" type="date" value={form.invoiceDate} onChange={e => set('invoiceDate', e.target.value)} InputLabelProps={{ shrink: true }} /></Grid>
                <Grid item xs={12}><TextField select fullWidth size="small" label="Supplier Name *" value={form.supName} onChange={e => handleSupplierChange(e.target.value)}><MenuItem value="">Select</MenuItem>{suppliers.map((s:any) => <MenuItem key={s.id} value={s.supName}>{s.supName}</MenuItem>)}</TextField></Grid>
                <Grid item xs={12}><TextField fullWidth size="small" label="Supplier Address" value={form.supAddress} InputProps={{ readOnly: true }} /></Grid>
                <Grid item xs={6}><TextField fullWidth size="small" label="Supplier Phone" value={form.supPhone} InputProps={{ readOnly: true }} /></Grid>
                <Grid item xs={6}><TextField fullWidth size="small" label="Supplier Email" value={form.supEmail} InputProps={{ readOnly: true }} /></Grid>
                <Grid item xs={6}><TextField fullWidth size="small" label="Invoice Amount *" type="number" value={form.invoiceAmount} onChange={e => set('invoiceAmount', Number(e.target.value))} /></Grid>
                <Grid item xs={6}><TextField fullWidth size="small" label="Supplier Advance" type="number" value={form.supAdvance} onChange={e => set('supAdvance', Number(e.target.value))} /></Grid>
                <Grid item xs={12}><FormControlLabel control={<Checkbox checked={hasDueDate} onChange={e => setHasDueDate(e.target.checked)} />} label="Set Due Date" /></Grid>
                {hasDueDate && <Grid item xs={12}><TextField fullWidth size="small" label="Invoice Due Date" type="date" value={form.invoiceDueDate} onChange={e => set('invoiceDueDate', e.target.value)} InputLabelProps={{ shrink: true }} /></Grid>}
                <Grid item xs={12}><TextField fullWidth size="small" label="Remarks" multiline rows={2} value={form.remarks} onChange={e => set('remarks', e.target.value)} /></Grid>
                <Grid item xs={6}><Button fullWidth variant="outlined" onClick={handleClear}>Clear</Button></Grid>
                <Grid item xs={6}><Button fullWidth variant="contained" onClick={handleSave} disabled={saving}>{saving ? <CircularProgress size={20} /> : 'Save'}</Button></Grid>
              </Grid>
            </CardContent>
          </Card>
        </Grid>
        <Grid item xs={12} lg={7}>
          <Card>
            <CardContent>
              {loading ? <Box sx={{ textAlign: 'center', py: 4 }}><CircularProgress /></Box> : (
                <DataTable rows={rows} columns={[
                  { key: 'iPurCode', label: 'Doc No' }, { key: 'invoiceNo', label: 'Bill No' },
                  { key: 'invoiceDate', label: 'Bill Date' }, { key: 'company', label: 'Company' },
                  { key: 'supName', label: 'Supplier' },
                  { key: 'invoiceAmount', label: 'Amount', render: (v:any) => `₹${Number(v).toLocaleString('en-IN')}` },
                  { key: 'invoiceDueDate', label: 'Due Date' },
                ]} />
              )}
            </CardContent>
          </Card>
        </Grid>
      </Grid>
    </Box>
  );
};
