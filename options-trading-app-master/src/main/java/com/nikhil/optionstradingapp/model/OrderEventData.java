package com.nikhil.optionstradingapp.model;

import lombok.Data;

@Data
public class OrderEventData {
    private Integer quantity;
    private Integer lotSize;
    private String tradingSymbol;
    private String transactionType;
}
