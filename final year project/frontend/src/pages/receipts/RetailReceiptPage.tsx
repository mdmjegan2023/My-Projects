import React, { useEffect, useState } from 'react';
import { Box, Card, CardContent, Grid, TextField, MenuItem, Button, CircularProgress, Typography } from '@mui/material';
import { receiptApi, invoiceApi, masterApi } from '../../api';
import DataTable from '../../components/common/DataTable';
import PageHeader from '../../components/common/PageHeader';
import toast from 'react-hot-toast';

const empty = () => ({ rRtlCode:'', entryDate: new Date().toISOString().split('T')[0], invoiceType:'', discountPercent:0, invoiceNo:'', invoiceDate:'', clientName:'', poNo:'', totalPair:0, inrExchangeRate:0, inrAmount:0, sgstPercent:0, sgstValue:0, cgstPercent:0, cgstValue:0, igstPercent:0, igstValue:0, transMode:'', bankName:'', accNo:'', accType:'', bankBranch:'', ifscCode:'', swiftCode:'', remarks:'' });

export const RetailReceiptPage = () => {
  const [rows, setRows]             = useState<any[]>([]);
  const [form, setForm]             = useState<any>(empty());
  const [openInvNos, setOpenInvNos] = useState<string[]>([]);
  const [transactions, setTrans]    = useState<any[]>([]);
  const [banks, setBanks]           = useState<any[]>([]);
  const [loading, setLoading]       = useState(true);
  const [saving, setSaving]         = useState(false);

  const load = () => {
    Promise.all([
      receiptApi.retail.getAll(), invoiceApi.retail.openInvoiceNos(),
      masterApi.transactions.getByCat('Retail'), masterApi.banks.getAll(), receiptApi.retail.docNo(),
    ]).then(([r, oi, tr, bk, dn]) => {
      setRows(r.data.data); setOpenInvNos(oi.data.data);
      setTrans(tr.data.data); setBanks(bk.data.data);
      setForm((f:any) => ({ ...f, rRtlCode: dn.data.data }));
    }).finally(() => setLoading(false));
  };
  useEffect(() => { load(); }, []);
  const set = (k: string, v: any) => setForm((f: any) => ({ ...f, [k]: v }));

  const handleInvoiceChange = async (invNo: string) => {
    set('invoiceNo', invNo);
    try {
      const all = await invoiceApi.retail.getAll();
      const inv = all.data.data.find((i:any) => i.invoiceNo === invNo);
      if (inv) { set('invoiceDate', inv.invoiceDate); set('clientName', inv.clientName); set('poNo', inv.poNo || ''); set('inrExchangeRate', inv.inrExchangeRate); }
    } catch {}
  };

  const handleBankChange = (accNo: string) => {
    set('accNo', accNo);
    const b = banks.find((x:any) => x.accNo === accNo);
    if (b) { set('bankName', b.bankName); set('accType', b.accType); set('bankBranch', b.bankBranch); set('ifscCode', b.ifscCode || ''); set('swiftCode', b.swiftCode || ''); }
  };

  const handleSave = async () => {
    if (!form.invoiceType || !form.invoiceNo || !form.totalPair || !form.transMode || !form.accNo || !form.bankName || !form.accType) {
      toast.error("Please Fill The Detail's!"); return;
    }
    setSaving(true);
    try {
      await receiptApi.retail.save(form);
      toast.success('Retail Receipt Added.');
      const dn = await receiptApi.retail.docNo();
      setForm({ ...empty(), rRtlCode: dn.data.data });
      load();
    } catch (e: any) { toast.error(e.response?.data?.message || 'Failed.'); }
    finally { setSaving(false); }
  };

  return (
    <Box>
      <PageHeader title="Retail Sale Receipt" breadcrumbs={[{ label: 'Receipts' }, { label: 'Retail Sale Receipt' }]} />
      <Grid container spacing={2.5}>
        <Grid item xs={12} lg={5}>
          <Card><CardContent>
            <Grid container spacing={2}>
              <Grid item xs={6}><TextField fullWidth size="small" label="Doc No" value={form.rRtlCode} InputProps={{ readOnly: true }} /></Grid>
              <Grid item xs={6}><TextField fullWidth size="small" label="Entry Date" value={form.entryDate} InputProps={{ readOnly: true }} /></Grid>
              <Grid item xs={6}><TextField select fullWidth size="small" label="Invoice Type *" value={form.invoiceType} onChange={e => set('invoiceType', e.target.value)}><MenuItem value="">Select</MenuItem>{['Full','Partial'].map(o => <MenuItem key={o} value={o}>{o}</MenuItem>)}</TextField></Grid>
              <Grid item xs={6}><TextField fullWidth size="small" label="Discount %" type="number" value={form.discountPercent} onChange={e => set('discountPercent', Number(e.target.value))} /></Grid>
              <Grid item xs={12}><TextField select fullWidth size="small" label="Invoice No *" value={form.invoiceNo} onChange={e => handleInvoiceChange(e.target.value)}><MenuItem value="">Select</MenuItem>{openInvNos.map(n => <MenuItem key={n} value={n}>{n}</MenuItem>)}</TextField></Grid>
              <Grid item xs={6}><TextField fullWidth size="small" label="Invoice Date" value={form.invoiceDate} InputProps={{ readOnly: true }} /></Grid>
              <Grid item xs={6}><TextField fullWidth size="small" label="Client Name" value={form.clientName} InputProps={{ readOnly: true }} /></Grid>
              <Grid item xs={6}><TextField fullWidth size="small" label="PO No" value={form.poNo} InputProps={{ readOnly: true }} /></Grid>
              <Grid item xs={6}><TextField fullWidth size="small" label="Total Pair *" type="number" value={form.totalPair} onChange={e => set('totalPair', Number(e.target.value))} /></Grid>
              <Grid item xs={6}><TextField fullWidth size="small" label="INR Exchange Rate" value={form.inrExchangeRate} InputProps={{ readOnly: true }} /></Grid>
              <Grid item xs={6}><TextField fullWidth size="small" label="INR Amount *" type="number" value={form.inrAmount} onChange={e => set('inrAmount', Number(e.target.value))} /></Grid>
              {[['sgstPercent','SGST %'],['sgstValue','SGST Val'],['cgstPercent','CGST %'],['cgstValue','CGST Val'],['igstPercent','IGST %'],['igstValue','IGST Val']].map(([k,l]) => (
                <Grid item xs={4} key={k}><TextField fullWidth size="small" label={l} type="number" value={form[k]} onChange={e => set(k, Number(e.target.value))} /></Grid>
              ))}
              <Grid item xs={12}><TextField select fullWidth size="small" label="Transaction Mode *" value={form.transMode} onChange={e => set('transMode', e.target.value)}><MenuItem value="">Select</MenuItem>{transactions.map((t:any) => <MenuItem key={t.id} value={t.transName}>{t.transName}</MenuItem>)}</TextField></Grid>
              <Grid item xs={12}><TextField select fullWidth size="small" label="Account No *" value={form.accNo} onChange={e => handleBankChange(e.target.value)}><MenuItem value="">Select</MenuItem>{banks.map((b:any) => <MenuItem key={b.id} value={b.accNo}>{b.accNo}</MenuItem>)}</TextField></Grid>
              <Grid item xs={6}><TextField fullWidth size="small" label="Bank Name" value={form.bankName} InputProps={{ readOnly: true }} /></Grid>
              <Grid item xs={6}><TextField fullWidth size="small" label="Account Type" value={form.accType} InputProps={{ readOnly: true }} /></Grid>
              <Grid item xs={6}><TextField fullWidth size="small" label="Branch" value={form.bankBranch} InputProps={{ readOnly: true }} /></Grid>
              <Grid item xs={6}><TextField fullWidth size="small" label="IFSC Code" value={form.ifscCode} InputProps={{ readOnly: true }} /></Grid>
              <Grid item xs={12}><TextField fullWidth size="small" label="Remarks" multiline rows={2} value={form.remarks} onChange={e => set('remarks', e.target.value)} /></Grid>
              <Grid item xs={6}><Button fullWidth variant="outlined" onClick={() => receiptApi.retail.docNo().then(r => setForm({...empty(), rRtlCode: r.data.data}))}>Clear</Button></Grid>
              <Grid item xs={6}><Button fullWidth variant="contained" onClick={handleSave} disabled={saving}>{saving ? <CircularProgress size={20}/> : 'Save'}</Button></Grid>
            </Grid>
          </CardContent></Card>
        </Grid>
        <Grid item xs={12} lg={7}>
          <Card><CardContent>
            {loading ? <Box sx={{ textAlign:'center', py:4 }}><CircularProgress /></Box> : (
              <DataTable rows={rows} columns={[
                {key:'rRtlCode',label:'Doc No'},{key:'invoiceNo',label:'Invoice No'},{key:'clientName',label:'Client'},
                {key:'totalPair',label:'Pairs'},{key:'inrAmount',label:'Amount',render:(v:any)=>`₹${Number(v).toLocaleString('en-IN')}`},
                {key:'transMode',label:'Mode'},{key:'bankName',label:'Bank'},
              ]} />
            )}
          </CardContent></Card>
        </Grid>
      </Grid>
    </Box>
  );
};
