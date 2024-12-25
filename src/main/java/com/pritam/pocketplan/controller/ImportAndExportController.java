package com.pritam.pocketplan.controller;

import com.pritam.pocketplan.models.ApiReturn;
import com.pritam.pocketplan.models.TransactionEntry;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ImportAndExportController {
    ResponseEntity<ApiReturn<List<TransactionEntry>>> importExcelFile(@RequestParam("file") MultipartFile file) throws IOException;
}
