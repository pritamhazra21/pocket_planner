package com.pritam.pocketplan.service;

import com.pritam.pocketplan.dao.AccountDao;
import com.pritam.pocketplan.dao.CatagoryDao;
import com.pritam.pocketplan.dao.TransactionDao;
import com.pritam.pocketplan.exceptions.AppException;
import com.pritam.pocketplan.helper.Utility;
import com.pritam.pocketplan.models.Account;
import com.pritam.pocketplan.models.Catagory;
import com.pritam.pocketplan.models.TransactionEntry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class TransactionService {

    @Autowired
    private TransactionDao transactionDao;

    @Autowired
    private AccountDao accountDao;

    @Autowired
    private CatagoryDao catagoryDao;

    private final Utility utility = new Utility();

    public TransactionEntry saveTransaction(TransactionEntry transactionEntry) {

        transactionEntry.setTimestamp(System.currentTimeMillis());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); // You can modify the format as needed
        String humanReadableDate = sdf.format(new Date(transactionEntry.getTimestamp()));

        // Set the human-readable date
        transactionEntry.setHumanReadableDate(humanReadableDate);
        transactionEntry = transactionDao.addTransaction(transactionEntry);
        return transactionEntry;
    }


    public List<TransactionEntry> importExcelFile(MultipartFile file) throws IOException {

        if (file.isEmpty()) {
            throw new AppException("No file uploaded", HttpStatus.BAD_REQUEST.value());
        }


        transactionDao.deleteAllData();
        accountDao.deleteAllData();
        catagoryDao.deleteAllData();


        List<TransactionEntry> transactionEntryList = utility.processExcelFile(file);
        transactionEntryList = transactionDao.addTransactions(transactionEntryList);


        // import accounts
        List<Account> accountList = new ArrayList<>();
        Set<String> uniqueAccounts = new HashSet<>();

        List<Catagory> catagoryList = new ArrayList<>();
        Set<String> uniquecatagories = new HashSet<>();

        for (TransactionEntry transactionEntry : transactionEntryList) {
            String accountName = transactionEntry.getAccount();
            String catagory = transactionEntry.getCategory();// Assuming this method exists
            if (accountName != null && !accountName.isEmpty()) {
                uniqueAccounts.add(accountName); // Add to the set for uniqueness
            }
            if (catagory != null && !catagory.isEmpty()) {
                uniquecatagories.add(catagory);
            }
        }
        for (String accountName : uniqueAccounts) {
            Account account = new Account();
            account.setName(accountName);
            accountList.add(account);
        }


        accountDao.addAccounts(accountList);

        for (String catagoryname : uniquecatagories) {
            if (!uniqueAccounts.contains(catagoryname)) {
                Catagory catagory = new Catagory();
                catagory.setName(catagoryname);
                catagoryList.add(catagory);
            }

        }
        // import catagories

        catagoryDao.addCatagories(catagoryList);


        return transactionEntryList;


    }
}