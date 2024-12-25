package com.pritam.pocketplan.controller;

import com.pritam.pocketplan.exceptions.AppException;
import com.pritam.pocketplan.models.ApiReturn;
import com.pritam.pocketplan.models.TransactionEntry;
import com.pritam.pocketplan.models.request.TransactionRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;

public interface TransactionController {
    ResponseEntity<ApiReturn<TransactionEntry>> addTransaction(@RequestBody TransactionEntry transactionEntry) throws AppException;
    ResponseEntity<ApiReturn<List<TransactionEntry> >> getTransaction(@RequestBody TransactionRequest transactionRequest) throws AppException;

    @GetMapping("/day-wise-summary/{startTime}/{endTime}")
    ResponseEntity<List<Map<String, Object>>> getDayWiseSummary(
            @PathVariable Long startTime,
            @PathVariable Long endTime);
}
