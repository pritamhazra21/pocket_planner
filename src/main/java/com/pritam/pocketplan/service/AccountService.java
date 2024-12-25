package com.pritam.pocketplan.service;

import com.pritam.pocketplan.models.Account;

import java.util.List;

public interface AccountService {
    List<Account> refreshAccounts();
}
