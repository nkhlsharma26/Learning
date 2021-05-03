package com.nikhil.Intraday.equities.modal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
