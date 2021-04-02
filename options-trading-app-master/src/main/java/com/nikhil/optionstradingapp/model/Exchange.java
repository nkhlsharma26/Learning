package com.nikhil.optionstradingapp.model;

public enum Exchange {
    BSE("BSE"),
    BFO("BFO"),
    CDS("CDS"),
    NSE("NSE"),
    NFO("NFO");
    private String value;

    Exchange(String value) {
        this.value = value;
    }
}
