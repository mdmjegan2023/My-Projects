package com.fundtracker.repository;

import com.fundtracker.model.BankMaster;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.Optional;
import java.util.List;

public interface BankMasterRepository extends MongoRepository<BankMaster, String> {
    Optional<BankMaster> findByBankCode(String bankCode);
    Optional<BankMaster> findByAccNo(String accNo);
    boolean existsByBankCode(String bankCode);
    boolean existsByAccNo(String accNo);
    boolean existsByBankNameAndAccType(String bankName, String accType);
    List<BankMaster> findAllByOrderByBankNameAsc();
}
