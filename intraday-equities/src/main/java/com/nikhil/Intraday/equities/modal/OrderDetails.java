package com.nikhil.Intraday.equities.modal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetails {
    private String pendingQuantity;
    private String avgExecutionPrice;
    private String orderPlacedBy;
    private String tradingSymbol;
    private String triggerPrice;
    private String exchange;
    private String totalQuantity;
    private String expiry;
    private String transactionType;
    private String productType;
    private String orderType;
    private String quantity;
    private String filledQuantity;
    private String orderPrice;
    private String filledPrice;
    private String exchangeOrderNo;
    private String orderValidity;
    private String orderTime;

    @Override
    public String toString() {
        return "OrderDetails{" +
                "pendingQuantity='" + pendingQuantity + '\'' +
                ", avgExecutionPrice='" + avgExecutionPrice + '\'' +
                ", orderPlacedBy='" + orderPlacedBy + '\'' +
                ", tradingSymbol='" + tradingSymbol + '\'' +
                ", triggerPrice='" + triggerPrice + '\'' +
                ", exchange='" + exchange + '\'' +
                ", totalQuantity='" + totalQuantity + '\'' +
                ", expiry='" + expiry + '\'' +
                ", transactionType='" + transactionType + '\'' +
                ", productType='" + productType + '\'' +
                ", orderType='" + orderType + '\'' +
                ", quantity='" + quantity + '\'' +
                ", filledQuantity='" + filledQuantity + '\'' +
                ", orderPrice='" + orderPrice + '\'' +
                ", filledPrice='" + filledPrice + '\'' +
                ", exchangeOrderNo='" + exchangeOrderNo + '\'' +
                ", orderValidity='" + orderValidity + '\'' +
                ", orderTime='" + orderTime + '\'' +
                '}';
    }
}
