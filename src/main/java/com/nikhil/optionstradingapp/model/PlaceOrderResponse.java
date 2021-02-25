package com.nikhil.optionstradingapp.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PlaceOrderResponse {
    @JsonProperty("serverTime")
    private String serverTime;
    @JsonProperty("msgId")
    private String msgId;
    @JsonProperty("orderNumber")
    private String orderNumber;
    @JsonProperty("orderStatus")
    private String orderStatus;
    @JsonProperty("statusMessage")
    private String statusMessage;
    @JsonProperty("exchangeOrderStatus")
    private String exchangeOrderStatus;
    @JsonProperty("rejectionReason")
    private String rejectionReason;
    @JsonProperty("orderDetails")
    private OrderDetails orderDetails;

    public String getServerTime() {
        return serverTime;
    }

    public void setServerTime(String serverTime) {
        this.serverTime = serverTime;
    }

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }

    public String getExchangeOrderStatus() {
        return exchangeOrderStatus;
    }

    public void setExchangeOrderStatus(String exchangeOrderStatus) {
        this.exchangeOrderStatus = exchangeOrderStatus;
    }

    public String getRejectionReason() {
        return rejectionReason;
    }

    public void setRejectionReason(String rejectionReason) {
        this.rejectionReason = rejectionReason;
    }

    public OrderDetails getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(OrderDetails orderDetails) {
        this.orderDetails = orderDetails;
    }

    @Override
    public String toString() {
        return "PlaceOrderResponse{" +
                "serverTime='" + serverTime + '\'' +
                ", msgId='" + msgId + '\'' +
                ", orderNumber='" + orderNumber + '\'' +
                ", orderStatus='" + orderStatus + '\'' +
                ", statusMessage='" + statusMessage + '\'' +
                ", exchangeOrderStatus='" + exchangeOrderStatus + '\'' +
                ", rejectionReason='" + rejectionReason + '\'' +
                ", orderDetails=" + orderDetails +
                '}';
    }
}
