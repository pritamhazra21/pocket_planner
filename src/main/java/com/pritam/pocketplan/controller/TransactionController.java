package com.pritam.pocketplan.controller;

import com.pritam.pocketplan.exceptions.AppException;
import com.pritam.pocketplan.helper.Utility;
import com.pritam.pocketplan.models.TransactionEntry;
import com.pritam.pocketplan.models.apiReturn;
import com.pritam.pocketplan.service.TransactionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
public class TransactionController {
    private static final Logger logger = LoggerFactory.getLogger(TransactionController.class);

    @Autowired
    private TransactionService transactionService;

    @PostMapping("/transaction")
    public ResponseEntity<apiReturn<TransactionEntry>> addTransaction(@RequestBody TransactionEntry transactionEntry) throws AppException {
        TransactionEntry savedTransaction = transactionService.saveTransaction(transactionEntry);
        logger.info("Transaction added successfully: {}", savedTransaction);
        apiReturn<TransactionEntry> response = new apiReturn<>(HttpStatus.OK.value(), "Transaction added successfully", savedTransaction);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/importexcel")
    public ResponseEntity<apiReturn<List<TransactionEntry>>> importExcelFile(@RequestParam("file") MultipartFile file) throws IOException {
        List<TransactionEntry> transactionEntryList = transactionService.importExcelFile(file);

        apiReturn<List<TransactionEntry>> response = new apiReturn<>(HttpStatus.OK.value(), "File processed and data saved successfully", transactionEntryList);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


}
