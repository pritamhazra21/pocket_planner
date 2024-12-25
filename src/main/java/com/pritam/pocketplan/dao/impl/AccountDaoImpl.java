package com.pritam.pocketplan.dao.impl;

import com.mongodb.client.result.UpdateResult;
import com.pritam.pocketplan.dao.AccountDao;
import com.pritam.pocketplan.models.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AccountDaoImpl implements AccountDao {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public Account addAccount(Account account) {

        return mongoTemplate.save(account);
    }

    @Override
    public List<Account> addAccounts(List<Account> accountList) {
        return (List<Account>) mongoTemplate.insert(accountList, Account.class);
    }

    @Override
    public void deleteAllData() {
        mongoTemplate.remove(new Query(), Account.class);
    }

    @Override
    public List<Account> getAllAccount() {
        return mongoTemplate.findAll(Account.class);
    }
    @Override
    public Account updateAccount(Account account) {
        try {
            // Check if the account exists
            Query query = new Query(Criteria.where("name").is(account.getName())); // Assuming the account is identified by 'name'

            // Create the update object with the new values for the account
            Update update = new Update();
            update.set("amount", account.getAmount());  // Assuming we're updating the balance (you can update other fields here)

            // Perform the update operation
            UpdateResult result = mongoTemplate.updateFirst(query, update, Account.class);

            // Check if the update was successful
            if (result.getModifiedCount() > 0) {
                return account;  // Return the updated account
            } else {
                return null;  // Return null if the account wasn't found or no changes were made
            }
        } catch (Exception e) {
            return null;  // Return null if an error occurs
        }
    }

}
