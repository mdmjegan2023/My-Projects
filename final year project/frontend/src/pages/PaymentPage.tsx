import React, { useEffect, useState } from 'react';
import { Box, Card, CardContent, Grid, TextField, MenuItem, Button, CircularProgress, Alert, Typography, Divider, Chip } from '@mui/material';
import { paymentApi, masterApi, dashboardApi } from '../api';
import DataTable from '../components/common/DataTable';
import PageHeader from '../components/common/PageHeader';
import toast from 'react-hot-toast';

const empty = () => ({ pPayCode:'', entryDate: new Date().toISOString().split('T')[0], paymentDate: new Date().toISOString().split('T')[0], paymentName:'', paymentType:'', supName:'', supAddress:'', supPhone:'', supEmail:'', paidAmount:0, transMode:'', description:'', bankName:'', accNo:'', accType:'', bankBranch:'', ifscCode:'', swiftCode:'', remarks:'' });

const PAY_CATEGORIES = ['Purchase Payment','Advance Payment','Miscellaneous'];
const PAY_TYPES      = ['Supplier Payment','Advance','Refund','Others'];

export const PaymentPage = () => {
  const [rows, setRows]           = useState<any[]>([]);
  const [form, setForm]           = useState<any>(empty());
  const [suppliers, setSuppliers] = useState<any[]>([]);
  const [transactions, setTrans]  = useState<any[]>([]);
  const [banks, setBanks]         = useState<any[]>([]);
  const [creditBal, setCreditBal] = useState<number | null>(null);
  const [bankBal, setBankBal]     = useState<number | null>(null);
  const [loading, setLoading]     = useState(true);
  const [saving, setSaving]       = useState(false);

  const load = () => {
    Promise.all([paymentApi.getAll(), masterApi.suppliers.getAll(), masterApi.transactions.getByCat('Retail'), masterApi.banks.getAll(), paymentApi.docNo()])
      .then(([p, sup, tr, bk, dn]) => {
        setRows(p.data.data); setSuppliers(sup.data.data);
        setTrans(tr.data.data); setBanks(bk.data.data);
        setForm((f:any) => ({ ...f, pPayCode: dn.data.data }));
      }).finally(() => setLoading(false));
  };
  useEffect(() => { load(); }, []);
  const set = (k: string, v: any) => setForm((f: any) => ({ ...f, [k]: v }));

  const handleSupplierChange = async (supName: string) => {
    set('supName', supName);
    const s = suppliers.find((x:any) => x.supName === supName);
    if (s) { set('supAddress', s.supAddress||''); set('supPhone', s.supPhone||''); set('supEmail', s.supEmail||''); }
    try {
      const res = await dashboardApi.supplierCredit(supName);
      setCreditBal(res.data.data?.supCredit ?? null);
    } catch { setCreditBal(null); }
  };

  const handleBankChange = async (accNo: string) => {
    set('accNo', accNo);
    const b = banks.find((x:any) => x.accNo === accNo);
    if (b) { set('bankName', b.bankName); set('accType', b.accType); set('bankBranch', b.bankBranch); set('ifscCode', b.ifscCode||''); set('swiftCode', b.swiftCode||''); }
    try {
      const res = await dashboardApi.bankBalance(accNo);
      setBankBal(res.data.data?.balance ?? null);
    } catch { setBankBal(null); }
  };

  const handleSave = async () => {
    if (!form.paymentName || !form.paymentType || !form.supName || !form.paidAmount || !form.transMode || !form.accNo || !form.bankName || !form.accType) {
      toast.error("Please Fill The Detail's!"); return;
    }
    setSaving(true);
    try {
      await paymentApi.save(form);
      toast.success('Credit Paid Successfully.');
      const dn = await paymentApi.docNo();
      setForm({ ...empty(), pPayCode: dn.data.data });
      setCreditBal(null); setBankBal(null);
      load();
    } catch (e: any) { toast.error(e.response?.data?.message || 'Payment Failed.'); }
    finally { setSaving(false); }
  };

  return (
    <Box>
      <PageHeader title="Payment Entries" subtitle="Process supplier payments" breadcrumbs={[{ label: 'Payments' }]} />
      <Grid container spacing={2.5}>
        <Grid item xs={12} lg={5}>
          <Card><CardContent>
            <Grid container spacing={2}>
              <Grid item xs={6}><TextField fullWidth size="small" label="Doc No" value={form.pPayCode} InputProps={{ readOnly: true }} /></Grid>
              <Grid item xs={6}><TextField fullWidth size="small" label="Entry Date" value={form.entryDate} InputProps={{ readOnly: true }} /></Grid>
              <Grid item xs={12}><TextField fullWidth size="small" label="Payment Date" type="date" value={form.paymentDate} onChange={e => set('paymentDate', e.target.value)} InputLabelProps={{ shrink: true }} /></Grid>
              <Grid item xs={6}><TextField select fullWidth size="small" label="Payment Category *" value={form.paymentName} onChange={e => set('paymentName', e.target.value)}><MenuItem value="">Select</MenuItem>{PAY_CATEGORIES.map(o => <MenuItem key={o} value={o}>{o}</MenuItem>)}</TextField></Grid>
              <Grid item xs={6}><TextField select fullWidth size="small" label="Payment Type *" value={form.paymentType} onChange={e => set('paymentType', e.target.value)}><MenuItem value="">Select</MenuItem>{PAY_TYPES.map(o => <MenuItem key={o} value={o}>{o}</MenuItem>)}</TextField></Grid>
              <Grid item xs={12}><TextField select fullWidth size="small" label="Supplier Name *" value={form.supName} onChange={e => handleSupplierChange(e.target.value)}><MenuItem value="">Select</MenuItem>{suppliers.map((s:any) => <MenuItem key={s.id} value={s.supName}>{s.supName}</MenuItem>)}</TextField></Grid>
              {creditBal !== null && (
                <Grid item xs={12}>
                  <Box sx={{ bgcolor: '#FFF3E0', borderRadius: 2, p: 1.5 }}>
                    <Typography variant="body2" color="text.secondary">Credit Balance:</Typography>
                    <Typography variant="h6" fontWeight={700} color="warning.dark">₹{Number(creditBal).toLocaleString('en-IN')}</Typography>
                  </Box>
                </Grid>
              )}
              <Grid item xs={12}><TextField fullWidth size="small" label="Amount To Pay *" type="number" value={form.paidAmount} onChange={e => set('paidAmount', Number(e.target.value))} /></Grid>
              <Grid item xs={12}><TextField select fullWidth size="small" label="Transaction Mode *" value={form.transMode} onChange={e => set('transMode', e.target.value)}><MenuItem value="">Select</MenuItem>{transactions.map((t:any) => <MenuItem key={t.id} value={t.transName}>{t.transName}</MenuItem>)}</TextField></Grid>
              <Grid item xs={12}><TextField fullWidth size="small" label="Description" value={form.description} onChange={e => set('description', e.target.value)} /></Grid>
              <Grid item xs={12}><TextField select fullWidth size="small" label="Account No *" value={form.accNo} onChange={e => handleBankChange(e.target.value)}><MenuItem value="">Select</MenuItem>{banks.map((b:any) => <MenuItem key={b.id} value={b.accNo}>{b.accNo}</MenuItem>)}</TextField></Grid>
              {bankBal !== null && (
                <Grid item xs={12}>
                  <Box sx={{ bgcolor: '#E3F2FD', borderRadius: 2, p: 1.5 }}>
                    <Typography variant="body2" color="text.secondary">Account Balance:</Typography>
                    <Typography variant="h6" fontWeight={700} color="primary.main">₹{Number(bankBal).toLocaleString('en-IN')}</Typography>
                  </Box>
                </Grid>
              )}
              <Grid item xs={6}><TextField fullWidth size="small" label="Bank Name" value={form.bankName} InputProps={{ readOnly: true }} /></Grid>
              <Grid item xs={6}><TextField fullWidth size="small" label="Account Type" value={form.accType} InputProps={{ readOnly: true }} /></Grid>
              <Grid item xs={6}><TextField fullWidth size="small" label="Branch" value={form.bankBranch} InputProps={{ readOnly: true }} /></Grid>
              <Grid item xs={6}><TextField fullWidth size="small" label="IFSC Code" value={form.ifscCode} InputProps={{ readOnly: true }} /></Grid>
              <Grid item xs={12}><TextField fullWidth size="small" label="Remarks" multiline rows={2} value={form.remarks} onChange={e => set('remarks', e.target.value)} /></Grid>
              <Grid item xs={6}><Button fullWidth variant="outlined" onClick={() => paymentApi.docNo().then(r => { setForm({...empty(), pPayCode: r.data.data}); setCreditBal(null); setBankBal(null); })}>Clear</Button></Grid>
              <Grid item xs={6}><Button fullWidth variant="contained" onClick={handleSave} disabled={saving}>{saving ? <CircularProgress size={20}/> : 'Save'}</Button></Grid>
            </Grid>
          </CardContent></Card>
        </Grid>
        <Grid item xs={12} lg={7}>
          <Card><CardContent>
            {loading ? <Box sx={{ textAlign:'center', py:4 }}><CircularProgress /></Box> : (
              <DataTable rows={rows} columns={[
                {key:'pPayCode',label:'Doc No'},{key:'paymentDate',label:'Pay Date'},
                {key:'paymentName',label:'Category'},{key:'supName',label:'Supplier'},
                {key:'paidAmount',label:'Paid Amt',render:(v:any)=>`₹${Number(v).toLocaleString('en-IN')}`},
                {key:'transMode',label:'Mode'},{key:'bankName',label:'Bank'},
              ]} />
            )}
          </CardContent></Card>
        </Grid>
      </Grid>
    </Box>
  );
};
