package com.pritam.pocketplan.controller;

import com.pritam.pocketplan.exceptions.AppException;
import com.pritam.pocketplan.models.Account;
import com.pritam.pocketplan.models.TransactionEntry;
import com.pritam.pocketplan.models.apiReturn;
import com.pritam.pocketplan.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AccountController {

    @Autowired
    private AccountService accountService;

        @GetMapping("/refreshaccounts")
    public ResponseEntity<apiReturn<List<Account>>> refreshAccounts() throws AppException {
        List<Account> UpdatedAccounts = accountService.refreshAccounts();
            apiReturn<List<Account>> response = new apiReturn<>(HttpStatus.OK.value(), "File processed and data saved successfully", UpdatedAccounts);
            return new ResponseEntity<>(response, HttpStatus.OK);
    }


}
