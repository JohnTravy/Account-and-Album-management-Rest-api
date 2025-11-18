package com.travy.SpringRestAPI.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.travy.SpringRestAPI.Model.Account;


public interface AccountRepository extends JpaRepository<Account, Long> {

    Optional<Account> findByEmail(String email);

    
    
}
