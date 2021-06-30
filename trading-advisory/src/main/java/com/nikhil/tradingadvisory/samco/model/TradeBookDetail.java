package com.nikhil.tradingadvisory.samco.model;

import lombok.Data;

@Data
public class TradeBookDetail {
    private String orderNumber;
    private String exchange;
    private String tradingSymbol;
    private String symbolDescription;
    private String transactionType;
    private String productCode;
    private String orderType;
    private String orderPrice;
    private String quantity;
    private String disclosedQuantity;
    private String triggerPrice;
    private String marketProtection;
    private String orderValidity;
    private String orderStatus;
    private String orderValue;
    private String instrumentName;
    private String orderTime;
    private String userId;
    private String filledQuantity;
    private String unfilledQuantity;
    private String exchangeConfirmationTime;
    private String coverOrderPercentage;
    private String exchangeOrderNumber;
    private String tradeNumber;
    private String tradePrice;
    private String tradeDate;
    private String tradeTime;
    private String strikePrice;
    private String optionType;
    private String lastTradePrice;
    private String expiry;

    @Override
    public String toString() {
        return "TradeBookDetail{" +
                "orderNumber='" + orderNumber + '\'' +
                ", exchange='" + exchange + '\'' +
                ", tradingSymbol='" + tradingSymbol + '\'' +
                ", symbolDescription='" + symbolDescription + '\'' +
                ", transactionType='" + transactionType + '\'' +
                ", productCode='" + productCode + '\'' +
                ", orderType='" + orderType + '\'' +
                ", orderPrice='" + orderPrice + '\'' +
                ", quantity='" + quantity + '\'' +
                ", disclosedQuantity='" + disclosedQuantity + '\'' +
                ", triggerPrice='" + triggerPrice + '\'' +
                ", marketProtection='" + marketProtection + '\'' +
                ", orderValidity='" + orderValidity + '\'' +
                ", orderStatus='" + orderStatus + '\'' +
                ", orderValue='" + orderValue + '\'' +
                ", instrumentName='" + instrumentName + '\'' +
                ", orderTime='" + orderTime + '\'' +
                ", userId='" + userId + '\'' +
                ", filledQuantity='" + filledQuantity + '\'' +
                ", unfilledQuantity='" + unfilledQuantity + '\'' +
                ", exchangeConfirmationTime='" + exchangeConfirmationTime + '\'' +
                ", coverOrderPercentage='" + coverOrderPercentage + '\'' +
                ", exchangeOrderNumber='" + exchangeOrderNumber + '\'' +
                ", tradeNumber='" + tradeNumber + '\'' +
                ", tradePrice='" + tradePrice + '\'' +
                ", tradeDate='" + tradeDate + '\'' +
                ", tradeTime='" + tradeTime + '\'' +
                ", strikePrice='" + strikePrice + '\'' +
                ", optionType='" + optionType + '\'' +
                ", lastTradePrice='" + lastTradePrice + '\'' +
                ", expiry='" + expiry + '\'' +
                '}';
    }
}
