package com.nikhil.optionstradingapp.model;

import lombok.Data;

@Data
public class OrderStatus {
    private String serverTime;
    private String msgId;
    private String orderNumber;
    private String orderStatus;
    private OrderDetails orderDetails;

    @Override
    public String toString() {
        return "OrderStatus{" +
                "serverTime='" + serverTime + '\'' +
                ", msgId='" + msgId + '\'' +
                ", orderNumber='" + orderNumber + '\'' +
                ", orderStatus='" + orderStatus + '\'' +
                ", orderDetails=" + orderDetails +
                '}';
    }
}
