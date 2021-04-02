package com.nikhil.optionstradingapp.model;

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

    public String getPendingQuantity() {
        return pendingQuantity;
    }

    public void setPendingQuantity(String pendingQuantity) {
        this.pendingQuantity = pendingQuantity;
    }

    public String getAvgExecutionPrice() {
        return avgExecutionPrice;
    }

    public void setAvgExecutionPrice(String avgExecutionPrice) {
        this.avgExecutionPrice = avgExecutionPrice;
    }

    public String getOrderPlacedBy() {
        return orderPlacedBy;
    }

    public void setOrderPlacedBy(String orderPlacedBy) {
        this.orderPlacedBy = orderPlacedBy;
    }

    public String getTradingSymbol() {
        return tradingSymbol;
    }

    public void setTradingSymbol(String tradingSymbol) {
        this.tradingSymbol = tradingSymbol;
    }

    public String getTriggerPrice() {
        return triggerPrice;
    }

    public void setTriggerPrice(String triggerPrice) {
        this.triggerPrice = triggerPrice;
    }

    public String getExchange() {
        return exchange;
    }

    public void setExchange(String exchange) {
        this.exchange = exchange;
    }

    public String getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(String totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getFilledQuantity() {
        return filledQuantity;
    }

    public void setFilledQuantity(String filledQuantity) {
        this.filledQuantity = filledQuantity;
    }

    public String getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(String orderPrice) {
        this.orderPrice = orderPrice;
    }

    public String getFilledPrice() {
        return filledPrice;
    }

    public void setFilledPrice(String filledPrice) {
        this.filledPrice = filledPrice;
    }

    public String getOrderValidity() {
        return orderValidity;
    }

    public void setOrderValidity(String orderValidity) {
        this.orderValidity = orderValidity;
    }

    public String getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

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
