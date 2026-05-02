package com.fundtracker.repository;

import com.fundtracker.model.BankBalance;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.Optional;

public interface BankBalanceRepository extends MongoRepository<BankBalance, String> {
    Optional<BankBalance> findByBankNameAndAccNoAndAccType(String bankName, String accNo, String accType);
    boolean existsByBankNameAndAccNoAndAccType(String bankName, String accNo, String accType);
}
