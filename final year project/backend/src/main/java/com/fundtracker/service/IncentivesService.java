package com.fundtracker.service;

import com.fundtracker.exception.ResourceNotFoundException;
import com.fundtracker.model.*;
import com.fundtracker.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class IncentivesService {

    @Autowired private SalexportIncentivesRepository incentivesRepo;
    @Autowired private DbkIncentivesOpenCloseRepository dbkRepo;
    @Autowired private MeisIncentivesOpenCloseRepository meisRepo;
    @Autowired private ReceiptService receiptService;

    public String generateDocNo() {
        long count = incentivesRepo.count() + 1;
        return "INC" + String.format("%05d", count);
    }

    public List<SalexportIncentives> getAll() { return incentivesRepo.findAll(); }

    public SalexportIncentives getById(String id) {
        return incentivesRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Incentive not found."));
    }

    /**
     * Full INSERT logic from salexportIncentives.vb:
     * 1. Insert salexport_incentives
     * 2. If DBK: update dbk_incentives_openclose -> Closed
     * 3. If MEIS: update meis_incentives_openclose -> Closed
     * 4. Upsert bank_balance (add amount)
     */
    @Transactional
    public SalexportIncentives save(SalexportIncentives incentive) {
        incentive.setEntryDate(LocalDate.now());
        if (incentive.getDocNo() == null || incentive.getDocNo().isBlank()) {
            incentive.setDocNo(generateDocNo());
        }
        SalexportIncentives saved = incentivesRepo.save(incentive);

        // Update openclose status
        if ("DBK".equals(incentive.getIncentivesType())) {
            dbkRepo.findByInvoiceNo(incentive.getInvoiceNo()).ifPresent(oc -> {
                oc.setDocNo(incentive.getDocNo());
                oc.setDbk(incentive.getIncentivesType());
                oc.setStatus("Closed");
                dbkRepo.save(oc);
            });
        } else if ("MEIS".equals(incentive.getIncentivesType())) {
            meisRepo.findByInvoiceNo(incentive.getInvoiceNo()).ifPresent(oc -> {
                oc.setDocNo(incentive.getDocNo());
                oc.setMeis(incentive.getIncentivesType());
                oc.setStatus("Closed");
                meisRepo.save(oc);
            });
        }

        // Upsert bank_balance
        receiptService.upsertBankBalance(incentive.getBankName(), incentive.getAccNo(),
                incentive.getAccType(), incentive.getBankBranch(),
                incentive.getIfscCode(), incentive.getSwiftCode(),
                incentive.getAmount(), true);

        return saved;
    }
}
