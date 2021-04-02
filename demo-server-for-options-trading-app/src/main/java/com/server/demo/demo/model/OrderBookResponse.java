package com.server.demo.demo.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class OrderBookResponse {
    @JsonProperty("serverTime")
    private String serverTime;
    @JsonProperty("msgId")
    private String msgId;
    @JsonProperty("status")
    private String status;
    @JsonProperty("statusMessage")
    private String statusMessage;
    @JsonProperty("orderBookDetails")
    private List<OrderBookDetail> orderBookDetails = null;


    @Override
    public String toString() {
        return "OrderBookResponse{" +
                "serverTime='" + serverTime + '\'' +
                ", msgId='" + msgId + '\'' +
                ", status='" + status + '\'' +
                ", statusMessage='" + statusMessage + '\'' +
                ", orderBookDetails=" + orderBookDetails +
                '}';
    }
}
