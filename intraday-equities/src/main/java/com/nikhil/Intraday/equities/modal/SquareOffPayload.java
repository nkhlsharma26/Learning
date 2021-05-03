package com.nikhil.Intraday.equities.modal;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class SquareOffPayload {
    public String exchange;
    public String symbolName;
    public String productType;
    public String netQuantity;
    public String transactionType;

    @Override
    public String toString() {
        return "SquareOffPayload{" +
                "exchange='" + exchange + '\'' +
                ", symbolName='" + symbolName + '\'' +
                ", productType='" + productType + '\'' +
                ", netQuantity='" + netQuantity + '\'' +
                ", transactionType='" + transactionType + '\'' +
                '}';
    }
}
