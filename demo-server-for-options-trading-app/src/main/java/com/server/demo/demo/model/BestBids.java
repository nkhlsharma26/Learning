package com.server.demo.demo.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class BestBids {
    @JsonProperty("number")
    private String number;
    @JsonProperty("quantity")
    private String quantity;
    @JsonProperty("price")
    private String price;

    @Override
    public String toString() {
        return "BestBids{" +
                "number='" + number + '\'' +
                ", quantity='" + quantity + '\'' +
                ", price='" + price + '\'' +
                '}';
    }
}
