package com.nikhil.optionstradingapp.model;

public enum TransactionType {
    BUY("BUY"),
    SELL("SELL");
    private final String value;

    TransactionType(String value){
        this.value = value;
    }
}
