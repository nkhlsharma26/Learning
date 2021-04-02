package com.server.demo.demo.model;

import lombok.Data;

@Data
public class OrderStatus {
    private String serverTime;
    private String msgId;
    private String orderNumber;
    private String orderStatus;
    private OrderDetails orderDetails;
    private String statusMessage;

    @Override
    public String toString() {
        return "OrderStatus{" +
                "serverTime='" + serverTime + '\'' +
                ", msgId='" + msgId + '\'' +
                ", orderNumber='" + orderNumber + '\'' +
                ", orderStatus='" + orderStatus + '\'' +
                ", statusMessage='" + statusMessage + '\'' +
                ", orderDetails=" + orderDetails +
                '}';
    }
}
