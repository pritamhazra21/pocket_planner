package com.pritam.pocketplan.controller.impl;

import com.pritam.pocketplan.controller.TransactionController;
import com.pritam.pocketplan.exceptions.AppException;
import com.pritam.pocketplan.models.ApiReturn;
import com.pritam.pocketplan.models.TransactionEntry;
import com.pritam.pocketplan.models.request.TransactionRequest;
import com.pritam.pocketplan.service.TransactionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class TransactionControllerImpl implements TransactionController {
    private static final Logger logger = LoggerFactory.getLogger(TransactionControllerImpl.class);

    @Autowired
    private TransactionService transactionService;

    @PostMapping("/transaction")
    @Override
    public ResponseEntity<ApiReturn<TransactionEntry>> addTransaction(@RequestBody TransactionEntry transactionEntry) throws AppException {
        TransactionEntry savedTransaction = transactionService.saveTransaction(transactionEntry);
        logger.info("Transaction added successfully: {}", savedTransaction);
        ApiReturn<TransactionEntry> response = new ApiReturn<>(HttpStatus.OK.value(), "Transaction added successfully", savedTransaction);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/gettransaction")
    @Override
    public ResponseEntity<ApiReturn<List<TransactionEntry>>> getTransaction(TransactionRequest transactionRequest) throws AppException {

        List<TransactionEntry> transactionEntryList = transactionService.getTransaction(transactionRequest);

        ApiReturn<List<TransactionEntry>> response = new ApiReturn<>(HttpStatus.OK.value(), "Transaction added successfully", transactionEntryList);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/day-wise-summary/{startTime}/{endTime}")
    @Override
    public ResponseEntity<List<Map<String, Object>>> getDayWiseSummary(
            @PathVariable Long startTime,
            @PathVariable Long endTime) {
        List<Map<String, Object>> summary = transactionService.getDayWiseSummary(startTime, endTime);
        return ResponseEntity.ok(summary);
    }


}
