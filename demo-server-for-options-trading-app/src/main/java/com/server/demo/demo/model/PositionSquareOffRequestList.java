package com.server.demo.demo.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class PositionSquareOffRequestList {
    @JsonProperty("exchange")
    private String exchange;
    @JsonProperty("symbolName")
    private String symbolName;
    @JsonProperty("productType")
    private String productType;
    @JsonProperty("netQuantity")
    private String netQuantity;
    @JsonProperty("transactionType")
    private String transactionType;

    @Override
    public String toString() {
        return "PositionSquareOffRequestList{" +
                "exchange='" + exchange + '\'' +
                ", symbolName='" + symbolName + '\'' +
                ", productType='" + productType + '\'' +
                ", netQuantity='" + netQuantity + '\'' +
                ", transactionType='" + transactionType + '\'' +
                '}';
    }
}
