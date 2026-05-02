package com.fundtracker.controller;

import com.fundtracker.dto.ApiResponse;
import com.fundtracker.model.ExportReceipt;
import com.fundtracker.service.ExportReceiptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/export-receipts")
public class ExportReceiptController {

    @Autowired private ExportReceiptService service;

    @GetMapping
    public ResponseEntity<ApiResponse<List<ExportReceipt>>> getAll() {
        return ResponseEntity.ok(ApiResponse.success(service.getAll()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ExportReceipt>> getById(@PathVariable String id) {
        return ResponseEntity.ok(ApiResponse.success(service.getById(id)));
    }

    @GetMapping("/doc-no")
    public ResponseEntity<ApiResponse<String>> generateDocNo() {
        return ResponseEntity.ok(ApiResponse.success(service.generateDocNo()));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<ExportReceipt>> save(@RequestBody ExportReceipt receipt) {
        return ResponseEntity.ok(ApiResponse.success("Export Receipt Added.", service.save(receipt)));
    }
}
