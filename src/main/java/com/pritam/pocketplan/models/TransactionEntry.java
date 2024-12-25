package com.pritam.pocketplan.models;

import com.pritam.pocketplan.values.TransactionType;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "transaction_entries")
public class TransactionEntry {

    @Id
    private String id;
    private Long timestamp;
    private String humanReadableDate;
    private String account; // Fixed capitalization
    private String category; // Fixed spelling
    private String subcategory; // Fixed spelling
    private String note;
    private Double amount;
    private TransactionType transactionType;

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public String getHumanReadableDate() {
        return humanReadableDate;
    }

    public void setHumanReadableDate(String humanReadableDate) {
        this.humanReadableDate = humanReadableDate;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSubcategory() {
        return subcategory;
    }

    public void setSubcategory(String subcategory) {
        this.subcategory = subcategory;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    @Override
    public String toString() {
        return "TransactionEntry{" +
                "timestamp=" + timestamp +
                ", humanReadableDate='" + humanReadableDate + '\'' +
                ", account='" + account + '\'' +
                ", category='" + category + '\'' +
                ", subcategory='" + subcategory + '\'' +
                ", note='" + note + '\'' +
                ", amount=" + amount +
                ", transactionType=" + transactionType +
                '}';
    }
}
