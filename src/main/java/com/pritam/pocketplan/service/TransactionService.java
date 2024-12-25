package com.pritam.pocketplan.service;

import com.pritam.pocketplan.models.TransactionEntry;
import com.pritam.pocketplan.models.request.TransactionRequest;

import java.util.List;
import java.util.Map;

public interface TransactionService {
    TransactionEntry saveTransaction(TransactionEntry transactionEntry);

    List<TransactionEntry> getTransaction(TransactionRequest transactionRequest);

    List<Map<String, Object>> getDayWiseSummary(Long startTime, Long endTime);
}
