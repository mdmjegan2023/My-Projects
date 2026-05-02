package com.fundtracker.repository;

import com.fundtracker.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {
    Optional<User> findByUid(String uid);
    boolean existsByUid(String uid);
    boolean existsByUsername(String username);
}
