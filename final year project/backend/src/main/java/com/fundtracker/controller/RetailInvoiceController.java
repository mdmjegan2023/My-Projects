package com.fundtracker.controller;

import com.fundtracker.dto.ApiResponse;
import com.fundtracker.model.RetailInvoice;
import com.fundtracker.service.RetailInvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/retail-invoices")
public class RetailInvoiceController {

    @Autowired private RetailInvoiceService service;

    @GetMapping
    public ResponseEntity<ApiResponse<List<RetailInvoice>>> getAll() {
        return ResponseEntity.ok(ApiResponse.success(service.getAll()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<RetailInvoice>> getById(@PathVariable String id) {
        return ResponseEntity.ok(ApiResponse.success(service.getById(id)));
    }

    @GetMapping("/doc-no")
    public ResponseEntity<ApiResponse<String>> generateDocNo() {
        return ResponseEntity.ok(ApiResponse.success(service.generateDocNo()));
    }

    @GetMapping("/open-invoice-nos")
    public ResponseEntity<ApiResponse<List<String>>> getOpenInvoiceNos() {
        return ResponseEntity.ok(ApiResponse.success(service.getOpenInvoiceNos()));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<RetailInvoice>> save(@RequestBody RetailInvoice invoice) {
        return ResponseEntity.ok(ApiResponse.success("Retail Invoice Added.", service.save(invoice)));
    }
}
