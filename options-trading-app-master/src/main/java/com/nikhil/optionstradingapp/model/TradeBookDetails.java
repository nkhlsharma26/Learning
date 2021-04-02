package com.nikhil.optionstradingapp.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class TradeBookDetails {
    @JsonProperty("orderNumber")
    private String orderNumber;
    @JsonProperty("exchange")
    private String exchange;
    @JsonProperty("tradingSymbol")
    private String tradingSymbol;
    @JsonProperty("symbolDescription")
    private String symbolDescription;
    @JsonProperty("transactionType")
    private String transactionType;
    @JsonProperty("productCode")
    private String productCode;
    @JsonProperty("orderType")
    private String orderType;
    @JsonProperty("orderPrice")
    private String orderPrice;
    @JsonProperty("quantity")
    private String quantity;
    @JsonProperty("disclosedQuantity")
    private String disclosedQuantity;
    @JsonProperty("triggerPrice")
    private String triggerPrice;
    @JsonProperty("marketProtection")
    private String marketProtection;
    @JsonProperty("orderValidity")
    private String orderValidity;
    @JsonProperty("orderStatus")
    private String orderStatus;
    @JsonProperty("orderValue")
    private String orderValue;
    @JsonProperty("instrumentName")
    private String instrumentName;
    @JsonProperty("orderTime")
    private String orderTime;
    @JsonProperty("userId")
    private String userId;
    @JsonProperty("filledQuantity")
    private String filledQuantity;
    @JsonProperty("unfilledQuantity")
    private String unfilledQuantity;
    @JsonProperty("exchangeConfirmationTime")
    private String exchangeConfirmationTime;
    @JsonProperty("coverOrderPercentage")
    private String coverOrderPercentage;
    @JsonProperty("exchangeOrderNumber")
    private String exchangeOrderNumber;
    @JsonProperty("tradeNumber")
    private String tradeNumber;
    @JsonProperty("tradePrice")
    private String tradePrice;
    @JsonProperty("tradeDate")
    private String tradeDate;
    @JsonProperty("tradeTime")
    private String tradeTime;
    @JsonProperty("strikePrice")
    private String strikePrice;
    @JsonProperty("optionType")
    private String optionType;
    @JsonProperty("lastTradePrice")
    private String lastTradePrice;
    @JsonProperty("expiry")
    private String expiry;

    @Override
    public String toString() {
        return "TradeBookDetails{" +
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
