package com.pritam.pocketplan.dao;

import com.pritam.pocketplan.models.TransactionEntry;

import java.util.List;

public interface TransactionDao {
    TransactionEntry addTransaction(TransactionEntry transactionEntry);

    List<TransactionEntry> findTransactionsByAccount(String account);

    List<TransactionEntry> findTransactionsByCategory(String category);

    List<TransactionEntry> addTransactions(List<TransactionEntry> transactionEntryList);

    void deleteAllData();

    List<TransactionEntry> getAllTransaction();
}
