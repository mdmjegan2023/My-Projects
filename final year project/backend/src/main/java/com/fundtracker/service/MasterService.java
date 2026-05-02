package com.fundtracker.service;

import com.fundtracker.exception.DuplicateResourceException;
import com.fundtracker.exception.ResourceNotFoundException;
import com.fundtracker.model.*;
import com.fundtracker.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MasterService {

    @Autowired private BankMasterRepository bankRepo;
    @Autowired private CategoryMasterRepository categoryRepo;
    @Autowired private CompanyMasterRepository companyRepo;
    @Autowired private ClientMasterRepository clientRepo;
    @Autowired private CurrencyMasterRepository currencyRepo;
    @Autowired private ReceiptNameMasterRepository receiptNameRepo;
    @Autowired private ReceiptTypeMasterRepository receiptTypeRepo;
    @Autowired private SupplierMasterRepository supplierRepo;
    @Autowired private TransactionMasterRepository transactionRepo;

    // ─── BANK MASTER ────────────────────────────────────────────────────────────
    public List<BankMaster> getAllBanks() { return bankRepo.findAllByOrderByBankNameAsc(); }

    public BankMaster addBank(BankMaster bank) {
        if (bankRepo.existsByBankCode(bank.getBankCode()))
            throw new DuplicateResourceException("Bank Code Already Exist.");
        if (bankRepo.existsByBankNameAndAccType(bank.getBankName(), bank.getAccType()) ||
                bankRepo.existsByAccNo(bank.getAccNo()))
            throw new DuplicateResourceException("Bank Already Exist.");
        return bankRepo.save(bank);
    }

    public BankMaster updateBank(String id, BankMaster updated) {
        BankMaster existing = bankRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Bank not found."));
        existing.setBankName(updated.getBankName());
        existing.setBankBranch(updated.getBankBranch());
        existing.setAccType(updated.getAccType());
        existing.setAccNo(updated.getAccNo());
        existing.setIfscCode(updated.getIfscCode());
        existing.setSwiftCode(updated.getSwiftCode());
        existing.setRemarks(updated.getRemarks());
        return bankRepo.save(existing);
    }

    public void deleteBank(String id) {
        if (!bankRepo.existsById(id)) throw new ResourceNotFoundException("Bank not found.");
        bankRepo.deleteById(id);
    }

    // ─── CATEGORY MASTER ────────────────────────────────────────────────────────
    public List<CategoryMaster> getAllCategories() { return categoryRepo.findAllByOrderByCatNameAsc(); }

    public CategoryMaster addCategory(CategoryMaster cat) {
        if (categoryRepo.existsByCatCode(cat.getCatCode()))
            throw new DuplicateResourceException("Category Code Already Exist.");
        if (categoryRepo.existsByCatName(cat.getCatName()))
            throw new DuplicateResourceException("Category Name Already Exist.");
        return categoryRepo.save(cat);
    }

    public CategoryMaster updateCategory(String id, CategoryMaster updated) {
        CategoryMaster existing = categoryRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found."));
        existing.setCatName(updated.getCatName());
        existing.setRemarks(updated.getRemarks());
        return categoryRepo.save(existing);
    }

    public void deleteCategory(String id) {
        if (!categoryRepo.existsById(id)) throw new ResourceNotFoundException("Category not found.");
        categoryRepo.deleteById(id);
    }

    // ─── COMPANY MASTER ─────────────────────────────────────────────────────────
    public List<CompanyMaster> getAllCompanies() { return companyRepo.findAllByOrderByCompNameAsc(); }

    public CompanyMaster addCompany(CompanyMaster company) {
        if (companyRepo.existsByCompCode(company.getCompCode()))
            throw new DuplicateResourceException("Company Code Already Exist.");
        if (companyRepo.existsByCompName(company.getCompName()))
            throw new DuplicateResourceException("Company Already Exist.");
        return companyRepo.save(company);
    }

    public CompanyMaster updateCompany(String id, CompanyMaster updated) {
        CompanyMaster existing = companyRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Company not found."));
        existing.setCompName(updated.getCompName());
        existing.setCompAddress(updated.getCompAddress());
        existing.setCompPhone(updated.getCompPhone());
        existing.setCompEmail(updated.getCompEmail());
        existing.setTaxType(updated.getTaxType());
        existing.setTaxNo(updated.getTaxNo());
        existing.setRemarks(updated.getRemarks());
        return companyRepo.save(existing);
    }

    public void deleteCompany(String id) {
        if (!companyRepo.existsById(id)) throw new ResourceNotFoundException("Company not found.");
        companyRepo.deleteById(id);
    }

    // ─── CLIENT MASTER ──────────────────────────────────────────────────────────
    public List<ClientMaster> getAllClients() { return clientRepo.findAllByOrderByClientNameAsc(); }

    public ClientMaster addClient(ClientMaster client) {
        if (clientRepo.existsByClientCode(client.getClientCode()))
            throw new DuplicateResourceException("Client Code Already Exist.");
        if (clientRepo.existsByClientName(client.getClientName()))
            throw new DuplicateResourceException("Client Already Exist.");
        return clientRepo.save(client);
    }

    public ClientMaster updateClient(String id, ClientMaster updated) {
        ClientMaster existing = clientRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Client not found."));
        existing.setClientName(updated.getClientName());
        existing.setClientAddress(updated.getClientAddress());
        existing.setClientPhone(updated.getClientPhone());
        existing.setClientEmail(updated.getClientEmail());
        existing.setTaxType(updated.getTaxType());
        existing.setTaxNo(updated.getTaxNo());
        existing.setRemarks(updated.getRemarks());
        return clientRepo.save(existing);
    }

    public void deleteClient(String id) {
        if (!clientRepo.existsById(id)) throw new ResourceNotFoundException("Client not found.");
        clientRepo.deleteById(id);
    }

    // ─── CURRENCY MASTER ────────────────────────────────────────────────────────
    public List<CurrencyMaster> getAllCurrencies() { return currencyRepo.findAllByOrderByCurrNameAsc(); }

    public CurrencyMaster addCurrency(CurrencyMaster currency) {
        if (currencyRepo.existsByCurrCode(currency.getCurrCode()))
            throw new DuplicateResourceException("Currency Code Already Exist.");
        if (currencyRepo.existsByCurrName(currency.getCurrName()))
            throw new DuplicateResourceException("Currency Already Exist.");
        return currencyRepo.save(currency);
    }

    public CurrencyMaster updateCurrency(String id, CurrencyMaster updated) {
        CurrencyMaster existing = currencyRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Currency not found."));
        existing.setCurrName(updated.getCurrName());
        existing.setRemarks(updated.getRemarks());
        return currencyRepo.save(existing);
    }

    public void deleteCurrency(String id) {
        if (!currencyRepo.existsById(id)) throw new ResourceNotFoundException("Currency not found.");
        currencyRepo.deleteById(id);
    }

    // ─── RECEIPT NAME MASTER ────────────────────────────────────────────────────
    public List<ReceiptNameMaster> getAllReceiptNames() { return receiptNameRepo.findAllByOrderByReceiptNameAsc(); }

    public ReceiptNameMaster addReceiptName(ReceiptNameMaster rn) {
        if (receiptNameRepo.existsByReceiptCode(rn.getReceiptCode()))
            throw new DuplicateResourceException("Receipt Code Already Exist.");
        if (receiptNameRepo.existsByReceiptName(rn.getReceiptName()))
            throw new DuplicateResourceException("Receipt Name Already Exist.");
        return receiptNameRepo.save(rn);
    }

    public ReceiptNameMaster updateReceiptName(String id, ReceiptNameMaster updated) {
        ReceiptNameMaster existing = receiptNameRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Receipt Name not found."));
        existing.setReceiptName(updated.getReceiptName());
        existing.setRemarks(updated.getRemarks());
        return receiptNameRepo.save(existing);
    }

    public void deleteReceiptName(String id) {
        if (!receiptNameRepo.existsById(id)) throw new ResourceNotFoundException("Receipt Name not found.");
        receiptNameRepo.deleteById(id);
    }

    // ─── RECEIPT TYPE MASTER ────────────────────────────────────────────────────
    public List<ReceiptTypeMaster> getAllReceiptTypes() { return receiptTypeRepo.findAllByOrderByReceiptTypeNameAsc(); }
    public List<ReceiptTypeMaster> getReceiptTypesByCat(String catName) { return receiptTypeRepo.findByCatName(catName); }

    public ReceiptTypeMaster addReceiptType(ReceiptTypeMaster rt) {
        if (receiptTypeRepo.existsByReceiptTypeCode(rt.getReceiptTypeCode()))
            throw new DuplicateResourceException("Receipt Type Code Already Exist.");
        if (receiptTypeRepo.existsByReceiptTypeName(rt.getReceiptTypeName()))
            throw new DuplicateResourceException("Receipt Type Already Exist.");
        return receiptTypeRepo.save(rt);
    }

    public ReceiptTypeMaster updateReceiptType(String id, ReceiptTypeMaster updated) {
        ReceiptTypeMaster existing = receiptTypeRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Receipt Type not found."));
        existing.setReceiptTypeName(updated.getReceiptTypeName());
        existing.setCatName(updated.getCatName());
        existing.setRemarks(updated.getRemarks());
        return receiptTypeRepo.save(existing);
    }

    public void deleteReceiptType(String id) {
        if (!receiptTypeRepo.existsById(id)) throw new ResourceNotFoundException("Receipt Type not found.");
        receiptTypeRepo.deleteById(id);
    }

    // ─── SUPPLIER MASTER ────────────────────────────────────────────────────────
    public List<SupplierMaster> getAllSuppliers() { return supplierRepo.findAllByOrderBySupNameAsc(); }

    public SupplierMaster addSupplier(SupplierMaster supplier) {
        if (supplierRepo.existsBySupCode(supplier.getSupCode()))
            throw new DuplicateResourceException("Supplier Code Already Exist.");
        if (supplierRepo.existsBySupName(supplier.getSupName()))
            throw new DuplicateResourceException("Supplier Already Exist.");
        return supplierRepo.save(supplier);
    }

    public SupplierMaster updateSupplier(String id, SupplierMaster updated) {
        SupplierMaster existing = supplierRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Supplier not found."));
        existing.setSupName(updated.getSupName());
        existing.setSupAddress(updated.getSupAddress());
        existing.setSupPhone(updated.getSupPhone());
        existing.setSupEmail(updated.getSupEmail());
        existing.setTaxType(updated.getTaxType());
        existing.setTaxNo(updated.getTaxNo());
        existing.setDueDays(updated.getDueDays());
        existing.setCatName(updated.getCatName());
        existing.setRemarks(updated.getRemarks());
        return supplierRepo.save(existing);
    }

    public void deleteSupplier(String id) {
        if (!supplierRepo.existsById(id)) throw new ResourceNotFoundException("Supplier not found.");
        supplierRepo.deleteById(id);
    }

    // ─── TRANSACTION MASTER ─────────────────────────────────────────────────────
    public List<TransactionMaster> getAllTransactions() { return transactionRepo.findAllByOrderByTransNameAsc(); }
    public List<TransactionMaster> getTransactionsByCat(String catName) { return transactionRepo.findByCatName(catName); }

    public TransactionMaster addTransaction(TransactionMaster trans) {
        if (transactionRepo.existsByTransCode(trans.getTransCode()))
            throw new DuplicateResourceException("Transaction Code Already Exist.");
        if (transactionRepo.existsByTransName(trans.getTransName()))
            throw new DuplicateResourceException("Transaction Already Exist.");
        return transactionRepo.save(trans);
    }

    public TransactionMaster updateTransaction(String id, TransactionMaster updated) {
        TransactionMaster existing = transactionRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Transaction not found."));
        existing.setTransName(updated.getTransName());
        existing.setCatName(updated.getCatName());
        existing.setRemarks(updated.getRemarks());
        return transactionRepo.save(existing);
    }

    public void deleteTransaction(String id) {
        if (!transactionRepo.existsById(id)) throw new ResourceNotFoundException("Transaction not found.");
        transactionRepo.deleteById(id);
    }
}
