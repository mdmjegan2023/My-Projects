package com.fundtracker.controller;

import com.fundtracker.dto.ApiResponse;
import com.fundtracker.model.DebitNote;
import com.fundtracker.service.DebitNoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/debit-notes")
public class DebitNoteController {

    @Autowired private DebitNoteService service;

    @GetMapping
    public ResponseEntity<ApiResponse<List<DebitNote>>> getAll() {
        return ResponseEntity.ok(ApiResponse.success(service.getAll()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<DebitNote>> getById(@PathVariable String id) {
        return ResponseEntity.ok(ApiResponse.success(service.getById(id)));
    }

    @GetMapping("/doc-no")
    public ResponseEntity<ApiResponse<String>> generateDocNo() {
        return ResponseEntity.ok(ApiResponse.success(service.generateDocNo()));
    }

    @GetMapping("/invoice-nos-by-supplier/{supName}")
    public ResponseEntity<ApiResponse<List<String>>> getInvoiceNos(@PathVariable String supName) {
        return ResponseEntity.ok(ApiResponse.success(service.getInvoiceNosBySupplier(supName)));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<DebitNote>> save(@RequestBody DebitNote debitNote) {
        return ResponseEntity.ok(ApiResponse.success("Debit Note Added.", service.save(debitNote)));
    }
}
