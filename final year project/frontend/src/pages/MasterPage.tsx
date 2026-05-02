import React, { useEffect, useState, useCallback } from 'react';
import { useParams } from 'react-router-dom';
import { Box, Card, CardContent, Dialog, DialogTitle, DialogContent, DialogActions, Button, TextField, Grid, MenuItem, CircularProgress, Alert } from '@mui/material';
import { masterApi } from '../api';
import DataTable from '../components/common/DataTable';
import PageHeader from '../components/common/PageHeader';
import toast from 'react-hot-toast';

const masterConfig: Record<string, { title: string; subtitle: string; api: any; columns: any[]; fields: any[] }> = {
  banks: {
    title: 'Bank Master', subtitle: 'Manage bank accounts',
    api: masterApi.banks,
    columns: [
      { key: 'bankCode', label: 'Code' }, { key: 'bankName', label: 'Bank Name' },
      { key: 'bankBranch', label: 'Branch' }, { key: 'accType', label: 'Acc Type' },
      { key: 'accNo', label: 'Account No' }, { key: 'ifscCode', label: 'IFSC' },
      { key: 'swiftCode', label: 'Swift Code' }, { key: 'remarks', label: 'Remarks' },
    ],
    fields: [
      { key: 'bankCode', label: 'Bank Code *', required: true },
      { key: 'bankName', label: 'Bank Name *', required: true },
      { key: 'bankBranch', label: 'Branch *', required: true },
      { key: 'accType', label: 'Account Type *', required: true },
      { key: 'accNo', label: 'Account No *', required: true, numeric: true },
      { key: 'ifscCode', label: 'IFSC Code' },
      { key: 'swiftCode', label: 'Swift Code' },
      { key: 'remarks', label: 'Remarks', multiline: true },
    ],
  },
  categories: {
    title: 'Category Master', subtitle: 'Manage purchase categories',
    api: masterApi.categories,
    columns: [{ key: 'catCode', label: 'Code' }, { key: 'catName', label: 'Category Name' }, { key: 'remarks', label: 'Remarks' }],
    fields: [{ key: 'catCode', label: 'Category Code *', required: true }, { key: 'catName', label: 'Category Name *', required: true }, { key: 'remarks', label: 'Remarks', multiline: true }],
  },
  companies: {
    title: 'Company Master', subtitle: 'Manage companies',
    api: masterApi.companies,
    columns: [{ key: 'compCode', label: 'Code' }, { key: 'compName', label: 'Company Name' }, { key: 'compPhone', label: 'Phone' }, { key: 'taxType', label: 'Tax Type' }, { key: 'taxNo', label: 'Tax No' }],
    fields: [
      { key: 'compCode', label: 'Company Code *', required: true },
      { key: 'compName', label: 'Company Name *', required: true },
      { key: 'compAddress', label: 'Address', multiline: true },
      { key: 'compPhone', label: 'Phone', numeric: true },
      { key: 'compEmail', label: 'Email' },
      { key: 'taxType', label: 'Tax Type', select: true, options: ['GST', 'VAT', 'NO'] },
      { key: 'taxNo', label: 'Tax No' },
      { key: 'remarks', label: 'Remarks', multiline: true },
    ],
  },
  clients: {
    title: 'Client Master', subtitle: 'Manage clients',
    api: masterApi.clients,
    columns: [{ key: 'clientCode', label: 'Code' }, { key: 'clientName', label: 'Client Name' }, { key: 'clientPhone', label: 'Phone' }, { key: 'taxType', label: 'Tax Type' }],
    fields: [
      { key: 'clientCode', label: 'Client Code *', required: true },
      { key: 'clientName', label: 'Client Name *', required: true },
      { key: 'clientAddress', label: 'Address', multiline: true },
      { key: 'clientPhone', label: 'Phone', numeric: true },
      { key: 'clientEmail', label: 'Email' },
      { key: 'taxType', label: 'Tax Type', select: true, options: ['GST', 'VAT', 'NO'] },
      { key: 'taxNo', label: 'Tax No' },
      { key: 'remarks', label: 'Remarks', multiline: true },
    ],
  },
  currencies: {
    title: 'Currency Master', subtitle: 'Manage currencies',
    api: masterApi.currencies,
    columns: [{ key: 'currCode', label: 'Code' }, { key: 'currName', label: 'Currency Name' }, { key: 'remarks', label: 'Remarks' }],
    fields: [{ key: 'currCode', label: 'Currency Code *', required: true }, { key: 'currName', label: 'Currency Name *', required: true }, { key: 'remarks', label: 'Remarks', multiline: true }],
  },
  'receipt-names': {
    title: 'Receipt Name Master', subtitle: 'Manage receipt names',
    api: masterApi.receiptNames,
    columns: [{ key: 'receiptCode', label: 'Code' }, { key: 'receiptName', label: 'Receipt Name' }, { key: 'remarks', label: 'Remarks' }],
    fields: [{ key: 'receiptCode', label: 'Receipt Code *', required: true }, { key: 'receiptName', label: 'Receipt Name *', required: true }, { key: 'remarks', label: 'Remarks', multiline: true }],
  },
  'receipt-types': {
    title: 'Receipt Type Master', subtitle: 'Manage receipt types',
    api: masterApi.receiptTypes,
    columns: [{ key: 'receiptTypeCode', label: 'Code' }, { key: 'receiptTypeName', label: 'Receipt Type' }, { key: 'catName', label: 'Category' }, { key: 'remarks', label: 'Remarks' }],
    fields: [{ key: 'receiptTypeCode', label: 'Type Code *', required: true }, { key: 'receiptTypeName', label: 'Type Name *', required: true }, { key: 'catName', label: 'Category' }, { key: 'remarks', label: 'Remarks', multiline: true }],
  },
  suppliers: {
    title: 'Supplier Master', subtitle: 'Manage suppliers',
    api: masterApi.suppliers,
    columns: [{ key: 'supCode', label: 'Code' }, { key: 'supName', label: 'Supplier Name' }, { key: 'supPhone', label: 'Phone' }, { key: 'taxType', label: 'Tax Type' }, { key: 'dueDays', label: 'Due Days' }, { key: 'catName', label: 'Category' }],
    fields: [
      { key: 'supCode', label: 'Supplier Code *', required: true },
      { key: 'supName', label: 'Supplier Name *', required: true },
      { key: 'supAddress', label: 'Address', multiline: true },
      { key: 'supPhone', label: 'Phone', numeric: true },
      { key: 'supEmail', label: 'Email' },
      { key: 'taxType', label: 'Tax Type *', required: true, select: true, options: ['GST', 'VAT', 'NO'] },
      { key: 'taxNo', label: 'Tax No *', required: true },
      { key: 'dueDays', label: 'Due Days *', required: true, numeric: true },
      { key: 'catName', label: 'Category *', required: true },
      { key: 'remarks', label: 'Remarks', multiline: true },
    ],
  },
  transactions: {
    title: 'Transaction Master', subtitle: 'Manage transaction modes',
    api: masterApi.transactions,
    columns: [{ key: 'transCode', label: 'Code' }, { key: 'transName', label: 'Transaction Name' }, { key: 'catName', label: 'Category' }, { key: 'remarks', label: 'Remarks' }],
    fields: [
      { key: 'transCode', label: 'Trans Code *', required: true },
      { key: 'transName', label: 'Trans Name *', required: true },
      { key: 'catName', label: 'Category', select: true, options: ['Retail', 'Export'] },
      { key: 'remarks', label: 'Remarks', multiline: true },
    ],
  },
};

export const MasterPage = () => {
  const { type } = useParams<{ type: string }>();
  const config = masterConfig[type!];
  const [rows, setRows] = useState<any[]>([]);
  const [loading, setLoading] = useState(true);
  const [open, setOpen] = useState(false);
  const [editing, setEditing] = useState<any>(null);
  const [form, setForm] = useState<Record<string, any>>({});
  const [saving, setSaving] = useState(false);

  const load = useCallback(() => {
    setLoading(true);
    config.api.getAll().then((r: any) => setRows(r.data.data)).finally(() => setLoading(false));
  }, [config]);

  useEffect(() => { load(); }, [load]);

  const openAdd = () => { setEditing(null); setForm({}); setOpen(true); };
  const openEdit = (row: any) => { setEditing(row); setForm({ ...row }); setOpen(true); };

  const handleSave = async () => {
    for (const f of config.fields) {
      if (f.required && !form[f.key]) { toast.error(`Please fill ${f.label.replace(' *', '')}`); return; }
    }
    setSaving(true);
    try {
      if (editing) { await config.api.update(editing.id, form); toast.success('Updated successfully.'); }
      else { await config.api.add(form); toast.success('Added successfully.'); }
      setOpen(false); load();
    } catch (e: any) {
      toast.error(e.response?.data?.message || 'Operation failed.');
    } finally { setSaving(false); }
  };

  const handleDelete = async (row: any) => {
    if (!window.confirm(`Delete "${row[config.columns[1]?.key]}"?`)) return;
    try { await config.api.delete(row.id); toast.success('Deleted.'); load(); }
    catch (e: any) { toast.error(e.response?.data?.message || 'Delete failed.'); }
  };

  if (!config) return <Alert severity="error">Unknown master type: {type}</Alert>;

  return (
    <Box>
      <PageHeader title={config.title} subtitle={config.subtitle} onAdd={openAdd}
        breadcrumbs={[{ label: 'Masters' }, { label: config.title }]} />
      <Card>
        <CardContent>
          {loading ? <Box sx={{ textAlign: 'center', py: 4 }}><CircularProgress /></Box>
            : <DataTable columns={config.columns} rows={rows} onEdit={openEdit} onDelete={handleDelete} />}
        </CardContent>
      </Card>

      <Dialog open={open} onClose={() => setOpen(false)} maxWidth="sm" fullWidth>
        <DialogTitle sx={{ fontWeight: 700 }}>{editing ? `Edit ${config.title}` : `Add ${config.title}`}</DialogTitle>
        <DialogContent dividers>
          <Grid container spacing={2} sx={{ pt: 1 }}>
            {config.fields.map(f => (
              <Grid item xs={12} sm={f.multiline ? 12 : 6} key={f.key}>
                {f.select
                  ? <TextField select fullWidth size="small" label={f.label} value={form[f.key] || ''} onChange={e => setForm(p => ({ ...p, [f.key]: e.target.value }))}>
                      <MenuItem value="">Select</MenuItem>
                      {f.options.map((o: string) => <MenuItem key={o} value={o}>{o}</MenuItem>)}
                    </TextField>
                  : <TextField fullWidth size="small" label={f.label} value={form[f.key] || ''}
                      onChange={e => setForm(p => ({ ...p, [f.key]: e.target.value }))}
                      multiline={f.multiline} rows={f.multiline ? 2 : 1}
                      inputProps={f.numeric ? { inputMode: 'numeric' } : {}} />
                }
              </Grid>
            ))}
          </Grid>
        </DialogContent>
        <DialogActions sx={{ px: 3, py: 2 }}>
          <Button onClick={() => setOpen(false)} variant="outlined">Cancel</Button>
          <Button onClick={handleSave} variant="contained" disabled={saving}>
            {saving ? <CircularProgress size={20} /> : (editing ? 'Update' : 'Save')}
          </Button>
        </DialogActions>
      </Dialog>
    </Box>
  );
};
