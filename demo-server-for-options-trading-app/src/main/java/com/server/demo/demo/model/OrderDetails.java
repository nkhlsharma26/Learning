package com.server.demo.demo.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class OrderDetails {
    @JsonProperty("pendingQuantity")
    private String pendingQuantity;
    @JsonProperty("avgExecutionPrice")
    private String avgExecutionPrice;
    @JsonProperty("orderPlacedBy")
    private String orderPlacedBy;
    @JsonProperty("tradingSymbol")
    private String tradingSymbol;
    @JsonProperty("triggerPrice")
    private String triggerPrice;
    @JsonProperty("expiry")
    private String expiry;
    @JsonProperty("exchange")
    private String exchange;
    @JsonProperty("totalQuantity")
    private String totalQuantity;
    @JsonProperty("transactionType")
    private String transactionType;
    @JsonProperty("productType")
    private String productType;
    @JsonProperty("orderType")
    private String orderType;
    @JsonProperty("quantity")
    private String quantity;
    @JsonProperty("filledQuantity")
    private String filledQuantity;
    @JsonProperty("orderPrice")
    private String orderPrice;
    @JsonProperty("filledPrice")
    private String filledPrice;
    @JsonProperty("orderValidity")
    private String orderValidity;
    @JsonProperty("orderTime")
    private String orderTime;
    @JsonProperty("exchangeOrderNo")
    private String exchangeOrderNo;

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
                ", exchangeOrderNo='" + exchangeOrderNo + '\'' +
                ", transactionType='" + transactionType + '\'' +
                ", productType='" + productType + '\'' +
                ", orderType='" + orderType + '\'' +
                ", quantity='" + quantity + '\'' +
                ", filledQuantity='" + filledQuantity + '\'' +
                ", orderPrice='" + orderPrice + '\'' +
                ", filledPrice='" + filledPrice + '\'' +
                ", orderValidity='" + orderValidity + '\'' +
                ", orderTime='" + orderTime + '\'' +
                '}';
    }
}
