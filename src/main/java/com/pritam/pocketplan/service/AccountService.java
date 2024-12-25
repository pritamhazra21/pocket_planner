package com.pritam.pocketplan.service;

import com.pritam.pocketplan.dao.AccountDao;
import com.pritam.pocketplan.dao.TransactionDao;
import com.pritam.pocketplan.models.Account;
import com.pritam.pocketplan.models.TransactionEntry;
import com.pritam.pocketplan.values.TransactionType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AccountService {

    @Autowired
    private AccountDao accountDao;

    @Autowired
    private TransactionDao transactionDao;


    public List<Account> refreshAccounts() {

        List<TransactionEntry> transactionEntryList = transactionDao.getAllTransaction();

        List<Account> accountList = accountDao.getAllAccount();

        Map<String, Double> accountBalanceMap = new HashMap<>();

        for (Account account : accountList) {
            accountBalanceMap.put(account.getName(), 0.0);  // Assuming Account has getName() and balance properties
        }

        for (TransactionEntry transactionEntry : transactionEntryList) {
            if (transactionEntry.getTransactionType().equals(TransactionType.INCOME)) {
                accountBalanceMap.put(transactionEntry.getAccount(), accountBalanceMap.getOrDefault(transactionEntry.getAccount(), 0.0) + transactionEntry.getAmount());
            } else if (transactionEntry.getTransactionType().equals(TransactionType.EXPENSE)) {
                accountBalanceMap.put(transactionEntry.getAccount(), accountBalanceMap.getOrDefault(transactionEntry.getAccount(), 0.0) - transactionEntry.getAmount());

            } else if (transactionEntry.getTransactionType().equals(TransactionType.TRANSFER_OUT)) {
                accountBalanceMap.put(transactionEntry.getAccount(), accountBalanceMap.getOrDefault(transactionEntry.getAccount(), 0.0) - transactionEntry.getAmount());
                accountBalanceMap.put(transactionEntry.getCategory(), accountBalanceMap.getOrDefault(transactionEntry.getCategory(), 0.0) + transactionEntry.getAmount());

            }
        }

        // Now update the account balances in the database
        for (Account account : accountList) {
            Double balance = accountBalanceMap.get(account.getName());

            // If balance is null or NaN, you can set a default value
            if (balance == null || balance.isNaN()) {
                balance = 0.0; // Set to 0 if balance is null or NaN
            }

            // Convert balance to BigDecimal for precise rounding
            BigDecimal roundedBalance = new BigDecimal(balance).setScale(2, RoundingMode.HALF_UP);
            account.setAmount(roundedBalance.doubleValue());
            // Assuming Account has a setBalance() method

            // Save updated account to the database
            accountDao.updateAccount(account);  // Assuming there's a method to update account
        }


        return accountList;
    }
}
