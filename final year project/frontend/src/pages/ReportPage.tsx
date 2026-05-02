import React, { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import { Box, Card, CardContent, Grid, TextField, MenuItem, Button, CircularProgress, Typography, Chip } from '@mui/material';
import { FilterAlt, Refresh, TableChart } from '@mui/icons-material';
import { reportApi } from '../api';
import DataTable from '../components/common/DataTable';
import PageHeader from '../components/common/PageHeader';
import toast from 'react-hot-toast';

const reportConfig: Record<string, { title: string; apiCall: (f:any) => Promise<any>; columns: any[] }> = {
  'payments': {
    title: 'Payments Report',
    apiCall: (f) => reportApi.payments(f),
    columns: [{key:'pPayCode',label:'Doc No'},{key:'entryDate',label:'Entry Date'},{key:'paymentDate',label:'Pay Date'},{key:'paymentName',label:'Category'},{key:'paymentType',label:'Type'},{key:'supName',label:'Supplier'},{key:'paidAmount',label:'Paid Amt',render:(v:any)=>`₹${Number(v||0).toLocaleString('en-IN')}`},{key:'transMode',label:'Mode'},{key:'bankName',label:'Bank'},{key:'accNo',label:'Acc No'},{key:'remarks',label:'Remarks'}],
  },
  'receipts': {
    title: 'Receipts Report',
    apiCall: (f) => reportApi.receipts(f),
    columns: [{key:'docNo',label:'Doc No'},{key:'entryDate',label:'Entry Date'},{key:'receiptDate',label:'Receipt Date'},{key:'receiptName',label:'Receipt Name'},{key:'receiptType',label:'Type'},{key:'company',label:'Company'},{key:'amount',label:'Amount',render:(v:any)=>`₹${Number(v||0).toLocaleString('en-IN')}`},{key:'transMode',label:'Mode'},{key:'bankName',label:'Bank'},{key:'remarks',label:'Remarks'}],
  },
  'purchase-invoices': {
    title: 'Purchase Invoice Report',
    apiCall: (f) => reportApi.purchaseInvoices(f),
    columns: [{key:'iPurCode',label:'Doc No'},{key:'invoiceNo',label:'Bill No'},{key:'invoiceDate',label:'Bill Date'},{key:'entryDate',label:'Entry Date'},{key:'company',label:'Company'},{key:'catName',label:'Category'},{key:'supName',label:'Supplier'},{key:'invoiceAmount',label:'Amount',render:(v:any)=>`₹${Number(v||0).toLocaleString('en-IN')}`},{key:'invoiceDueDate',label:'Due Date'},{key:'remarks',label:'Remarks'}],
  },
  'due-date-crossed': {
    title: 'Due Date Crossed Report',
    apiCall: (f) => reportApi.dueDateCrossed(f),
    columns: [{key:'invoiceNo',label:'Bill No'},{key:'invoiceDate',label:'Bill Date'},{key:'entryDate',label:'Entry Date'},{key:'company',label:'Company'},{key:'catName',label:'Category'},{key:'supName',label:'Supplier'},{key:'invoiceAmount',label:'Amount',render:(v:any)=>`₹${Number(v||0).toLocaleString('en-IN')}`},{key:'invoiceDueDate',label:'Due Date',render:(v:any)=><Chip label={v} size="small" color="error"/>}],
  },
  'export-invoices': {
    title: 'Export Invoice Report',
    apiCall: (f) => reportApi.exportInvoices(f),
    columns: [{key:'iExpCode',label:'Doc No'},{key:'invoiceNo',label:'Invoice No'},{key:'invoiceDate',label:'Date'},{key:'clientName',label:'Client'},{key:'poNo',label:'PO No'},{key:'totalPair',label:'Pairs'},{key:'otrCurrencyType',label:'Currency'},{key:'otrAmount',label:'OTR Amt'},{key:'inrAmount',label:'INR Amt',render:(v:any)=>`₹${Number(v||0).toLocaleString('en-IN')}`},{key:'status',label:'Status',render:(v:any)=><Chip label={v||'—'} size="small" color={v==='Open'?'success':'default'}/>}],
  },
  'retail-invoices': {
    title: 'Retail Invoice Report',
    apiCall: (f) => reportApi.retailInvoices(f),
    columns: [{key:'iRtlCode',label:'Doc No'},{key:'invoiceNo',label:'Invoice No'},{key:'invoiceDate',label:'Date'},{key:'clientName',label:'Client'},{key:'totalPair',label:'Pairs'},{key:'inrAmount',label:'INR Amt',render:(v:any)=>`₹${Number(v||0).toLocaleString('en-IN')}`},{key:'status',label:'Status',render:(v:any)=><Chip label={v||'—'} size="small" color={v==='Open'?'success':'default'}/>}],
  },
  'retail-receipts': {
    title: 'Retail Receipt Report',
    apiCall: (f) => reportApi.retailReceipts(f),
    columns: [{key:'rRtlCode',label:'Doc No'},{key:'entryDate',label:'Date'},{key:'invoiceNo',label:'Invoice No'},{key:'clientName',label:'Client'},{key:'totalPair',label:'Pairs'},{key:'inrAmount',label:'Amount',render:(v:any)=>`₹${Number(v||0).toLocaleString('en-IN')}`},{key:'transMode',label:'Mode'},{key:'bankName',label:'Bank'}],
  },
  'export-receipts': {
    title: 'Export Receipt Report',
    apiCall: (f) => reportApi.exportReceipts(f),
    columns: [{key:'rExpCode',label:'Doc No'},{key:'entryDate',label:'Date'},{key:'invoiceNo',label:'Invoice No'},{key:'clientName',label:'Client'},{key:'totalPair',label:'Pairs'},{key:'otrAmount',label:'OTR'},{key:'inrAmount',label:'INR Amt',render:(v:any)=>`₹${Number(v||0).toLocaleString('en-IN')}`},{key:'transMode',label:'Mode'},{key:'bankName',label:'Bank'}],
  },
  'debit-notes': {
    title: 'Debit Notes Report',
    apiCall: (f) => reportApi.debitNotes(f),
    columns: [{key:'iDbtCode',label:'Doc No'},{key:'entryDate',label:'Date'},{key:'invoiceNo',label:'Invoice No'},{key:'supName',label:'Supplier'},{key:'billInvoiceAmount',label:'Bill Amt',render:(v:any)=>`₹${Number(v||0).toLocaleString('en-IN')}`},{key:'supDebit',label:'Debit',render:(v:any)=>`₹${Number(v||0).toLocaleString('en-IN')}`},{key:'invoiceAmount',label:'Inv Amt',render:(v:any)=>`₹${Number(v||0).toLocaleString('en-IN')}`},{key:'remarks',label:'Remarks'}],
  },
  'incentives': {
    title: 'Incentives Report',
    apiCall: (f) => reportApi.incentives(f),
    columns: [{key:'docNo',label:'Doc No'},{key:'entryDate',label:'Date'},{key:'receiptDate',label:'Receipt Date'},{key:'incentivesType',label:'Type'},{key:'invoiceNo',label:'Invoice No'},{key:'description',label:'Description'},{key:'amount',label:'Amount',render:(v:any)=>`₹${Number(v||0).toLocaleString('en-IN')}`},{key:'transMode',label:'Mode'},{key:'bankName',label:'Bank'}],
  },
  'supplier-advance': {
    title: 'Supplier Advance Report',
    apiCall: (f) => reportApi.supplierAdvance(f),
    columns: [{key:'pAdvCode',label:'Doc No'},{key:'entryDate',label:'Date'},{key:'supName',label:'Supplier'},{key:'supAdvance',label:'Advance',render:(v:any)=>`₹${Number(v||0).toLocaleString('en-IN')}`},{key:'bankName',label:'Bank'},{key:'accNo',label:'Acc No'},{key:'remarks',label:'Remarks'}],
  },
  'supplier-credit': {
    title: 'Supplier Credit Report',
    apiCall: (f) => reportApi.supplierCredit(f),
    columns: [{key:'supName',label:'Supplier Name'},{key:'supAddress',label:'Address'},{key:'supPhone',label:'Phone'},{key:'supEmail',label:'Email'},{key:'supCredit',label:'Credit Balance',render:(v:any)=><Typography fontWeight={700} color="warning.dark">₹{Number(v||0).toLocaleString('en-IN')}</Typography>}],
  },
  'supplier-ledger': {
    title: 'Supplier Ledger Report',
    apiCall: async (f) => { return { data: { data: [] } }; }, // handled separately
    columns: [{key:'entryDate',label:'Entry Date'},{key:'invoiceDate',label:'Bill Date'},{key:'paymentDate',label:'Pay Date'},{key:'company',label:'Company'},{key:'supName',label:'Supplier'},{key:'transMode',label:'Mode'},{key:'description',label:'Description'},{key:'bankName',label:'Bank'},{key:'accNo',label:'Acc No'},{key:'debit',label:'Dr',render:(v:any)=>v?`₹${Number(v).toLocaleString('en-IN')}`:'—'},{key:'credit',label:'Cr',render:(v:any)=>v?`₹${Number(v).toLocaleString('en-IN')}`:'—'},{key:'clsBal',label:'Closing Bal',render:(v:any)=><Typography fontWeight={700}>₹{Number(v||0).toLocaleString('en-IN')}</Typography>}],
  },
};

export const ReportPage = () => {
  const { type } = useParams<{ type: string }>();
  const config = reportConfig[type!];
  const [rows, setRows]           = useState<any[]>([]);
  const [fromDate, setFromDate]   = useState('');
  const [toDate, setToDate]       = useState('');
  const [filterType, setFilterType] = useState('');
  const [filterValue, setFilterValue] = useState('');
  const [supName, setSupName]     = useState('');
  const [supNames, setSupNames]   = useState<string[]>([]);
  const [loading, setLoading]     = useState(false);
  const [loaded, setLoaded]       = useState(false);

  useEffect(() => {
    setRows([]); setLoaded(false);
    if (type === 'supplier-ledger') {
      reportApi.ledgerSuppliers().then(r => setSupNames(r.data.data));
    }
  }, [type]);

  const handleLoad = async () => {
    if (!config) return;
    setLoading(true);
    try {
      const filter = { fromDate: fromDate || undefined, toDate: toDate || undefined, filterType: filterType || undefined, filterValue: filterValue || undefined };
      let res;
      if (type === 'supplier-ledger') {
        if (!supName) { toast.error('Select a supplier.'); setLoading(false); return; }
        res = await reportApi.supplierLedger(supName, filter);
      } else {
        res = await config.apiCall(filter);
      }
      setRows(res.data.data || []);
      setLoaded(true);
    } catch (e: any) { toast.error('Failed to load report.'); }
    finally { setLoading(false); }
  };

  const handleClear = () => { setFromDate(''); setToDate(''); setFilterType(''); setFilterValue(''); setSupName(''); setRows([]); setLoaded(false); };

  if (!config) return <Box><Typography color="error">Unknown report type: {type}</Typography></Box>;

  return (
    <Box>
      <PageHeader title={config.title} subtitle="Filter and view report data" extra={
        loaded ? <Chip icon={<TableChart />} label={`${rows.length} records`} color="primary" /> : undefined
      } breadcrumbs={[{ label: 'Reports' }, { label: config.title }]} />

      <Card sx={{ mb: 2.5 }}>
        <CardContent>
          <Grid container spacing={2} alignItems="flex-end">
            <Grid item xs={12} sm={6} md={3}><TextField fullWidth size="small" label="From Date" type="date" value={fromDate} onChange={e => setFromDate(e.target.value)} InputLabelProps={{ shrink: true }} /></Grid>
            <Grid item xs={12} sm={6} md={3}><TextField fullWidth size="small" label="To Date" type="date" value={toDate} onChange={e => setToDate(e.target.value)} InputLabelProps={{ shrink: true }} /></Grid>
            {type === 'supplier-ledger' ? (
              <Grid item xs={12} sm={6} md={3}><TextField select fullWidth size="small" label="Supplier *" value={supName} onChange={e => setSupName(e.target.value)}><MenuItem value="">Select</MenuItem>{supNames.map(n => <MenuItem key={n} value={n}>{n}</MenuItem>)}</TextField></Grid>
            ) : (
              <>
                <Grid item xs={12} sm={6} md={2}><TextField fullWidth size="small" label="Filter By" value={filterType} onChange={e => setFilterType(e.target.value)} placeholder="e.g. supName" /></Grid>
                <Grid item xs={12} sm={6} md={2}><TextField fullWidth size="small" label="Filter Value" value={filterValue} onChange={e => setFilterValue(e.target.value)} /></Grid>
              </>
            )}
            <Grid item xs={12} sm={6} md={2} sx={{ display:'flex', gap:1 }}>
              <Button fullWidth variant="contained" startIcon={<FilterAlt />} onClick={handleLoad} disabled={loading}>{loading ? <CircularProgress size={18}/> : 'Load'}</Button>
              <Button variant="outlined" onClick={handleClear} sx={{ minWidth: 44, px: 1 }}><Refresh /></Button>
            </Grid>
          </Grid>
        </CardContent>
      </Card>

      <Card>
        <CardContent>
          {!loaded && !loading ? (
            <Box sx={{ textAlign:'center', py:6 }}>
              <TableChart sx={{ fontSize:60, color:'#e0e0e0', mb:2 }} />
              <Typography color="text.secondary">Click "Load" to fetch report data</Typography>
            </Box>
          ) : loading ? (
            <Box sx={{ textAlign:'center', py:6 }}><CircularProgress /></Box>
          ) : (
            <DataTable rows={rows} columns={config.columns} />
          )}
        </CardContent>
      </Card>
    </Box>
  );
};
