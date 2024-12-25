package com.pritam.pocketplan.models;

import com.pritam.pocketplan.values.TransactionType;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "catagories")
public class Catagory {

    @Id
    private String id;
    private String name;
    private TransactionType transactionType;
    private String parentCatagory;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    public String getParentCatagory() {
        return parentCatagory;
    }

    public void setParentCatagory(String parentCatagory) {
        this.parentCatagory = parentCatagory;
    }
}
