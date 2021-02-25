package com.nikhil.optionstradingapp.model;

public enum OrderType {
    MKT("MKT"),
    L("L"),
    SL("SL");

    private final String value;

    OrderType(String value) {
        this.value = value;
    }
}
