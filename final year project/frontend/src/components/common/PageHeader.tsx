import React from 'react';
import { Box, Typography, Button, Breadcrumbs, Link } from '@mui/material';
import { Add, NavigateNext } from '@mui/icons-material';

interface Props {
  title: string;
  subtitle?: string;
  breadcrumbs?: { label: string; href?: string }[];
  onAdd?: () => void;
  addLabel?: string;
  extra?: React.ReactNode;
}

export default function PageHeader({ title, subtitle, breadcrumbs, onAdd, addLabel = 'Add New', extra }: Props) {
  return (
    <Box sx={{ mb: 3 }}>
      {breadcrumbs && (
        <Breadcrumbs separator={<NavigateNext fontSize="small" />} sx={{ mb: 1 }}>
          {breadcrumbs.map((b, i) =>
            b.href
              ? <Link key={i} href={b.href} underline="hover" color="inherit" variant="body2">{b.label}</Link>
              : <Typography key={i} variant="body2" color="text.primary">{b.label}</Typography>
          )}
        </Breadcrumbs>
      )}
      <Box sx={{ display: 'flex', alignItems: 'center', justifyContent: 'space-between', flexWrap: 'wrap', gap: 1 }}>
        <Box>
          <Typography variant="h5" fontWeight={800}>{title}</Typography>
          {subtitle && <Typography variant="body2" color="text.secondary">{subtitle}</Typography>}
        </Box>
        <Box sx={{ display: 'flex', gap: 1 }}>
          {extra}
          {onAdd && (
            <Button variant="contained" startIcon={<Add />} onClick={onAdd}
              sx={{ borderRadius: 2, boxShadow: '0 4px 12px rgba(21,101,192,0.3)' }}>
              {addLabel}
            </Button>
          )}
        </Box>
      </Box>
    </Box>
  );
}
