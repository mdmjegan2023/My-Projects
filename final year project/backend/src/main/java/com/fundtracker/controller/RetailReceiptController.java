package com.fundtracker.controller;

import com.fundtracker.dto.ApiResponse;
import com.fundtracker.model.RetailReceipt;
import com.fundtracker.service.RetailReceiptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/retail-receipts")
public class RetailReceiptController {

    @Autowired private RetailReceiptService service;

    @GetMapping
    public ResponseEntity<ApiResponse<List<RetailReceipt>>> getAll() {
        return ResponseEntity.ok(ApiResponse.success(service.getAll()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<RetailReceipt>> getById(@PathVariable String id) {
        return ResponseEntity.ok(ApiResponse.success(service.getById(id)));
    }

    @GetMapping("/doc-no")
    public ResponseEntity<ApiResponse<String>> generateDocNo() {
        return ResponseEntity.ok(ApiResponse.success(service.generateDocNo()));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<RetailReceipt>> save(@RequestBody RetailReceipt receipt) {
        return ResponseEntity.ok(ApiResponse.success("Retail Receipt Added.", service.save(receipt)));
    }
}
