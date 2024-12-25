package com.pritam.pocketplan.dao;

import com.pritam.pocketplan.models.Account;

import java.util.List;

public interface AccountDao {

    Account addAccount(Account account);
    List<Account> addAccounts(List<Account> accountList);

    void deleteAllData();

    List<Account> getAllAccount();

    Account updateAccount(Account account);
}
