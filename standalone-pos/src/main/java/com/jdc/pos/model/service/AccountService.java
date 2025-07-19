package com.jdc.pos.model.service;

import com.jdc.pos.model.entity.Account;
import com.jdc.pos.model.repo.AccountRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

    @Autowired
    private AccountRepo repo;

    public void save(Account acc) {
        repo.save(acc);
    }
}
