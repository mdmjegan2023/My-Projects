import React, { useState } from 'react';
import { Box, Card, CardContent, TextField, Button, Typography, CircularProgress, InputAdornment, IconButton, Alert } from '@mui/material';
import { Visibility, VisibilityOff, LocalAtm } from '@mui/icons-material';
import { authApi } from '../api';
import { useAuth } from '../context/AuthContext';
import toast from 'react-hot-toast';

export default function LoginPage() {
  const { login } = useAuth();
  const [uid, setUid] = useState('');
  const [password, setPassword] = useState('');
  const [showPwd, setShowPwd] = useState(false);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState('');

  const handleLogin = async (e: React.FormEvent) => {
    e.preventDefault();
    if (!uid || !password) { setError('Enter User ID & Password.'); return; }
    setLoading(true); setError('');
    try {
      const res = await authApi.login({ uid, password });
      login(res.data.data);
      toast.success(`Welcome, ${res.data.data.username}!`);
    } catch (err: any) {
      const msg = err.response?.data?.message || 'Username and Password is incorrect!';
      setError(msg);
    } finally {
      setLoading(false);
    }
  };

  return (
    <Box sx={{ minHeight: '100vh', display: 'flex', alignItems: 'center', justifyContent: 'center', background: 'linear-gradient(135deg, #0D2E6B 0%, #1565C0 50%, #0288D1 100%)' }}>
      <Box sx={{ position: 'absolute', inset: 0, backgroundImage: 'radial-gradient(circle at 20% 50%, rgba(255,255,255,0.05) 0%, transparent 50%), radial-gradient(circle at 80% 20%, rgba(255,255,255,0.05) 0%, transparent 40%)' }} />
      <Card sx={{ width: '100%', maxWidth: 420, mx: 2, borderRadius: 3, boxShadow: '0 24px 80px rgba(0,0,0,0.4)', position: 'relative' }}>
        <CardContent sx={{ p: 4 }}>
          <Box sx={{ textAlign: 'center', mb: 4 }}>
            <Box sx={{ width: 60, height: 60, borderRadius: 3, background: 'linear-gradient(135deg,#1565C0,#0288D1)', display: 'flex', alignItems: 'center', justifyContent: 'center', mx: 'auto', mb: 2, boxShadow: '0 8px 24px rgba(21,101,192,0.4)' }}>
              <LocalAtm sx={{ color: '#fff', fontSize: 32 }} />
            </Box>
            <Typography variant="h5" fontWeight={800} color="primary.main">FundTracker</Typography>
            <Typography variant="body2" color="text.secondary" mt={0.5}>Enterprise Edition — Sign in to continue</Typography>
          </Box>

          {error && <Alert severity="error" sx={{ mb: 2, borderRadius: 2 }}>{error}</Alert>}

          <Box component="form" onSubmit={handleLogin}>
            <TextField fullWidth label="User ID" value={uid} onChange={e => setUid(e.target.value)}
              inputProps={{ inputMode: 'numeric', pattern: '[0-9]*' }}
              sx={{ mb: 2 }} autoFocus />
            <TextField fullWidth label="Password" type={showPwd ? 'text' : 'password'} value={password} onChange={e => setPassword(e.target.value)}
              InputProps={{ endAdornment: <InputAdornment position="end"><IconButton onClick={() => setShowPwd(!showPwd)} edge="end">{showPwd ? <VisibilityOff /> : <Visibility />}</IconButton></InputAdornment> }}
              sx={{ mb: 3 }} />
            <Button fullWidth variant="contained" type="submit" size="large" disabled={loading}
              sx={{ py: 1.4, fontSize: 16, fontWeight: 700, borderRadius: 2, background: 'linear-gradient(135deg,#1565C0,#0288D1)', boxShadow: '0 6px 20px rgba(21,101,192,0.4)' }}>
              {loading ? <CircularProgress size={24} sx={{ color: '#fff' }} /> : 'Login'}
            </Button>
          </Box>
        </CardContent>
      </Card>
    </Box>
  );
}
