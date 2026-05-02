import React, { useEffect, useState } from 'react';
import { Box, Card, CardContent, Grid, TextField, MenuItem, Button, CircularProgress } from '@mui/material';
import { receiptApi, masterApi } from '../../api';
import DataTable from '../../components/common/DataTable';
import PageHeader from '../../components/common/PageHeader';
import toast from 'react-hot-toast';

const empty = () => ({ docNo:'', entryDate: new Date().toISOString().split('T')[0], receiptDate: new Date().toISOString().split('T')[0], receiptName:'', receiptType:'', company:'', description:'', amount:0, transMode:'', bankName:'', accNo:'', accType:'', bankBranch:'', ifscCode:'', swiftCode:'', remarks:'' });

export const ReceiptPage = () => {
  const [rows, setRows]               = useState<any[]>([]);
  const [form, setForm]               = useState<any>(empty());
  const [companies, setCompanies]     = useState<any[]>([]);
  const [receiptNames, setReceiptNames] = useState<any[]>([]);
  const [receiptTypes, setReceiptTypes] = useState<any[]>([]);
  const [transactions, setTransactions] = useState<any[]>([]);
  const [banks, setBanks]             = useState<any[]>([]);
  const [loading, setLoading]         = useState(true);
  const [saving, setSaving]           = useState(false);

  const load = () => {
    Promise.all([
      receiptApi.general.getAll(), masterApi.companies.getAll(),
      masterApi.receiptNames.getAll(), masterApi.receiptTypes.getAll(),
      masterApi.transactions.getByCat('Retail'), masterApi.banks.getAll(),
      receiptApi.general.docNo(),
    ]).then(([r, co, rn, rt, tr, bk, dn]) => {
      setRows(r.data.data); setCompanies(co.data.data);
      setReceiptNames(rn.data.data); setReceiptTypes(rt.data.data);
      setTransactions(tr.data.data); setBanks(bk.data.data);
      setForm((f:any) => ({ ...f, docNo: dn.data.data }));
    }).finally(() => setLoading(false));
  };
  useEffect(() => { load(); }, []);
  const set = (k: string, v: any) => setForm((f: any) => ({ ...f, [k]: v }));

  const handleBankChange = (accNo: string) => {
    set('accNo', accNo);
    const b = banks.find((x:any) => x.accNo === accNo);
    if (b) { set('bankName', b.bankName); set('accType', b.accType); set('bankBranch', b.bankBranch); set('ifscCode', b.ifscCode || ''); set('swiftCode', b.swiftCode || ''); }
  };

  const handleSave = async () => {
    if (!form.receiptName || !form.receiptType || !form.company || !form.amount || !form.transMode || !form.accNo) {
      toast.error("Please Fill The Detail's!"); return;
    }
    setSaving(true);
    try {
      await receiptApi.general.save(form);
      toast.success('Receipt Added.');
      const dn = await receiptApi.general.docNo();
      setForm({ ...empty(), docNo: dn.data.data });
      load();
    } catch (e: any) { toast.error(e.response?.data?.message || 'Failed.'); }
    finally { setSaving(false); }
  };

  return (
    <Box>
      <PageHeader title="Receipt Entries" breadcrumbs={[{ label: 'Receipts' }, { label: 'Receipt Entries' }]} />
      <Grid container spacing={2.5}>
        <Grid item xs={12} lg={5}>
          <Card><CardContent>
            <Grid container spacing={2}>
              <Grid item xs={6}><TextField fullWidth size="small" label="Doc No" value={form.docNo} InputProps={{ readOnly: true }} /></Grid>
              <Grid item xs={6}><TextField fullWidth size="small" label="Entry Date" value={form.entryDate} InputProps={{ readOnly: true }} /></Grid>
              <Grid item xs={12}><TextField fullWidth size="small" label="Receipt Date" type="date" value={form.receiptDate} onChange={e => set('receiptDate', e.target.value)} InputLabelProps={{ shrink: true }} /></Grid>
              <Grid item xs={12}><TextField select fullWidth size="small" label="Receipt Name *" value={form.receiptName} onChange={e => set('receiptName', e.target.value)}><MenuItem value="">Select</MenuItem>{receiptNames.map((r:any) => <MenuItem key={r.id} value={r.receiptName}>{r.receiptName}</MenuItem>)}</TextField></Grid>
              <Grid item xs={12}><TextField select fullWidth size="small" label="Receipt Type *" value={form.receiptType} onChange={e => set('receiptType', e.target.value)}><MenuItem value="">Select</MenuItem>{receiptTypes.map((r:any) => <MenuItem key={r.id} value={r.receiptTypeName}>{r.receiptTypeName}</MenuItem>)}</TextField></Grid>
              <Grid item xs={12}><TextField select fullWidth size="small" label="Company *" value={form.company} onChange={e => set('company', e.target.value)}><MenuItem value="">Select</MenuItem>{companies.map((c:any) => <MenuItem key={c.id} value={c.compName}>{c.compName}</MenuItem>)}</TextField></Grid>
              <Grid item xs={12}><TextField fullWidth size="small" label="Description" value={form.description} onChange={e => set('description', e.target.value)} /></Grid>
              <Grid item xs={12}><TextField fullWidth size="small" label="Amount *" type="number" value={form.amount} onChange={e => set('amount', Number(e.target.value))} /></Grid>
              <Grid item xs={12}><TextField select fullWidth size="small" label="Transaction Mode *" value={form.transMode} onChange={e => set('transMode', e.target.value)}><MenuItem value="">Select</MenuItem>{transactions.map((t:any) => <MenuItem key={t.id} value={t.transName}>{t.transName}</MenuItem>)}</TextField></Grid>
              <Grid item xs={12}><TextField select fullWidth size="small" label="Account No *" value={form.accNo} onChange={e => handleBankChange(e.target.value)}><MenuItem value="">Select</MenuItem>{banks.map((b:any) => <MenuItem key={b.id} value={b.accNo}>{b.accNo}</MenuItem>)}</TextField></Grid>
              <Grid item xs={6}><TextField fullWidth size="small" label="Bank Name" value={form.bankName} InputProps={{ readOnly: true }} /></Grid>
              <Grid item xs={6}><TextField fullWidth size="small" label="Account Type" value={form.accType} InputProps={{ readOnly: true }} /></Grid>
              <Grid item xs={6}><TextField fullWidth size="small" label="Branch" value={form.bankBranch} InputProps={{ readOnly: true }} /></Grid>
              <Grid item xs={6}><TextField fullWidth size="small" label="IFSC Code" value={form.ifscCode} InputProps={{ readOnly: true }} /></Grid>
              <Grid item xs={12}><TextField fullWidth size="small" label="Remarks" multiline rows={2} value={form.remarks} onChange={e => set('remarks', e.target.value)} /></Grid>
              <Grid item xs={6}><Button fullWidth variant="outlined" onClick={() => receiptApi.general.docNo().then(r => setForm({...empty(), docNo: r.data.data}))}>Clear</Button></Grid>
              <Grid item xs={6}><Button fullWidth variant="contained" onClick={handleSave} disabled={saving}>{saving ? <CircularProgress size={20}/> : 'Save'}</Button></Grid>
            </Grid>
          </CardContent></Card>
        </Grid>
        <Grid item xs={12} lg={7}>
          <Card><CardContent>
            {loading ? <Box sx={{ textAlign:'center', py:4 }}><CircularProgress /></Box> : (
              <DataTable rows={rows} columns={[
                { key:'docNo', label:'Doc No' }, { key:'receiptDate', label:'Receipt Date' },
                { key:'receiptName', label:'Receipt Name' }, { key:'receiptType', label:'Type' },
                { key:'company', label:'Company' },
                { key:'amount', label:'Amount', render:(v:any) => `₹${Number(v).toLocaleString('en-IN')}` },
                { key:'transMode', label:'Mode' }, { key:'bankName', label:'Bank' },
              ]} />
            )}
          </CardContent></Card>
        </Grid>
      </Grid>
    </Box>
  );
};
