package com.nikhil.optionstradingapp.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class BestAsks {
    @JsonProperty("number")
    private String number;
    @JsonProperty("quantity")
    private String quantity;
    @JsonProperty("price")
    private String price;

    @Override
    public String toString() {
        return "BestAsks{" +
                "number='" + number + '\'' +
                ", quantity='" + quantity + '\'' +
                ", price='" + price + '\'' +
                '}';
    }
}
