package com.pritam.pocketplan.controller.impl;

import com.pritam.pocketplan.controller.ImportAndExportController;
import com.pritam.pocketplan.models.ApiReturn;
import com.pritam.pocketplan.models.TransactionEntry;
import com.pritam.pocketplan.service.ImportAndExportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
public class ImportAndExportControllerImpl implements ImportAndExportController {

    @Autowired
    private ImportAndExportService importAndExportService;

    @PostMapping("/importexcel")
    @Override
    public ResponseEntity<ApiReturn<List<TransactionEntry>>> importExcelFile(@RequestParam("file") MultipartFile file) throws IOException {
        List<TransactionEntry> transactionEntryList = importAndExportService.importExcelFile(file);

        ApiReturn<List<TransactionEntry>> response = new ApiReturn<>(HttpStatus.OK.value(), "File processed and data saved successfully", transactionEntryList);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
