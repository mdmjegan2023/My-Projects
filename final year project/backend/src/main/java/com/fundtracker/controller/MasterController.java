package com.fundtracker.controller;

import com.fundtracker.dto.ApiResponse;
import com.fundtracker.model.*;
import com.fundtracker.service.MasterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/masters")
public class MasterController {

    @Autowired private MasterService masterService;

    // ─── BANK ────────────────────────────────────────────────────────────────────
    @GetMapping("/banks")
    public ResponseEntity<ApiResponse<List<BankMaster>>> getAllBanks() {
        return ResponseEntity.ok(ApiResponse.success(masterService.getAllBanks()));
    }
    @PostMapping("/banks")
    public ResponseEntity<ApiResponse<BankMaster>> addBank(@RequestBody BankMaster bank) {
        return ResponseEntity.ok(ApiResponse.success("New Bank Added.", masterService.addBank(bank)));
    }
    @PutMapping("/banks/{id}")
    public ResponseEntity<ApiResponse<BankMaster>> updateBank(@PathVariable String id, @RequestBody BankMaster bank) {
        return ResponseEntity.ok(ApiResponse.success("Bank Updated.", masterService.updateBank(id, bank)));
    }
    @DeleteMapping("/banks/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteBank(@PathVariable String id) {
        masterService.deleteBank(id);
        return ResponseEntity.ok(ApiResponse.successVoid("Bank Deleted."));
    }

    // ─── CATEGORY ────────────────────────────────────────────────────────────────
    @GetMapping("/categories")
    public ResponseEntity<ApiResponse<List<CategoryMaster>>> getAllCategories() {
        return ResponseEntity.ok(ApiResponse.success(masterService.getAllCategories()));
    }
    @PostMapping("/categories")
    public ResponseEntity<ApiResponse<CategoryMaster>> addCategory(@RequestBody CategoryMaster cat) {
        return ResponseEntity.ok(ApiResponse.success("Category Added.", masterService.addCategory(cat)));
    }
    @PutMapping("/categories/{id}")
    public ResponseEntity<ApiResponse<CategoryMaster>> updateCategory(@PathVariable String id, @RequestBody CategoryMaster cat) {
        return ResponseEntity.ok(ApiResponse.success("Category Updated.", masterService.updateCategory(id, cat)));
    }
    @DeleteMapping("/categories/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteCategory(@PathVariable String id) {
        masterService.deleteCategory(id);
        return ResponseEntity.ok(ApiResponse.successVoid("Category Deleted."));
    }

    // ─── COMPANY ─────────────────────────────────────────────────────────────────
    @GetMapping("/companies")
    public ResponseEntity<ApiResponse<List<CompanyMaster>>> getAllCompanies() {
        return ResponseEntity.ok(ApiResponse.success(masterService.getAllCompanies()));
    }
    @PostMapping("/companies")
    public ResponseEntity<ApiResponse<CompanyMaster>> addCompany(@RequestBody CompanyMaster company) {
        return ResponseEntity.ok(ApiResponse.success("Company Added.", masterService.addCompany(company)));
    }
    @PutMapping("/companies/{id}")
    public ResponseEntity<ApiResponse<CompanyMaster>> updateCompany(@PathVariable String id, @RequestBody CompanyMaster company) {
        return ResponseEntity.ok(ApiResponse.success("Company Updated.", masterService.updateCompany(id, company)));
    }
    @DeleteMapping("/companies/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteCompany(@PathVariable String id) {
        masterService.deleteCompany(id);
        return ResponseEntity.ok(ApiResponse.successVoid("Company Deleted."));
    }

    // ─── CLIENT ──────────────────────────────────────────────────────────────────
    @GetMapping("/clients")
    public ResponseEntity<ApiResponse<List<ClientMaster>>> getAllClients() {
        return ResponseEntity.ok(ApiResponse.success(masterService.getAllClients()));
    }
    @PostMapping("/clients")
    public ResponseEntity<ApiResponse<ClientMaster>> addClient(@RequestBody ClientMaster client) {
        return ResponseEntity.ok(ApiResponse.success("Client Added.", masterService.addClient(client)));
    }
    @PutMapping("/clients/{id}")
    public ResponseEntity<ApiResponse<ClientMaster>> updateClient(@PathVariable String id, @RequestBody ClientMaster client) {
        return ResponseEntity.ok(ApiResponse.success("Client Updated.", masterService.updateClient(id, client)));
    }
    @DeleteMapping("/clients/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteClient(@PathVariable String id) {
        masterService.deleteClient(id);
        return ResponseEntity.ok(ApiResponse.successVoid("Client Deleted."));
    }

    // ─── CURRENCY ────────────────────────────────────────────────────────────────
    @GetMapping("/currencies")
    public ResponseEntity<ApiResponse<List<CurrencyMaster>>> getAllCurrencies() {
        return ResponseEntity.ok(ApiResponse.success(masterService.getAllCurrencies()));
    }
    @PostMapping("/currencies")
    public ResponseEntity<ApiResponse<CurrencyMaster>> addCurrency(@RequestBody CurrencyMaster currency) {
        return ResponseEntity.ok(ApiResponse.success("Currency Added.", masterService.addCurrency(currency)));
    }
    @PutMapping("/currencies/{id}")
    public ResponseEntity<ApiResponse<CurrencyMaster>> updateCurrency(@PathVariable String id, @RequestBody CurrencyMaster currency) {
        return ResponseEntity.ok(ApiResponse.success("Currency Updated.", masterService.updateCurrency(id, currency)));
    }
    @DeleteMapping("/currencies/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteCurrency(@PathVariable String id) {
        masterService.deleteCurrency(id);
        return ResponseEntity.ok(ApiResponse.successVoid("Currency Deleted."));
    }

    // ─── RECEIPT NAME ────────────────────────────────────────────────────────────
    @GetMapping("/receipt-names")
    public ResponseEntity<ApiResponse<List<ReceiptNameMaster>>> getAllReceiptNames() {
        return ResponseEntity.ok(ApiResponse.success(masterService.getAllReceiptNames()));
    }
    @PostMapping("/receipt-names")
    public ResponseEntity<ApiResponse<ReceiptNameMaster>> addReceiptName(@RequestBody ReceiptNameMaster rn) {
        return ResponseEntity.ok(ApiResponse.success("Receipt Name Added.", masterService.addReceiptName(rn)));
    }
    @PutMapping("/receipt-names/{id}")
    public ResponseEntity<ApiResponse<ReceiptNameMaster>> updateReceiptName(@PathVariable String id, @RequestBody ReceiptNameMaster rn) {
        return ResponseEntity.ok(ApiResponse.success("Receipt Name Updated.", masterService.updateReceiptName(id, rn)));
    }
    @DeleteMapping("/receipt-names/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteReceiptName(@PathVariable String id) {
        masterService.deleteReceiptName(id);
        return ResponseEntity.ok(ApiResponse.successVoid("Receipt Name Deleted."));
    }

    // ─── RECEIPT TYPE ────────────────────────────────────────────────────────────
    @GetMapping("/receipt-types")
    public ResponseEntity<ApiResponse<List<ReceiptTypeMaster>>> getAllReceiptTypes() {
        return ResponseEntity.ok(ApiResponse.success(masterService.getAllReceiptTypes()));
    }
    @GetMapping("/receipt-types/by-category/{catName}")
    public ResponseEntity<ApiResponse<List<ReceiptTypeMaster>>> getReceiptTypesByCat(@PathVariable String catName) {
        return ResponseEntity.ok(ApiResponse.success(masterService.getReceiptTypesByCat(catName)));
    }
    @PostMapping("/receipt-types")
    public ResponseEntity<ApiResponse<ReceiptTypeMaster>> addReceiptType(@RequestBody ReceiptTypeMaster rt) {
        return ResponseEntity.ok(ApiResponse.success("Receipt Type Added.", masterService.addReceiptType(rt)));
    }
    @PutMapping("/receipt-types/{id}")
    public ResponseEntity<ApiResponse<ReceiptTypeMaster>> updateReceiptType(@PathVariable String id, @RequestBody ReceiptTypeMaster rt) {
        return ResponseEntity.ok(ApiResponse.success("Receipt Type Updated.", masterService.updateReceiptType(id, rt)));
    }
    @DeleteMapping("/receipt-types/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteReceiptType(@PathVariable String id) {
        masterService.deleteReceiptType(id);
        return ResponseEntity.ok(ApiResponse.successVoid("Receipt Type Deleted."));
    }

    // ─── SUPPLIER ────────────────────────────────────────────────────────────────
    @GetMapping("/suppliers")
    public ResponseEntity<ApiResponse<List<SupplierMaster>>> getAllSuppliers() {
        return ResponseEntity.ok(ApiResponse.success(masterService.getAllSuppliers()));
    }
    @PostMapping("/suppliers")
    public ResponseEntity<ApiResponse<SupplierMaster>> addSupplier(@RequestBody SupplierMaster supplier) {
        return ResponseEntity.ok(ApiResponse.success("Supplier Added.", masterService.addSupplier(supplier)));
    }
    @PutMapping("/suppliers/{id}")
    public ResponseEntity<ApiResponse<SupplierMaster>> updateSupplier(@PathVariable String id, @RequestBody SupplierMaster supplier) {
        return ResponseEntity.ok(ApiResponse.success("Supplier Updated.", masterService.updateSupplier(id, supplier)));
    }
    @DeleteMapping("/suppliers/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteSupplier(@PathVariable String id) {
        masterService.deleteSupplier(id);
        return ResponseEntity.ok(ApiResponse.successVoid("Supplier Deleted."));
    }

    // ─── TRANSACTION ─────────────────────────────────────────────────────────────
    @GetMapping("/transactions")
    public ResponseEntity<ApiResponse<List<TransactionMaster>>> getAllTransactions() {
        return ResponseEntity.ok(ApiResponse.success(masterService.getAllTransactions()));
    }
    @GetMapping("/transactions/by-category/{catName}")
    public ResponseEntity<ApiResponse<List<TransactionMaster>>> getTransactionsByCat(@PathVariable String catName) {
        return ResponseEntity.ok(ApiResponse.success(masterService.getTransactionsByCat(catName)));
    }
    @PostMapping("/transactions")
    public ResponseEntity<ApiResponse<TransactionMaster>> addTransaction(@RequestBody TransactionMaster trans) {
        return ResponseEntity.ok(ApiResponse.success("Transaction Added.", masterService.addTransaction(trans)));
    }
    @PutMapping("/transactions/{id}")
    public ResponseEntity<ApiResponse<TransactionMaster>> updateTransaction(@PathVariable String id, @RequestBody TransactionMaster trans) {
        return ResponseEntity.ok(ApiResponse.success("Transaction Updated.", masterService.updateTransaction(id, trans)));
    }
    @DeleteMapping("/transactions/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteTransaction(@PathVariable String id) {
        masterService.deleteTransaction(id);
        return ResponseEntity.ok(ApiResponse.successVoid("Transaction Deleted."));
    }
}
