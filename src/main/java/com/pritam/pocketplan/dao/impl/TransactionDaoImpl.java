package com.pritam.pocketplan.dao.impl;

import com.pritam.pocketplan.dao.TransactionDao;
import com.pritam.pocketplan.models.TransactionEntry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TransactionDaoImpl implements TransactionDao {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public TransactionEntry addTransaction(TransactionEntry transactionEntry) {
        // Save the transaction to the MongoDB collection
        return mongoTemplate.save(transactionEntry);  // This saves the transaction and returns the saved object
    }

    @Override
    public List<TransactionEntry> findTransactionsByAccount(String account) {

        Query query = new Query(
                new Criteria().orOperator(
                        Criteria.where("account").is("EMI"),
                        Criteria.where("category").is("EMI")
                )
        );


        return mongoTemplate.find(query, TransactionEntry.class);

    }

    @Override
    public List<TransactionEntry> findTransactionsByCategory(String category) {
        return List.of();
    }

    @Override
    public List<TransactionEntry> addTransactions(List<TransactionEntry> transactionEntryList) {
        if (transactionEntryList == null || transactionEntryList.isEmpty()) {
            // Return an empty list if the input is null or empty
            System.out.println("No transactions to save.");
            return List.of();
        }

        try {
            // Insert all entries into the MongoDB collection
            List<TransactionEntry> savedEntries = (List<TransactionEntry>) mongoTemplate.insert(transactionEntryList, TransactionEntry.class);
            System.out.println("Transactions successfully saved: " + savedEntries.size());
            return savedEntries;
        } catch (Exception e) {
            // Handle exceptions during the save operation
            System.err.println("Error while saving transactions: " + e.getMessage());
            throw e; // Optionally rethrow for higher layers to handle
        }
    }

    @Override
    public void deleteAllData() {
        mongoTemplate.remove(new Query(), TransactionEntry.class);
    }

    @Override
    public List<TransactionEntry> getAllTransaction() {
        return mongoTemplate.findAll(TransactionEntry.class);

    }

}
