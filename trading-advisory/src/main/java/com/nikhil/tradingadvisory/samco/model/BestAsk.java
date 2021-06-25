package com.nikhil.tradingadvisory.samco.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BestAsk {
    private String number;
    private String quantity;
    private String price;

}