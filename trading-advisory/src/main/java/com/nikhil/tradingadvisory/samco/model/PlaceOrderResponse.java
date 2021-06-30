package com.nikhil.tradingadvisory.samco.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlaceOrderResponse {
    private String serverTime;
    private String msgId;
    private String orderNumber;
    private String status;
    private String statusMessage;
    private String exchangeOrderStatus;
    private String rejectionReason;
    private OrderDetails orderDetails;

    @Override
    public String toString() {
        return "PlaceOrderResponse{" +
                "serverTime='" + serverTime + '\'' +
                ", msgId='" + msgId + '\'' +
                ", orderNumber='" + orderNumber + '\'' +
                ", status='" + status + '\'' +
                ", statusMessage='" + statusMessage + '\'' +
                ", exchangeOrderStatus='" + exchangeOrderStatus + '\'' +
                ", rejectionReason='" + rejectionReason + '\'' +
                ", orderDetails=" + orderDetails +
                '}';
    }
}
