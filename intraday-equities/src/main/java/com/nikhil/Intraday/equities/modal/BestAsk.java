package com.nikhil.Intraday.equities.modal;

import lombok.Data;

@Data
public class BestAsk {
    private String number;
    private String quantity;
    private String price;

    @Override
    public String toString() {
        return "BestAsk{" +
                "number='" + number + '\'' +
                ", quantity='" + quantity + '\'' +
                ", price='" + price + '\'' +
                '}';
    }
}
