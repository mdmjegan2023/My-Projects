// ─── AUTH ─────────────────────────────────────────────────────────────────────
export interface LoginRequest { uid: string; password: string; }
export interface JwtResponse { token: string; type: string; uid: string; username: string; userType: string; }
export interface ApiResponse<T> { success: boolean; message: string; data: T; }

// ─── USER ─────────────────────────────────────────────────────────────────────
export interface User { id?: string; uid: string; username: string; password?: string; userType: string; remarks?: string; }

// ─── MASTERS ──────────────────────────────────────────────────────────────────
export interface BankMaster { id?: string; bankCode: string; bankName: string; bankBranch: string; accType: string; accNo: string; ifscCode?: string; swiftCode?: string; remarks?: string; }
export interface CategoryMaster { id?: string; catCode: string; catName: string; remarks?: string; }
export interface CompanyMaster { id?: string; compCode: string; compName: string; compAddress?: string; compPhone?: string; compEmail?: string; taxType?: string; taxNo?: string; remarks?: string; }
export interface ClientMaster { id?: string; clientCode: string; clientName: string; clientAddress?: string; clientPhone?: string; clientEmail?: string; taxType?: string; taxNo?: string; remarks?: string; }
export interface CurrencyMaster { id?: string; currCode: string; currName: string; remarks?: string; }
export interface ReceiptNameMaster { id?: string; receiptCode: string; receiptName: string; remarks?: string; }
export interface ReceiptTypeMaster { id?: string; receiptTypeCode: string; receiptTypeName: string; catName?: string; remarks?: string; }
export interface SupplierMaster { id?: string; supCode: string; supName: string; supAddress?: string; supPhone?: string; supEmail?: string; taxType?: string; taxNo?: string; dueDays?: string; catName?: string; remarks?: string; }
export interface TransactionMaster { id?: string; transCode: string; transName: string; catName?: string; remarks?: string; }

// ─── INVOICES ─────────────────────────────────────────────────────────────────
export interface PurchaseInvoice { id?: string; iPurCode?: string; entryDate?: string; company: string; catName: string; invoiceNo: string; invoiceDate: string; supName: string; supAddress?: string; supPhone?: string; supEmail?: string; invoiceAmount: number; supAdvance?: number; invoiceDueDate?: string; remarks?: string; }
export interface ExportInvoice { id?: string; iExpCode?: string; entryDate?: string; invoiceNo: string; invoiceDate: string; clientName: string; poNo?: string; totalPair: number; otrCurrencyType?: string; otrExchangeRate?: number; otrAmount?: number; inrCurrencyType?: string; inrExchangeRate: number; inrAmount: number; sgstPercent?: number; sgstValue?: number; cgstPercent?: number; cgstValue?: number; igstPercent?: number; igstValue?: number; status?: string; remarks?: string; }
export interface RetailInvoice { id?: string; iRtlCode?: string; entryDate?: string; invoiceNo: string; invoiceDate: string; clientName: string; poNo?: string; totalPair: number; inrCurrencyType?: string; inrExchangeRate: number; inrAmount: number; sgstPercent?: number; sgstValue?: number; cgstPercent?: number; cgstValue?: number; igstPercent?: number; igstValue?: number; status?: string; remarks?: string; }
export interface DebitNote { id?: string; iDbtCode?: string; entryDate?: string; invoiceNo: string; supName: string; supAddress?: string; supPhone?: string; supEmail?: string; company?: string; catName?: string; billInvoiceAmount?: number; supDebit: number; invoiceAmount: number; invoiceDate?: string; invoiceDueDate?: string; remarks?: string; }

// ─── RECEIPTS ─────────────────────────────────────────────────────────────────
export interface Receipt { id?: string; docNo?: string; entryDate?: string; receiptDate: string; receiptName: string; receiptType: string; company: string; description?: string; amount: number; transMode: string; bankName: string; accNo: string; accType: string; bankBranch?: string; ifscCode?: string; swiftCode?: string; remarks?: string; }
export interface RetailReceipt { id?: string; rRtlCode?: string; entryDate?: string; invoiceType: string; discountPercent?: number; invoiceNo: string; invoiceDate?: string; clientName?: string; poNo?: string; totalPair: number; inrCurrencyType?: string; inrExchangeRate?: number; inrAmount: number; sgstPercent?: number; sgstValue?: number; cgstPercent?: number; cgstValue?: number; igstPercent?: number; igstValue?: number; transMode: string; bankName: string; accNo: string; accType: string; bankBranch?: string; ifscCode?: string; swiftCode?: string; status?: string; remarks?: string; }
export interface ExportReceipt { id?: string; rExpCode?: string; entryDate?: string; invoiceType: string; discountPercent?: number; invoiceNo: string; invoiceDate?: string; clientName?: string; poNo?: string; totalPair: number; otrCurrencyType?: string; otrExchangeRate?: number; otrAmount?: number; inrCurrencyType?: string; inrExchangeRate?: number; inrAmount: number; sgstPercent?: number; sgstValue?: number; cgstPercent?: number; cgstValue?: number; igstPercent?: number; igstValue?: number; transMode: string; bankName: string; accNo: string; accType: string; bankBranch?: string; ifscCode?: string; swiftCode?: string; status?: string; remarks?: string; }
export interface SalexportIncentives { id?: string; docNo?: string; entryDate?: string; receiptDate: string; incentivesType: string; invoiceNo: string; description?: string; amount: number; transMode: string; bankName: string; accNo: string; accType: string; bankBranch?: string; ifscCode?: string; swiftCode?: string; remarks?: string; }

// ─── PAYMENTS ─────────────────────────────────────────────────────────────────
export interface Payment { id?: string; pPayCode?: string; entryDate?: string; paymentDate: string; paymentName: string; paymentType: string; supName: string; supAddress?: string; supPhone?: string; supEmail?: string; supCredit?: number; paidAmount: number; transMode: string; description?: string; bankName: string; accNo: string; accType: string; bankBranch?: string; ifscCode?: string; swiftCode?: string; remarks?: string; }

// ─── DASHBOARD ────────────────────────────────────────────────────────────────
export interface BankBalance { id?: string; bankName: string; accNo: string; accType: string; bankBranch?: string; ifscCode?: string; swiftCode?: string; balance: number; }
export interface SupplierCreditOpenClose { id?: string; supName: string; supAddress?: string; supPhone?: string; supEmail?: string; supCredit: number; }
export interface DashboardSummary { paymentAlerts: PurchaseInvoice[]; totalSuppliers: number; totalPurchaseInvoices: number; totalExportInvoices: number; totalRetailInvoices: number; totalPayments: number; totalReceipts: number; overdueBills: number; }

// ─── REPORTS ──────────────────────────────────────────────────────────────────
export interface ReportFilterRequest { fromDate?: string; toDate?: string; secondFromDate?: string; secondToDate?: string; filterType?: string; filterValue?: string; secondFilterValue?: string; }
export interface SupplierLedger { id?: string; entryDate?: string; invoiceDate?: string; paymentDate?: string; company?: string; supName: string; transMode?: string; description?: string; bankName?: string; accNo?: string; debit?: number; credit?: number; clsBal?: number; remarks?: string; }
export interface SupplierAdvance { id?: string; pAdvCode?: string; entryDate?: string; supName: string; supAddress?: string; supPhone?: string; supEmail?: string; supAdvance: number; transMode?: string; bankName?: string; accNo?: string; accType?: string; bankBranch?: string; remarks?: string; }
