package com.tokenvalidator.app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import com.tokenvalidator.app.domain.user.user;

public interface UserRepository extends JpaRepository<user, String>{

    UserDetails findByEmail(String email);
    
}
