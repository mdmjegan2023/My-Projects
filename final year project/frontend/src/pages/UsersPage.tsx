import React, { useEffect, useState } from 'react';
import { Box, Card, CardContent, Dialog, DialogTitle, DialogContent, DialogActions, Button, TextField, Grid, MenuItem, CircularProgress, Chip } from '@mui/material';
import { userApi } from '../api';
import DataTable from '../components/common/DataTable';
import PageHeader from '../components/common/PageHeader';
import toast from 'react-hot-toast';

const USER_TYPES = ['Administrator','Admin','User'];

export const UsersPage = () => {
  const [rows, setRows]     = useState<any[]>([]);
  const [open, setOpen]     = useState(false);
  const [editing, setEditing] = useState<any>(null);
  const [form, setForm]     = useState<any>({ uid:'', username:'', password:'', userType:'', remarks:'' });
  const [loading, setLoading] = useState(true);
  const [saving, setSaving]   = useState(false);

  const load = () => { userApi.getAll().then(r => setRows(r.data.data)).finally(() => setLoading(false)); };
  useEffect(() => { load(); }, []);
  const set = (k: string, v: any) => setForm((f: any) => ({ ...f, [k]: v }));

  const openAdd  = () => { setEditing(null); setForm({ uid:'', username:'', password:'', userType:'', remarks:'' }); setOpen(true); };
  const openEdit = (row: any) => { setEditing(row); setForm({ ...row, password:'' }); setOpen(true); };

  const handleSave = async () => {
    if (!form.uid || !form.username || !form.userType || (!editing && !form.password)) {
      toast.error("Please Fill The Detail's!"); return;
    }
    setSaving(true);
    try {
      if (editing) { await userApi.update(editing.uid, form); toast.success('User Updated.'); }
      else         { await userApi.add(form); toast.success('New User Added.'); }
      setOpen(false); load();
    } catch (e: any) { toast.error(e.response?.data?.message || 'Operation failed.'); }
    finally { setSaving(false); }
  };

  const handleDelete = async (row: any) => {
    if (!window.confirm(`Delete user "${row.username}"?`)) return;
    try { await userApi.delete(row.uid); toast.success('User Deleted.'); load(); }
    catch (e: any) { toast.error(e.response?.data?.message || 'Delete failed.'); }
  };

  const typeColor: Record<string,any> = { Administrator:'error', Admin:'warning', User:'default' };

  return (
    <Box>
      <PageHeader title="User Registration" subtitle="Manage system users" onAdd={openAdd} addLabel="Add User" breadcrumbs={[{ label: 'User Registration' }]} />
      <Card>
        <CardContent>
          {loading ? <Box sx={{ textAlign:'center', py:4 }}><CircularProgress /></Box> : (
            <DataTable rows={rows} onEdit={openEdit} onDelete={handleDelete} columns={[
              { key:'uid', label:'User ID' },
              { key:'username', label:'Username' },
              { key:'userType', label:'User Type', render:(v:any) => <Chip label={v} size="small" color={typeColor[v] || 'default'} /> },
              { key:'remarks', label:'Remarks' },
            ]} />
          )}
        </CardContent>
      </Card>

      <Dialog open={open} onClose={() => setOpen(false)} maxWidth="sm" fullWidth>
        <DialogTitle sx={{ fontWeight:700 }}>{editing ? 'Edit User' : 'Add User'}</DialogTitle>
        <DialogContent dividers>
          <Grid container spacing={2} sx={{ pt:1 }}>
            <Grid item xs={6}><TextField fullWidth size="small" label="User ID *" value={form.uid} onChange={e => set('uid', e.target.value)} disabled={!!editing} inputProps={{ inputMode:'numeric' }} /></Grid>
            <Grid item xs={6}><TextField select fullWidth size="small" label="User Type *" value={form.userType} onChange={e => set('userType', e.target.value)}><MenuItem value="">Select</MenuItem>{USER_TYPES.map(t => <MenuItem key={t} value={t}>{t}</MenuItem>)}</TextField></Grid>
            <Grid item xs={12}><TextField fullWidth size="small" label="Username *" value={form.username} onChange={e => set('username', e.target.value)} /></Grid>
            <Grid item xs={12}><TextField fullWidth size="small" label={editing ? 'Password (leave blank to keep)' : 'Password *'} type="password" value={form.password} onChange={e => set('password', e.target.value)} /></Grid>
            <Grid item xs={12}><TextField fullWidth size="small" label="Remarks" multiline rows={2} value={form.remarks} onChange={e => set('remarks', e.target.value)} /></Grid>
          </Grid>
        </DialogContent>
        <DialogActions sx={{ px:3, py:2 }}>
          <Button onClick={() => setOpen(false)} variant="outlined">Cancel</Button>
          <Button onClick={handleSave} variant="contained" disabled={saving}>{saving ? <CircularProgress size={20}/> : (editing ? 'Update' : 'Save')}</Button>
        </DialogActions>
      </Dialog>
    </Box>
  );
};
