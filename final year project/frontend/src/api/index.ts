import axios from 'axios';

const API_BASE = process.env.REACT_APP_API_URL || 'http://localhost:8080/api';

const api = axios.create({ baseURL: API_BASE, headers: { 'Content-Type': 'application/json' } });

api.interceptors.request.use((config) => {
  const token = localStorage.getItem('token');
  if (token) config.headers.Authorization = `Bearer ${token}`;
  return config;
});

api.interceptors.response.use(
  (res) => res,
  (err) => {
    if (err.response?.status === 401) {
      localStorage.clear();
      window.location.href = '/login';
    }
    return Promise.reject(err);
  }
);

export default api;

// ─── AUTH ─────────────────────────────────────────────────────────────────────
export const authApi = {
  login: (data: { uid: string; password: string }) => api.post('/auth/login', data),
};

// ─── USERS ────────────────────────────────────────────────────────────────────
export const userApi = {
  getAll: () => api.get('/users'),
  getByUid: (uid: string) => api.get(`/users/${uid}`),
  add: (data: any) => api.post('/users', data),
  update: (uid: string, data: any) => api.put(`/users/${uid}`, data),
  delete: (uid: string) => api.delete(`/users/${uid}`),
};

// ─── MASTERS ──────────────────────────────────────────────────────────────────
export const masterApi = {
  banks:        { getAll: () => api.get('/masters/banks'),        add: (d:any) => api.post('/masters/banks', d),        update: (id:string, d:any) => api.put(`/masters/banks/${id}`, d),        delete: (id:string) => api.delete(`/masters/banks/${id}`) },
  categories:   { getAll: () => api.get('/masters/categories'),   add: (d:any) => api.post('/masters/categories', d),   update: (id:string, d:any) => api.put(`/masters/categories/${id}`, d),   delete: (id:string) => api.delete(`/masters/categories/${id}`) },
  companies:    { getAll: () => api.get('/masters/companies'),    add: (d:any) => api.post('/masters/companies', d),    update: (id:string, d:any) => api.put(`/masters/companies/${id}`, d),    delete: (id:string) => api.delete(`/masters/companies/${id}`) },
  clients:      { getAll: () => api.get('/masters/clients'),      add: (d:any) => api.post('/masters/clients', d),      update: (id:string, d:any) => api.put(`/masters/clients/${id}`, d),      delete: (id:string) => api.delete(`/masters/clients/${id}`) },
  currencies:   { getAll: () => api.get('/masters/currencies'),   add: (d:any) => api.post('/masters/currencies', d),   update: (id:string, d:any) => api.put(`/masters/currencies/${id}`, d),   delete: (id:string) => api.delete(`/masters/currencies/${id}`) },
  receiptNames: { getAll: () => api.get('/masters/receipt-names'),add: (d:any) => api.post('/masters/receipt-names',d), update: (id:string,d:any) => api.put(`/masters/receipt-names/${id}`,d), delete: (id:string) => api.delete(`/masters/receipt-names/${id}`) },
  receiptTypes: { getAll: () => api.get('/masters/receipt-types'),add: (d:any) => api.post('/masters/receipt-types',d), update: (id:string,d:any) => api.put(`/masters/receipt-types/${id}`,d), delete: (id:string) => api.delete(`/masters/receipt-types/${id}`) },
  suppliers:    { getAll: () => api.get('/masters/suppliers'),    add: (d:any) => api.post('/masters/suppliers', d),    update: (id:string, d:any) => api.put(`/masters/suppliers/${id}`, d),    delete: (id:string) => api.delete(`/masters/suppliers/${id}`) },
  transactions: { getAll: () => api.get('/masters/transactions'), add: (d:any) => api.post('/masters/transactions', d), update: (id:string, d:any) => api.put(`/masters/transactions/${id}`, d), delete: (id:string) => api.delete(`/masters/transactions/${id}`),
    getByCat: (cat: string) => api.get(`/masters/transactions/by-category/${cat}`) },
};

// ─── INVOICES ─────────────────────────────────────────────────────────────────
export const invoiceApi = {
  purchase: {
    getAll: () => api.get('/purchase-invoices'),
    save:   (d:any) => api.post('/purchase-invoices', d),
    docNo:  () => api.get('/purchase-invoices/doc-no'),
    alerts: () => api.get('/purchase-invoices/payment-alerts'),
    overdue:() => api.get('/purchase-invoices/overdue'),
  },
  export: {
    getAll:         () => api.get('/export-invoices'),
    save:           (d:any) => api.post('/export-invoices', d),
    docNo:          () => api.get('/export-invoices/doc-no'),
    openInvoiceNos: () => api.get('/export-invoices/open-invoice-nos'),
    openDbkNos:     () => api.get('/export-invoices/open-dbk-invoice-nos'),
    openMeisNos:    () => api.get('/export-invoices/open-meis-invoice-nos'),
  },
  retail: {
    getAll:         () => api.get('/retail-invoices'),
    save:           (d:any) => api.post('/retail-invoices', d),
    docNo:          () => api.get('/retail-invoices/doc-no'),
    openInvoiceNos: () => api.get('/retail-invoices/open-invoice-nos'),
  },
  debitNote: {
    getAll:              () => api.get('/debit-notes'),
    save:                (d:any) => api.post('/debit-notes', d),
    docNo:               () => api.get('/debit-notes/doc-no'),
    invoiceNosBySupplier:(sup:string) => api.get(`/debit-notes/invoice-nos-by-supplier/${encodeURIComponent(sup)}`),
  },
};

// ─── RECEIPTS ─────────────────────────────────────────────────────────────────
export const receiptApi = {
  general: { getAll: () => api.get('/receipts'),        save: (d:any) => api.post('/receipts', d),        docNo: () => api.get('/receipts/doc-no') },
  retail:  { getAll: () => api.get('/retail-receipts'), save: (d:any) => api.post('/retail-receipts', d), docNo: () => api.get('/retail-receipts/doc-no') },
  export:  { getAll: () => api.get('/export-receipts'), save: (d:any) => api.post('/export-receipts', d), docNo: () => api.get('/export-receipts/doc-no') },
  incentives: { getAll: () => api.get('/incentives'),   save: (d:any) => api.post('/incentives', d),      docNo: () => api.get('/incentives/doc-no') },
};

// ─── PAYMENTS ─────────────────────────────────────────────────────────────────
export const paymentApi = {
  getAll: () => api.get('/payments'),
  save:   (d:any) => api.post('/payments', d),
  docNo:  () => api.get('/payments/doc-no'),
};

// ─── DASHBOARD ────────────────────────────────────────────────────────────────
export const dashboardApi = {
  summary:         () => api.get('/dashboard/summary'),
  paymentAlerts:   () => api.get('/dashboard/payment-alerts'),
  bankBalances:    () => api.get('/dashboard/bank-balances'),
  bankBalance:     (accNo:string) => api.get(`/dashboard/bank-balance/${encodeURIComponent(accNo)}`),
  supplierCredits: () => api.get('/dashboard/supplier-credits'),
  supplierCredit:  (name:string) => api.get(`/dashboard/supplier-credit/${encodeURIComponent(name)}`),
};

// ─── REPORTS ──────────────────────────────────────────────────────────────────
export const reportApi = {
  payments:        (f:any) => api.post('/reports/payments', f),
  receipts:        (f:any) => api.post('/reports/receipts', f),
  purchaseInvoices:(f:any) => api.post('/reports/purchase-invoices', f),
  dueDateCrossed:  (f:any) => api.post('/reports/due-date-crossed', f),
  exportInvoices:  (f:any) => api.post('/reports/export-invoices', f),
  retailInvoices:  (f:any) => api.post('/reports/retail-invoices', f),
  retailReceipts:  (f:any) => api.post('/reports/retail-receipts', f),
  exportReceipts:  (f:any) => api.post('/reports/export-receipts', f),
  debitNotes:      (f:any) => api.post('/reports/debit-notes', f),
  incentives:      (f:any) => api.post('/reports/incentives', f),
  supplierAdvance: (f:any) => api.post('/reports/supplier-advance', f),
  supplierCredit:  (f:any) => api.post('/reports/supplier-credit', f),
  supplierLedger:  (sup:string, f:any) => api.post(`/reports/supplier-ledger/${encodeURIComponent(sup)}`, f),
  ledgerSuppliers: () => api.get('/reports/supplier-ledger/suppliers'),
  paymentFilters:  () => api.get('/reports/filter-options/payments'),
  receiptFilters:  () => api.get('/reports/filter-options/receipts'),
  masterBanks:        () => api.get('/reports/masters/banks'),
  masterCategories:   () => api.get('/reports/masters/categories'),
  masterClients:      () => api.get('/reports/masters/clients'),
  masterCompanies:    () => api.get('/reports/masters/companies'),
  masterCurrencies:   () => api.get('/reports/masters/currencies'),
  masterReceiptNames: () => api.get('/reports/masters/receipt-names'),
  masterReceiptTypes: () => api.get('/reports/masters/receipt-types'),
  masterSuppliers:    () => api.get('/reports/masters/suppliers'),
  masterTransactions: () => api.get('/reports/masters/transactions'),
};
