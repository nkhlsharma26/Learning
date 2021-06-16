package com.nikhil.messageserver.Model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class OrderDetail {
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

    public OrderDetail(String pendingQuantity, String avgExecutionPrice, String orderPlacedBy, String tradingSymbol, String triggerPrice, String expiry, String exchange, String totalQuantity, String transactionType, String productType, String orderType, String quantity, String filledQuantity, String orderPrice, String filledPrice, String orderValidity, String orderTime, String exchangeOrderNo) {
        this.pendingQuantity = pendingQuantity;
        this.avgExecutionPrice = avgExecutionPrice;
        this.orderPlacedBy = orderPlacedBy;
        this.tradingSymbol = tradingSymbol;
        this.triggerPrice = triggerPrice;
        this.expiry = expiry;
        this.exchange = exchange;
        this.totalQuantity = totalQuantity;
        this.transactionType = transactionType;
        this.productType = productType;
        this.orderType = orderType;
        this.quantity = quantity;
        this.filledQuantity = filledQuantity;
        this.orderPrice = orderPrice;
        this.filledPrice = filledPrice;
        this.orderValidity = orderValidity;
        this.orderTime = orderTime;
        this.exchangeOrderNo = exchangeOrderNo;
    }

    @Override
    public String toString() {
        return  "pendingQuantity='" + pendingQuantity + ",'\r\n" +
                " avgExecutionPrice='" + avgExecutionPrice + ",'\r\n" +
                " orderPlacedBy='" + orderPlacedBy + ",'\r\n"  +
                " tradingSymbol='" + tradingSymbol +",'\r\n" +
                " triggerPrice='" + triggerPrice +",'\r\n" +
                " exchange='" + exchange + ",'\r\n"  +
                " totalQuantity='" + totalQuantity + ",'\r\n"  +
                " expiry='" + expiry + ",'\r\n"  +
                " exchangeOrderNo='" + exchangeOrderNo +",'\r\n"  +
                " transactionType='" + transactionType + ",'\r\n"  +
                " productType='" + productType + ",'\r\n"  +
                " orderType='" + orderType + ",'\r\n" +
                " quantity='" + quantity + ",'\r\n" +
                " filledQuantity='" + filledQuantity + ",'\r\n" +
                " orderPrice='" + orderPrice + ",'\r\n" +
                " filledPrice='" + filledPrice + ",'\r\n"  +
                " orderValidity='" + orderValidity +",'\r\n" +
                " orderTime='" + orderTime + ",'\r\n" ;
    }
}