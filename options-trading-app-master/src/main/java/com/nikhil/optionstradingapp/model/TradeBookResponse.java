package com.nikhil.optionstradingapp.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class TradeBookResponse {
    @JsonProperty("serverTime")
    private String serverTime;
    @JsonProperty("msgId")
    private String msgId;
    @JsonProperty("status")
    private String status;
    @JsonProperty("statusMessage")
    private String statusMessage;
    @JsonProperty("tradeBookDetails")
    private List<TradeBookDetails> tradeBookDetails = null;

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
