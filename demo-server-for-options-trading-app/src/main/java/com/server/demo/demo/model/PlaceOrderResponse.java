package com.server.demo.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@JsonIgnoreProperties
@Data
public class PlaceOrderResponse {
    @JsonProperty("serverTime")
    private String serverTime;
    @JsonProperty("msgId")
    private String msgId;
    @JsonProperty("orderNumber")
    private String orderNumber;
    @JsonProperty("status")
    private String status;
    @JsonProperty("statusMessage")
    private String statusMessage;
    @JsonProperty("exchangeOrderStatus")
    private String exchangeOrderStatus;
    @JsonProperty("rejectionReason")
    private String rejectionReason;
    @JsonProperty("orderDetails")
    private OrderDetails orderDetails;

    @Override
    public String toString() {
        return "PlaceOrderResponse{" +
                "serverTime='" + serverTime + '\'' +
                ", msgId='" + msgId + '\'' +
                ", orderNumber='" + orderNumber + '\'' +
                ", orderStatus='" + status + '\'' +
                ", statusMessage='" + statusMessage + '\'' +
                ", exchangeOrderStatus='" + exchangeOrderStatus + '\'' +
                ", rejectionReason='" + rejectionReason + '\'' +
                ", orderDetails=" + orderDetails +
                '}';
    }
}
