import React, { useEffect, useState } from 'react';
import { Box, Card, CardContent, Grid, TextField, MenuItem, Button, CircularProgress } from '@mui/material';
import { invoiceApi, masterApi } from '../../api';
import DataTable from '../../components/common/DataTable';
import PageHeader from '../../components/common/PageHeader';
import toast from 'react-hot-toast';

const empty = () => ({ iDbtCode:'', entryDate: new Date().toISOString().split('T')[0], supName:'', invoiceNo:'', company:'', catName:'', billInvoiceAmount:0, supDebit:0, invoiceAmount:0, remarks:'' });

export const DebitNotePage = () => {
  const [rows, setRows] = useState<any[]>([]);
  const [form, setForm] = useState<any>(empty());
  const [suppliers, setSuppliers] = useState<any[]>([]);
  const [invoiceNos, setInvoiceNos] = useState<string[]>([]);
  const [loading, setLoading] = useState(true);
  const [saving, setSaving] = useState(false);

  const load = () => {
    Promise.all([invoiceApi.debitNote.getAll(), masterApi.suppliers.getAll(), invoiceApi.debitNote.docNo()])
      .then(([dn, sup, docno]) => { setRows(dn.data.data); setSuppliers(sup.data.data); setForm((f:any) => ({...f, iDbtCode: docno.data.data})); })
      .finally(() => setLoading(false));
  };
  useEffect(() => { load(); }, []);
  const set = (k: string, v: any) => setForm((f: any) => ({ ...f, [k]: v }));

  const handleSupChange = async (supName: string) => {
    set('supName', supName);
    const sup = suppliers.find((s:any) => s.supName === supName);
    if (sup) { set('supAddress', sup.supAddress); set('supPhone', sup.supPhone); set('supEmail', sup.supEmail); }
    const res = await invoiceApi.debitNote.invoiceNosBySupplier(supName);
    setInvoiceNos(res.data.data);
  };

  const handleSave = async () => {
    if (!form.supName || !form.invoiceNo || !form.supDebit) { toast.error("Please Fill The Detail's!"); return; }
    setSaving(true);
    try {
      await invoiceApi.debitNote.save(form);
      toast.success('Debit Note Added.');
      const dn = await invoiceApi.debitNote.docNo();
      setForm({ ...empty(), iDbtCode: dn.data.data });
      load();
    } catch (e: any) { toast.error(e.response?.data?.message || 'Failed.'); }
    finally { setSaving(false); }
  };

  return (
    <Box>
      <PageHeader title="Debit Note" breadcrumbs={[{ label: 'Bill Entries' }, { label: 'Debit Note' }]} />
      <Grid container spacing={2.5}>
        <Grid item xs={12} lg={5}>
          <Card><CardContent>
            <Grid container spacing={2}>
              <Grid item xs={6}><TextField fullWidth size="small" label="Doc No" value={form.iDbtCode} InputProps={{ readOnly: true }} /></Grid>
              <Grid item xs={6}><TextField fullWidth size="small" label="Entry Date" value={form.entryDate} InputProps={{ readOnly: true }} /></Grid>
              <Grid item xs={12}><TextField select fullWidth size="small" label="Supplier Name *" value={form.supName} onChange={e => handleSupChange(e.target.value)}><MenuItem value="">Select</MenuItem>{suppliers.map((s:any) => <MenuItem key={s.id} value={s.supName}>{s.supName}</MenuItem>)}</TextField></Grid>
              <Grid item xs={12}><TextField select fullWidth size="small" label="Invoice No *" value={form.invoiceNo} onChange={e => set('invoiceNo', e.target.value)}><MenuItem value="">Select</MenuItem>{invoiceNos.map(n => <MenuItem key={n} value={n}>{n}</MenuItem>)}</TextField></Grid>
              <Grid item xs={6}><TextField fullWidth size="small" label="Bill Invoice Amount" type="number" value={form.billInvoiceAmount} onChange={e => set('billInvoiceAmount', Number(e.target.value))} /></Grid>
              <Grid item xs={6}><TextField fullWidth size="small" label="Invoice Amount *" type="number" value={form.invoiceAmount} onChange={e => set('invoiceAmount', Number(e.target.value))} /></Grid>
              <Grid item xs={6}><TextField fullWidth size="small" label="Supplier Advance" type="number" value={form.supAdvance || 0} onChange={e => set('supAdvance', Number(e.target.value))} /></Grid>
              <Grid item xs={6}><TextField fullWidth size="small" label="Sup Debit *" type="number" value={form.supDebit} onChange={e => set('supDebit', Number(e.target.value))} /></Grid>
              <Grid item xs={12}><TextField fullWidth size="small" label="Remarks" multiline rows={2} value={form.remarks} onChange={e => set('remarks', e.target.value)} /></Grid>
              <Grid item xs={6}><Button fullWidth variant="outlined" onClick={() => invoiceApi.debitNote.docNo().then(r => setForm({...empty(), iDbtCode: r.data.data}))}>Clear</Button></Grid>
              <Grid item xs={6}><Button fullWidth variant="contained" onClick={handleSave} disabled={saving}>{saving ? <CircularProgress size={20}/> : 'Save'}</Button></Grid>
            </Grid>
          </CardContent></Card>
        </Grid>
        <Grid item xs={12} lg={7}>
          <Card><CardContent>
            {loading ? <Box sx={{textAlign:'center',py:4}}><CircularProgress/></Box> : (
              <DataTable rows={rows} columns={[
                {key:'iDbtCode',label:'Doc No'},{key:'invoiceNo',label:'Invoice No'},{key:'supName',label:'Supplier'},
                {key:'supDebit',label:'Debit Amt',render:(v:any)=>`₹${Number(v).toLocaleString('en-IN')}`},{key:'invoiceAmount',label:'Inv Amt',render:(v:any)=>`₹${Number(v).toLocaleString('en-IN')}`},
              ]} />
            )}
          </CardContent></Card>
        </Grid>
      </Grid>
    </Box>
  );
};
