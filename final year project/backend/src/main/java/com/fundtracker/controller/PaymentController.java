package com.fundtracker.controller;

import com.fundtracker.dto.ApiResponse;
import com.fundtracker.model.Payment;
import com.fundtracker.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/payments")
public class PaymentController {

    @Autowired private PaymentService service;

    @GetMapping
    public ResponseEntity<ApiResponse<List<Payment>>> getAll() {
        return ResponseEntity.ok(ApiResponse.success(service.getAll()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Payment>> getById(@PathVariable String id) {
        return ResponseEntity.ok(ApiResponse.success(service.getById(id)));
    }

    @GetMapping("/doc-no")
    public ResponseEntity<ApiResponse<String>> generateDocNo() {
        return ResponseEntity.ok(ApiResponse.success(service.generateDocNo()));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Payment>> save(@RequestBody Payment payment) {
        return ResponseEntity.ok(ApiResponse.success("Credit Paid Successfully.", service.save(payment)));
    }
}
