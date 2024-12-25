package com.pritam.pocketplan.controller.impl;

import com.pritam.pocketplan.controller.AccountController;
import com.pritam.pocketplan.exceptions.AppException;
import com.pritam.pocketplan.models.Account;
import com.pritam.pocketplan.models.ApiReturn;
import com.pritam.pocketplan.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AccountControllerImpl implements AccountController {

    @Autowired
    private AccountService accountService;

    @GetMapping("/refreshaccounts")
    @Override
    public ResponseEntity<ApiReturn<List<Account>>> refreshAccounts() throws AppException {
        List<Account> UpdatedAccounts = accountService.refreshAccounts();
        ApiReturn<List<Account>> response = new ApiReturn<>(HttpStatus.OK.value(), "File processed and data saved successfully", UpdatedAccounts);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


}
