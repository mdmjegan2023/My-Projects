package com.fundtracker.security;

import com.fundtracker.model.User;
import com.fundtracker.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String uid) throws UsernameNotFoundException {
        User user = userRepository.findByUid(uid)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with UID: " + uid));
        return UserDetailsImpl.build(user);
    }
}
