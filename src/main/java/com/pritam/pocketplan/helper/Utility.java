package com.pritam.pocketplan.helper;

import com.pritam.pocketplan.models.TransactionEntry;
import com.pritam.pocketplan.values.TransactionType;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class Utility {

    // Method to process the Excel file
    public List<TransactionEntry> processExcelFile(MultipartFile file) throws IOException {
        // Create a workbook from the uploaded file
        Workbook workbook = new XSSFWorkbook(file.getInputStream());
        Sheet sheet = workbook.getSheetAt(0); // Assuming data is in the first sheet

        // Iterate over rows
        Iterator<Row> rowIterator = sheet.iterator();
        rowIterator.next(); // Skip header row

        List<TransactionEntry> transactionEntryList = new ArrayList<>();

        while (rowIterator.hasNext()) {
            TransactionEntry transactionEntry = new TransactionEntry();
            Row row = rowIterator.next();

            // Handle date in the first cell (Column 0)
            handleDateCell(row.getCell(0), transactionEntry);

            // Handle other fields (assuming column index order)
            transactionEntry.setAccount(getCellValueAsString(row.getCell(1)));
            transactionEntry.setCategory(getCellValueAsString(row.getCell(2)));
            transactionEntry.setSubcategory(getCellValueAsString(row.getCell(3), ""));  // Default if null
            transactionEntry.setNote(getCellValueAsString(row.getCell(4), ""));
            transactionEntry.setAmount(getCellValueAsDouble(row.getCell(5), 0.0));  // Default to 0.0 if empty


            String type = getCellValueAsString(row.getCell(6), "");

            if (type.startsWith("Ex")) {
                transactionEntry.setTransactionType(TransactionType.EXPENSE);
            } else if (type.startsWith("In")) {
                transactionEntry.setTransactionType(TransactionType.INCOME);
            } else if (type.startsWith("Tr")) {
                transactionEntry.setTransactionType(TransactionType.TRANSFER_OUT);
            } else {
                // Handle cases where the type doesn't match any known prefixes
                System.err.println("Unknown transaction type: " + type);
                transactionEntry.setTransactionType(TransactionType.UNKNOWN); // Assuming you have an UNKNOWN type, or handle it accordingly
            }


            transactionEntryList.add(transactionEntry);

        }

        // Close the workbook
        workbook.close();

        return transactionEntryList;
    }

    /**
     * Helper method to handle date cell and set formatted date in transaction entry.
     */
    private void handleDateCell(Cell cell, TransactionEntry transactionEntry) {
        if (cell == null) return;

        switch (cell.getCellType()) {
            case STRING:
                transactionEntry.setHumanReadableDate(cell.getStringCellValue());
                break;
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    Date date = cell.getDateCellValue();
                    String formattedDate = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(date);
                    transactionEntry.setHumanReadableDate(formattedDate);
                }
                break;
            default:
        }

        if (transactionEntry.getHumanReadableDate() != null) {
            try {
                SimpleDateFormat[] dateFormats = {
                        new SimpleDateFormat("MM/dd/yyyy HH:mm:ss"), // Format with time
                        new SimpleDateFormat("MM/dd/yyyy")          // Format without time
                };


                for (SimpleDateFormat dateFormat : dateFormats) {
                    try {
                        Date parsedDate = dateFormat.parse(transactionEntry.getHumanReadableDate());
                        long timestamp = parsedDate.getTime();
                        transactionEntry.setTimestamp(timestamp);
                        break;
                    } catch (ParseException e) {}
                }


            } catch (Exception e) {
            }
        }
    }

    /**
     * Helper method to safely get the string value from a cell, or return default if null.
     */
    private String getCellValueAsString(Cell cell) {
        return getCellValueAsString(cell, null);
    }

    /**
     * Helper method to safely get the string value from a cell, or return a provided default.
     */
    private String getCellValueAsString(Cell cell, String defaultValue) {
        if (cell == null) return defaultValue;

        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                return String.valueOf(cell.getNumericCellValue());
            default:
                return defaultValue;
        }
    }

    /**
     * Helper method to safely get the double value from a cell, or return a provided default.
     */
    private double getCellValueAsDouble(Cell cell, double defaultValue) {
        if (cell == null) return defaultValue;

        switch (cell.getCellType()) {
            case NUMERIC:
                return cell.getNumericCellValue();
            case STRING:
                try {
                    return Double.parseDouble(cell.getStringCellValue());
                } catch (NumberFormatException e) {
                    return defaultValue;
                }
            default:
                return defaultValue;
        }
    }

    public static void writeTransactionToFile(TransactionEntry transactionEntry) {
        BufferedWriter writer = null;

        try {
            // Create a BufferedWriter to write to the file
//            File file = new File("C:\\Users\\prita\\OneDrive\\Documents");
            File file = new File("C:\\Users\\prita\\OneDrive\\Documents\\transactions.txt");

            writer = new BufferedWriter(new FileWriter(file, true)); // 'true' for appending to the file

            // Writing the transaction entry details to the file
            writer.write(transactionEntry + "\n");

//            System.out.println("Transaction written to file successfully.");
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        } finally {
            try {
                if (writer != null) {
                    writer.close(); // Close the writer
                }
            } catch (IOException e) {
                System.err.println("Error closing writer: " + e.getMessage());
            }
        }
    }





}
