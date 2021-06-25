package com.nikhil.tradingadvisory.samco.model;

import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PreviousDayDataResponse {
    public String serverTime;
    public String msgId;
    public String status;
    public String statusMessage;
    public List<HistoricalCandleData> historicalCandleData = null;

}