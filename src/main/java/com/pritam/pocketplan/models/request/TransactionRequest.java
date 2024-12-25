package com.pritam.pocketplan.models.request;

import com.pritam.pocketplan.values.TransactionType;

import java.util.ArrayList;
import java.util.List;

public class TransactionRequest {
    private Long startTime;
    private Long endTime;
    private List<String> accounts;
    private List<String> catagories;
    private TransactionType transactionType;

    public Long getStartTime() {
        return startTime;
    }

    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }

    public Long getEndTime() {
        return endTime;
    }

    public void setEndTime(Long endTime) {
        this.endTime = endTime;
    }

    public List<String> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<String> accounts) {
        this.accounts = accounts;
    }

    public void addAccount(String account){
        if(this.accounts == null){
            this.accounts = new ArrayList<>();
        }
        this.accounts.add(account);
    }

    public List<String> getCatagories() {
        return catagories;
    }

    public void setCatagories(List<String> catagories) {
        this.catagories = catagories;
    }

    public void addCatagory(String catgory){
        if(this.catagories == null){
            this.catagories = new ArrayList<>();
        }
        this.catagories.add(catgory);
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    @Override
    public String toString() {
        return "TransactionRequest{" +
                "startTime=" + startTime +
                ", endTime=" + endTime +
                ", accounts=" + accounts +
                ", categories=" + catagories +
                ", transactionType=" + transactionType +
                '}';
    }

}
