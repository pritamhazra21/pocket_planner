package com.pritam.pocketplan.service;

import com.pritam.pocketplan.models.TransactionEntry;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ImportAndExportService {
    List<TransactionEntry> importExcelFile(MultipartFile file) throws IOException;
}
