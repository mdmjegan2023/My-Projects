package com.fundtracker.controller;

import com.fundtracker.dto.ApiResponse;
import com.fundtracker.model.Receipt;
import com.fundtracker.service.ReceiptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/receipts")
public class ReceiptController {

    @Autowired private ReceiptService service;

    @GetMapping
    public ResponseEntity<ApiResponse<List<Receipt>>> getAll() {
        return ResponseEntity.ok(ApiResponse.success(service.getAll()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Receipt>> getById(@PathVariable String id) {
        return ResponseEntity.ok(ApiResponse.success(service.getById(id)));
    }

    @GetMapping("/doc-no")
    public ResponseEntity<ApiResponse<String>> generateDocNo() {
        return ResponseEntity.ok(ApiResponse.success(service.generateDocNo()));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Receipt>> save(@RequestBody Receipt receipt) {
        return ResponseEntity.ok(ApiResponse.success("Receipt Added.", service.save(receipt)));
    }
}
