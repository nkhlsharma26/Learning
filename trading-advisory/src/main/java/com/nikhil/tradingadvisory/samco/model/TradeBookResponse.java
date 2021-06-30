package com.nikhil.tradingadvisory.samco.model;

import lombok.Data;

import java.util.List;

@Data
public class TradeBookResponse {
    private String serverTime;
    private String msgId;
    private String status;
    private String statusMessage;
    private List<TradeBookDetail> tradeBookDetails;

    @Override
    public String toString() {
        return "TradeBookResponse{" +
                "serverTime='" + serverTime + '\'' +
                ", msgId='" + msgId + '\'' +
                ", status='" + status + '\'' +
                ", statusMessage='" + statusMessage + '\'' +
                ", tradeBookDetails=" + tradeBookDetails +
                '}';
    }
}
