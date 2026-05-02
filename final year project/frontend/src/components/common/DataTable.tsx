import React from 'react';
import { Table, TableBody, TableCell, TableContainer, TableHead, TableRow, Paper, IconButton, Tooltip, Chip, Typography, Box } from '@mui/material';
import { Edit, Delete } from '@mui/icons-material';

interface Column { key: string; label: string; render?: (val: any, row: any) => React.ReactNode; }

interface Props {
  columns: Column[];
  rows: any[];
  onEdit?: (row: any) => void;
  onDelete?: (row: any) => void;
  emptyMessage?: string;
}

export default function DataTable({ columns, rows, onEdit, onDelete, emptyMessage = 'No records found.' }: Props) {
  if (rows.length === 0) {
    return (
      <Box sx={{ textAlign: 'center', py: 6, color: 'text.secondary' }}>
        <Typography variant="body1">{emptyMessage}</Typography>
      </Box>
    );
  }
  return (
    <TableContainer component={Paper} elevation={0} sx={{ border: '1px solid #e0e0e0', borderRadius: 2, overflowX: 'auto' }}>
      <Table size="small" stickyHeader>
        <TableHead>
          <TableRow>
            <TableCell sx={{ width: 40 }}>#</TableCell>
            {columns.map(c => <TableCell key={c.key}>{c.label}</TableCell>)}
            {(onEdit || onDelete) && <TableCell align="center" sx={{ width: 90 }}>Actions</TableCell>}
          </TableRow>
        </TableHead>
        <TableBody>
          {rows.map((row, idx) => (
            <TableRow key={row.id || idx} hover>
              <TableCell sx={{ color: 'text.secondary', fontSize: 12 }}>{idx + 1}</TableCell>
              {columns.map(c => (
                <TableCell key={c.key}>
                  {c.render ? c.render(row[c.key], row) : (row[c.key] ?? '—')}
                </TableCell>
              ))}
              {(onEdit || onDelete) && (
                <TableCell align="center">
                  {onEdit && <Tooltip title="Edit"><IconButton size="small" color="primary" onClick={() => onEdit(row)}><Edit fontSize="small" /></IconButton></Tooltip>}
                  {onDelete && <Tooltip title="Delete"><IconButton size="small" color="error" onClick={() => onDelete(row)}><Delete fontSize="small" /></IconButton></Tooltip>}
                </TableCell>
              )}
            </TableRow>
          ))}
        </TableBody>
      </Table>
    </TableContainer>
  );
}
