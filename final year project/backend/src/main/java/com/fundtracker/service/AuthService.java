package com.fundtracker.service;

import com.fundtracker.dto.JwtResponse;
import com.fundtracker.dto.LoginRequest;
import com.fundtracker.exception.BusinessException;
import com.fundtracker.model.User;
import com.fundtracker.repository.UserRepository;
import com.fundtracker.security.JwtUtils;
import com.fundtracker.security.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired private AuthenticationManager authenticationManager;
    @Autowired private UserRepository userRepository;
    @Autowired private PasswordEncoder passwordEncoder;
    @Autowired private JwtUtils jwtUtils;

    /**
     * Authenticate user by UID + password (matches original VB login logic).
     * Supports both legacy plain-text passwords and new BCrypt-hashed passwords.
     */
    public JwtResponse login(LoginRequest request) {
        // First try BCrypt authentication via Spring Security
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUid(), request.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtUtils.generateJwtToken(authentication);
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            return new JwtResponse(jwt, userDetails.getUid(), userDetails.getUsername(), userDetails.getUserType());
        } catch (BadCredentialsException e) {
            // Fallback: check plain-text password for migrated legacy accounts
            User user = userRepository.findByUid(request.getUid())
                    .orElseThrow(() -> new BusinessException("Invalid User ID or Password."));
            if (!user.getPassword().equals(request.getPassword())) {
                throw new BusinessException("Invalid User ID or Password.");
            }
            // Migrate to BCrypt on successful plain-text login
            user.setPassword(passwordEncoder.encode(request.getPassword()));
            userRepository.save(user);
            // Re-authenticate with new BCrypt password
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUid(), request.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtUtils.generateJwtToken(authentication);
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            return new JwtResponse(jwt, userDetails.getUid(), userDetails.getUsername(), userDetails.getUserType());
        }
    }
}
