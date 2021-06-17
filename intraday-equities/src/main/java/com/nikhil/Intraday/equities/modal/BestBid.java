package com.nikhil.Intraday.equities.modal;

import lombok.Data;

@Data
public class BestBid {
    private String number;
    private String quantity;
    private String price;

    @Override
    public String toString() {
        return "BestBid{" +
                "number='" + number + '\'' +
                ", quantity='" + quantity + '\'' +
                ", price='" + price + '\'' +
                '}';
    }
}
