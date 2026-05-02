package com.fundtracker.controller;

import com.fundtracker.dto.ApiResponse;
import com.fundtracker.model.SalexportIncentives;
import com.fundtracker.service.IncentivesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/incentives")
public class IncentivesController {

    @Autowired private IncentivesService service;

    @GetMapping
    public ResponseEntity<ApiResponse<List<SalexportIncentives>>> getAll() {
        return ResponseEntity.ok(ApiResponse.success(service.getAll()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<SalexportIncentives>> getById(@PathVariable String id) {
        return ResponseEntity.ok(ApiResponse.success(service.getById(id)));
    }

    @GetMapping("/doc-no")
    public ResponseEntity<ApiResponse<String>> generateDocNo() {
        return ResponseEntity.ok(ApiResponse.success(service.generateDocNo()));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<SalexportIncentives>> save(@RequestBody SalexportIncentives incentive) {
        return ResponseEntity.ok(ApiResponse.success("Incentive Added.", service.save(incentive)));
    }
}
