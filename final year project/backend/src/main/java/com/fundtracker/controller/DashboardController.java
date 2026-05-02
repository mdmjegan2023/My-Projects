package com.fundtracker.controller;

import com.fundtracker.dto.ApiResponse;
import com.fundtracker.dto.DashboardSummary;
import com.fundtracker.model.BankBalance;
import com.fundtracker.model.PurchaseInvoice;
import com.fundtracker.model.SupplierCreditOpenClose;
import com.fundtracker.service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/dashboard")
public class DashboardController {

    @Autowired private DashboardService service;

    @GetMapping("/summary")
    public ResponseEntity<ApiResponse<DashboardSummary>> getSummary() {
        return ResponseEntity.ok(ApiResponse.success(service.getSummary()));
    }

    @GetMapping("/payment-alerts")
    public ResponseEntity<ApiResponse<List<PurchaseInvoice>>> getPaymentAlerts() {
        return ResponseEntity.ok(ApiResponse.success(service.getPaymentAlerts()));
    }

    @GetMapping("/bank-balances")
    public ResponseEntity<ApiResponse<List<BankBalance>>> getAllBankBalances() {
        return ResponseEntity.ok(ApiResponse.success(service.getAllBankBalances()));
    }

    @GetMapping("/bank-balance/{accNo}")
    public ResponseEntity<ApiResponse<BankBalance>> getBankBalance(@PathVariable String accNo) {
        return ResponseEntity.ok(ApiResponse.success(
            service.getBankBalance(accNo).orElse(null)));
    }

    @GetMapping("/supplier-credits")
    public ResponseEntity<ApiResponse<List<SupplierCreditOpenClose>>> getAllSupplierCredits() {
        return ResponseEntity.ok(ApiResponse.success(service.getAllSupplierCredits()));
    }

    @GetMapping("/supplier-credit/{supName}")
    public ResponseEntity<ApiResponse<SupplierCreditOpenClose>> getSupplierCredit(@PathVariable String supName) {
        return ResponseEntity.ok(ApiResponse.success(
            service.getSupplierCredit(supName).orElse(null)));
    }
}
