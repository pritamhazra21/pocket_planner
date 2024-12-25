package com.pritam.pocketplan.controller;

import com.pritam.pocketplan.exceptions.AppException;
import com.pritam.pocketplan.models.Account;
import com.pritam.pocketplan.models.ApiReturn;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

public interface AccountController {
    ResponseEntity<ApiReturn<List<Account>>> refreshAccounts() throws AppException;
}
