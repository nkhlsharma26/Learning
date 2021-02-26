package com.nikhil.optionstradingapp.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class OrderData implements Serializable {
    private static final long serialVersionUID = 1L;
    private int strikePrice;
    private String symbol;

    public OrderData(int strikePrice, String symbol) {
        this.strikePrice = strikePrice;
        this.symbol = symbol;
    }
}
