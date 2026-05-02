package com.fundtracker.controller;

import com.fundtracker.dto.ApiResponse;
import com.fundtracker.model.ExportInvoice;
import com.fundtracker.service.ExportInvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/export-invoices")
public class ExportInvoiceController {

    @Autowired private ExportInvoiceService service;

    @GetMapping
    public ResponseEntity<ApiResponse<List<ExportInvoice>>> getAll() {
        return ResponseEntity.ok(ApiResponse.success(service.getAll()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ExportInvoice>> getById(@PathVariable String id) {
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

    @GetMapping("/open-dbk-invoice-nos")
    public ResponseEntity<ApiResponse<List<String>>> getOpenDbkInvoiceNos() {
        return ResponseEntity.ok(ApiResponse.success(service.getOpenDbkInvoiceNos()));
    }

    @GetMapping("/open-meis-invoice-nos")
    public ResponseEntity<ApiResponse<List<String>>> getOpenMeisInvoiceNos() {
        return ResponseEntity.ok(ApiResponse.success(service.getOpenMeisInvoiceNos()));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<ExportInvoice>> save(@RequestBody ExportInvoice invoice) {
        return ResponseEntity.ok(ApiResponse.success("Export Invoice Added.", service.save(invoice)));
    }
}
