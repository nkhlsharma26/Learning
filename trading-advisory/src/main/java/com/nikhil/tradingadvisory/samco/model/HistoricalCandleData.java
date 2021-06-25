package com.nikhil.tradingadvisory.samco.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HistoricalCandleData {

    public String date;
    public String open;
    public String high;
    public String low;
    public String close;
    public String ltp;
    public String volume;

}