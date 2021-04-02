package com.server.demo.demo.model;

import lombok.Data;

@Data
public class PlaceOrder {
    private String symbolName;
    private String exchange;
    private String transactionType;
    private String orderType;
    private String quantity;
    private String disclosedQuantity;
    private String orderValidity;
    private String productType;
    private String afterMarketOrderFlag;

    @Override
    public String toString() {
        return "PlaceOrder{" +
                "symbolName='" + symbolName + '\'' +
                ", exchange='" + exchange + '\'' +
                ", transactionType='" + transactionType + '\'' +
                ", orderType='" + orderType + '\'' +
                ", quantity='" + quantity + '\'' +
                ", disclosedQuantity='" + disclosedQuantity + '\'' +
                ", orderValidity='" + orderValidity + '\'' +
                ", productType='" + productType + '\'' +
                ", afterMarketOrderFlag='" + afterMarketOrderFlag + '\'' +
                '}';
    }
}
