package com.fundtracker.repository;

import com.fundtracker.model.SupplierAdvanceOpenClose;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.Optional;

public interface SupplierAdvanceOpenCloseRepository extends MongoRepository<SupplierAdvanceOpenClose, String> {
    Optional<SupplierAdvanceOpenClose> findBySupNameAndSupAddressAndSupPhoneAndSupEmail(
        String supName, String supAddress, String supPhone, String supEmail);
    boolean existsBySupNameAndSupAddressAndSupPhoneAndSupEmail(
        String supName, String supAddress, String supPhone, String supEmail);
}
