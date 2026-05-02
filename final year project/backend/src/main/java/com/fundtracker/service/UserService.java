package com.fundtracker.service;

import com.fundtracker.exception.BusinessException;
import com.fundtracker.exception.DuplicateResourceException;
import com.fundtracker.exception.ResourceNotFoundException;
import com.fundtracker.model.User;
import com.fundtracker.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired private UserRepository userRepository;
    @Autowired private PasswordEncoder passwordEncoder;

    public List<User> getAllUsers() {
        List<User> users = userRepository.findAll();
        // Mask passwords before returning
        users.forEach(u -> u.setPassword("***"));
        return users;
    }

    public User getUserByUid(String uid) {
        User user = userRepository.findByUid(uid)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with UID: " + uid));
        user.setPassword("***");
        return user;
    }

    /** addUser form logic - check UID duplicate, then username duplicate */
    public User addUser(User user) {
        if (userRepository.existsByUid(user.getUid())) {
            throw new DuplicateResourceException("User ID Already Exist.");
        }
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new DuplicateResourceException("Username Already Exist.");
        }
        if (user.getPassword() == null || user.getPassword().isBlank()) {
            throw new BusinessException("Password is required.");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    /** updateUser form logic */
    public User updateUser(String uid, User updated) {
        User existing = userRepository.findByUid(uid)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with UID: " + uid));
        existing.setUsername(updated.getUsername());
        existing.setUserType(updated.getUserType());
        existing.setRemarks(updated.getRemarks());
        if (updated.getPassword() != null && !updated.getPassword().isBlank()
                && !updated.getPassword().equals("***")) {
            existing.setPassword(passwordEncoder.encode(updated.getPassword()));
        }
        return userRepository.save(existing);
    }

    /** deleteUser form logic */
    public void deleteUser(String uid) {
        User user = userRepository.findByUid(uid)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with UID: " + uid));
        userRepository.delete(user);
    }
}
