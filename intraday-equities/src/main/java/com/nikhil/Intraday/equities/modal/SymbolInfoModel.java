package com.nikhil.Intraday.equities.modal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SymbolInfoModel {
    private String symbol;
    private String high;
    private String low;
    private String close;
    private String volume;

    @Override
    public String toString() {
        return "SymbolModel{" +
                "symbol='" + symbol + '\'' +
                ", high='" + high + '\'' +
                ", low='" + low + '\'' +
                ", close='" + close + '\'' +
                ", volume='" + volume + '\'' +
                '}';
    }
}
