package com.fundtracker.repository;

import com.fundtracker.model.SupplierCreditOpenClose;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;
import java.util.Optional;

public interface SupplierCreditOpenCloseRepository extends MongoRepository<SupplierCreditOpenClose, String> {
    Optional<SupplierCreditOpenClose> findBySupName(String supName);
    Optional<SupplierCreditOpenClose> findBySupNameAndSupAddressAndSupPhoneAndSupEmail(
        String supName, String supAddress, String supPhone, String supEmail);
    boolean existsBySupNameAndSupAddressAndSupPhoneAndSupEmail(
        String supName, String supAddress, String supPhone, String supEmail);
    List<SupplierCreditOpenClose> findAllByOrderBySupNameAsc();
}
