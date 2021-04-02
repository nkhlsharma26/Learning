package com.server.demo.demo.model;

public enum OrderType {
    MKT("MKT"),
    L("L"),
    SL("SL");

    private final String value;

    OrderType(String value) {
        this.value = value;
    }
}
