package com.fundtracker.controller;

import com.fundtracker.dto.ApiResponse;
import com.fundtracker.model.PurchaseInvoice;
import com.fundtracker.service.PurchaseInvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/purchase-invoices")
public class PurchaseInvoiceController {

    @Autowired private PurchaseInvoiceService service;

    @GetMapping
    public ResponseEntity<ApiResponse<List<PurchaseInvoice>>> getAll() {
        return ResponseEntity.ok(ApiResponse.success(service.getAll()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<PurchaseInvoice>> getById(@PathVariable String id) {
        return ResponseEntity.ok(ApiResponse.success(service.getById(id)));
    }

    @GetMapping("/doc-no")
    public ResponseEntity<ApiResponse<String>> generateDocNo() {
        return ResponseEntity.ok(ApiResponse.success(service.generateDocNo()));
    }

    @GetMapping("/payment-alerts")
    public ResponseEntity<ApiResponse<List<PurchaseInvoice>>> getPaymentAlerts() {
        return ResponseEntity.ok(ApiResponse.success(service.getPaymentAlerts()));
    }

    @GetMapping("/overdue")
    public ResponseEntity<ApiResponse<List<PurchaseInvoice>>> getOverdue() {
        return ResponseEntity.ok(ApiResponse.success(service.getOverdueBills()));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<PurchaseInvoice>> save(@RequestBody PurchaseInvoice invoice) {
        return ResponseEntity.ok(ApiResponse.success("Booking Added.", service.save(invoice)));
    }
}
