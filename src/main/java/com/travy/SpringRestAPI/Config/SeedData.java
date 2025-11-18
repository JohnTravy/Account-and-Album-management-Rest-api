package com.travy.SpringRestAPI.Config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.travy.SpringRestAPI.Model.Account;
import com.travy.SpringRestAPI.Service.AccountService;
import com.travy.SpringRestAPI.Util.Constraints.Authority;

@Component
public class SeedData implements CommandLineRunner {


    @Autowired
    private AccountService accountService;

    @Override
    public void run(String... args) throws Exception {
      
        Account account01 = new Account();
        Account account02 = new Account();

        account01.setEmail("user@user.com");
        account01.setPassword("pass987");
        account01.setAuthorities(Authority.USER.toString());
        accountService.Save(account01);

        account02.setEmail("admin@admin.com");
        account02.setPassword("pass987");
        account02.setAuthorities(Authority.ADMIN.toString() + " " + Authority.USER.toString());
        accountService.Save(account02);

    }
    
    
}
