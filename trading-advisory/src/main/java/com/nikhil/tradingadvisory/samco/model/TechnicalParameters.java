package com.nikhil.tradingadvisory.samco.model;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class TechnicalParameters {
    private String sma20;
    private String sma50;
    private String sma200;
    private String rsi;
    private String scripName;
    private String lastClose;
}
