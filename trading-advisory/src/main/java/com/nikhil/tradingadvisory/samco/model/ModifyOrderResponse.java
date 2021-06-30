package com.nikhil.tradingadvisory.samco.model;

import lombok.Data;

@Data
public class ModifyOrderResponse {
    private String serverTime;
    private String msgId;
    private String orderNumber;
    private String status;
    private String statusMessage;
    private String exchangeOrderStatus;
    private String rejectionReason;
    private ModifyOrderResponseOrderDetails orderDetails;

    @Override
    public String toString() {
        return "ModifyOrderResponse{" +
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
