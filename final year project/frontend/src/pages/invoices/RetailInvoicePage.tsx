import React, { useEffect, useState } from 'react';
import { Box, Card, CardContent, Grid, TextField, MenuItem, Button, CircularProgress } from '@mui/material';
import { invoiceApi, masterApi } from '../../api';
import DataTable from '../../components/common/DataTable';
import PageHeader from '../../components/common/PageHeader';
import toast from 'react-hot-toast';

const empty = () => ({ iRtlCode:'', entryDate: new Date().toISOString().split('T')[0], invoiceNo:'', invoiceDate: new Date().toISOString().split('T')[0], clientName:'', poNo:'', totalPair:0, inrExchangeRate:0, inrAmount:0, sgstPercent:0, sgstValue:0, cgstPercent:0, cgstValue:0, igstPercent:0, igstValue:0, remarks:'' });

export const RetailInvoicePage = () => {
  const [rows, setRows] = useState<any[]>([]);
  const [form, setForm] = useState<any>(empty());
  const [clients, setClients] = useState<any[]>([]);
  const [loading, setLoading] = useState(true);
  const [saving, setSaving] = useState(false);

  const load = () => {
    Promise.all([invoiceApi.retail.getAll(), masterApi.clients.getAll(), invoiceApi.retail.docNo()])
      .then(([inv, cl, dn]) => { setRows(inv.data.data); setClients(cl.data.data); setForm((f:any) => ({ ...f, iRtlCode: dn.data.data })); })
      .finally(() => setLoading(false));
  };
  useEffect(() => { load(); }, []);
  const set = (k: string, v: any) => setForm((f: any) => ({ ...f, [k]: v }));

  const handleSave = async () => {
    if (!form.invoiceNo || !form.clientName || !form.totalPair) { toast.error("Please Fill The Detail's!"); return; }
    setSaving(true);
    try {
      await invoiceApi.retail.save(form);
      toast.success('Retail Invoice Added.');
      const dn = await invoiceApi.retail.docNo();
      setForm({ ...empty(), iRtlCode: dn.data.data });
      load();
    } catch (e: any) { toast.error(e.response?.data?.message || 'Failed.'); }
    finally { setSaving(false); }
  };

  return (
    <Box>
      <PageHeader title="Retail Sale Invoice" breadcrumbs={[{ label: 'Bill Entries' }, { label: 'Retail Sale Invoice' }]} />
      <Grid container spacing={2.5}>
        <Grid item xs={12} lg={5}>
          <Card><CardContent>
            <Grid container spacing={2}>
              <Grid item xs={6}><TextField fullWidth size="small" label="Doc No" value={form.iRtlCode} InputProps={{ readOnly: true }} /></Grid>
              <Grid item xs={6}><TextField fullWidth size="small" label="Entry Date" value={form.entryDate} InputProps={{ readOnly: true }} /></Grid>
              <Grid item xs={6}><TextField fullWidth size="small" label="Invoice No *" value={form.invoiceNo} onChange={e => set('invoiceNo', e.target.value)} /></Grid>
              <Grid item xs={6}><TextField fullWidth size="small" label="Invoice Date" type="date" value={form.invoiceDate} onChange={e => set('invoiceDate', e.target.value)} InputLabelProps={{ shrink: true }} /></Grid>
              <Grid item xs={12}><TextField select fullWidth size="small" label="Client Name *" value={form.clientName} onChange={e => set('clientName', e.target.value)}><MenuItem value="">Select</MenuItem>{clients.map((c:any) => <MenuItem key={c.id} value={c.clientName}>{c.clientName}</MenuItem>)}</TextField></Grid>
              <Grid item xs={6}><TextField fullWidth size="small" label="PO No" value={form.poNo} onChange={e => set('poNo', e.target.value)} /></Grid>
              <Grid item xs={6}><TextField fullWidth size="small" label="Total Pair *" type="number" value={form.totalPair} onChange={e => set('totalPair', Number(e.target.value))} /></Grid>
              <Grid item xs={6}><TextField fullWidth size="small" label="INR Exchange Rate" type="number" value={form.inrExchangeRate} onChange={e => set('inrExchangeRate', Number(e.target.value))} /></Grid>
              <Grid item xs={6}><TextField fullWidth size="small" label="INR Amount *" type="number" value={form.inrAmount} onChange={e => set('inrAmount', Number(e.target.value))} /></Grid>
              {[['sgstPercent','SGST %'],['sgstValue','SGST Val'],['cgstPercent','CGST %'],['cgstValue','CGST Val'],['igstPercent','IGST %'],['igstValue','IGST Val']].map(([k,l]) => (
                <Grid item xs={4} key={k}><TextField fullWidth size="small" label={l} type="number" value={form[k]} onChange={e => set(k, Number(e.target.value))} /></Grid>
              ))}
              <Grid item xs={12}><TextField fullWidth size="small" label="Remarks" multiline rows={2} value={form.remarks} onChange={e => set('remarks', e.target.value)} /></Grid>
              <Grid item xs={6}><Button fullWidth variant="outlined" onClick={() => invoiceApi.retail.docNo().then(r => setForm({ ...empty(), iRtlCode: r.data.data }))}>Clear</Button></Grid>
              <Grid item xs={6}><Button fullWidth variant="contained" onClick={handleSave} disabled={saving}>{saving ? <CircularProgress size={20}/> : 'Save'}</Button></Grid>
            </Grid>
          </CardContent></Card>
        </Grid>
        <Grid item xs={12} lg={7}>
          <Card><CardContent>
            {loading ? <Box sx={{textAlign:'center',py:4}}><CircularProgress/></Box> : (
              <DataTable rows={rows} columns={[
                {key:'iRtlCode',label:'Doc No'},{key:'invoiceNo',label:'Invoice No'},{key:'invoiceDate',label:'Date'},{key:'clientName',label:'Client'},
                {key:'totalPair',label:'Pairs'},{key:'inrAmount',label:'Amount',render:(v:any)=>`₹${Number(v).toLocaleString('en-IN')}`},{key:'status',label:'Status'},
              ]} />
            )}
          </CardContent></Card>
        </Grid>
      </Grid>
    </Box>
  );
};
