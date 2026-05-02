package com.fundtracker.repository;

import com.fundtracker.model.SalexportIncentives;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface SalexportIncentivesRepository extends MongoRepository<SalexportIncentives, String> {
    Optional<SalexportIncentives> findByDocNo(String docNo);
    long count();
    List<SalexportIncentives> findByIncentivesType(String incentivesType);
    List<SalexportIncentives> findByEntryDateBetween(LocalDate from, LocalDate to);
    List<SalexportIncentives> findByEntryDateBetweenAndIncentivesType(LocalDate from, LocalDate to, String incentivesType);
}
