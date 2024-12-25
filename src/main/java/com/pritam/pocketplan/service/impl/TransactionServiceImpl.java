package com.pritam.pocketplan.service.impl;

import com.pritam.pocketplan.dao.TransactionDao;
import com.pritam.pocketplan.helper.Utility;
import com.pritam.pocketplan.models.TransactionEntry;
import com.pritam.pocketplan.models.request.TransactionRequest;
import com.pritam.pocketplan.service.TransactionService;
import com.pritam.pocketplan.values.TransactionType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private TransactionDao transactionDao;

    private Utility utility = new Utility();

    @Override
    public TransactionEntry saveTransaction(TransactionEntry transactionEntry) {

        transactionEntry.setTimestamp(System.currentTimeMillis());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); // You can modify the format as needed
        String humanReadableDate = sdf.format(new Date(transactionEntry.getTimestamp()));

        // Set the human-readable date
        transactionEntry.setHumanReadableDate(humanReadableDate);
        transactionEntry = transactionDao.addTransaction(transactionEntry);
        return transactionEntry;
    }

    @Override
    public List<TransactionEntry> getTransaction(TransactionRequest transactionRequest) {


        List<TransactionEntry> transactionEntryList = transactionDao.getTransaction(transactionRequest);



        return transactionEntryList;
    }

    @Override
    public List<Map<String, Object>> getDayWiseSummary(Long startTime, Long endTime) {

        TransactionRequest transactionRequest = new TransactionRequest();
        transactionRequest.setStartTime(startTime);
        transactionRequest.setEndTime(endTime);

        List<TransactionEntry> transactions = transactionDao.getTransaction(transactionRequest);

        // Map to group transactions by day
        Map<Long, Map<String, Double>> dayWiseSummary = new TreeMap<>();

        // Iterate through all transactions
        for (TransactionEntry transaction : transactions) {
            long dayStartTimestamp = utility.getStartOfDayTimestamp(transaction.getTimestamp());

            // Initialize map for each day if not present
            dayWiseSummary.putIfAbsent(dayStartTimestamp, new HashMap<>());
            Map<String, Double> dailySummary = dayWiseSummary.get(dayStartTimestamp);

            // Calculate income and expense
            if (transaction.getTransactionType() == TransactionType.INCOME) {
                dailySummary.put("income", dailySummary.getOrDefault("income", 0.0) + transaction.getAmount());
            } else if (transaction.getTransactionType() == TransactionType.EXPENSE) {
                dailySummary.put("expense", dailySummary.getOrDefault("expense", 0.0) + transaction.getAmount());
            }
        }

        // Convert map to list for response
        List<Map<String, Object>> result = new ArrayList<>();
        for (Map.Entry<Long, Map<String, Double>> entry : dayWiseSummary.entrySet()) {
            Map<String, Object> daySummary = new HashMap<>();
            daySummary.put("date", entry.getKey());
            daySummary.put("humanReadableDate",
                    new SimpleDateFormat("dd/MM/yyyy").format(new Date(entry.getKey())));
            daySummary.put("income", entry.getValue().getOrDefault("income", 0.0));
            daySummary.put("expense", entry.getValue().getOrDefault("expense", 0.0));
            result.add(daySummary);
        }

        return result;
    }


}