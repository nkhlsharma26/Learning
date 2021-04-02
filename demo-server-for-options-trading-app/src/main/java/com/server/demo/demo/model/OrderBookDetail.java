package com.server.demo.demo.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class OrderBookDetail {
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
    @JsonProperty("fillPrice")
    private String fillPrice;
    @JsonProperty("averagePrice")
    private String averagePrice;
    @JsonProperty("unfilledQuantity")
    private String unfilledQuantity;
    @JsonProperty("exchangeOrderId")
    private String exchangeOrderId;
    @JsonProperty("rejectionReason")
    private String rejectionReason;
    @JsonProperty("exchangeConfirmationTime")
    private String exchangeConfirmationTime;
    @JsonProperty("cancelledQuantity")
    private String cancelledQuantity;
    @JsonProperty("referenceLimitPrice")
    private String referenceLimitPrice;
    @JsonProperty("coverOrderPercentage")
    private String coverOrderPercentage;
    @JsonProperty("orderRemarks")
    private String orderRemarks;
    @JsonProperty("exchangeOrderNumber")
    private String exchangeOrderNumber;
    @JsonProperty("symbol")
    private String symbol;
    @JsonProperty("displayStrikePrice")
    private String displayStrikePrice;
    @JsonProperty("displayNetQuantity")
    private String displayNetQuantity;
    @JsonProperty("status")
    private String status;
    @JsonProperty("exchangeStatus")
    private String exchangeStatus;
    @JsonProperty("expiry")
    private String expiry;
    @JsonProperty("pendingQuantity")
    private String pendingQuantity;
    @JsonProperty("instrument")
    private String instrument;
    @JsonProperty("scripName")
    private String scripName;
    @JsonProperty("totalQuanity")
    private String totalQuanity;
    @JsonProperty("optionType")
    private String optionType;
    @JsonProperty("orderPlaceBy")
    private String orderPlaceBy;
    @JsonProperty("lotQuantity")
    private String lotQuantity;
    @JsonProperty("parentOrderId")
    private String parentOrderId;

    @Override
    public String toString() {
        return "OrderBookDetail{" +
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
                ", fillPrice='" + fillPrice + '\'' +
                ", averagePrice='" + averagePrice + '\'' +
                ", unfilledQuantity='" + unfilledQuantity + '\'' +
                ", exchangeOrderId='" + exchangeOrderId + '\'' +
                ", rejectionReason='" + rejectionReason + '\'' +
                ", exchangeConfirmationTime='" + exchangeConfirmationTime + '\'' +
                ", cancelledQuantity='" + cancelledQuantity + '\'' +
                ", referenceLimitPrice='" + referenceLimitPrice + '\'' +
                ", coverOrderPercentage='" + coverOrderPercentage + '\'' +
                ", orderRemarks='" + orderRemarks + '\'' +
                ", exchangeOrderNumber='" + exchangeOrderNumber + '\'' +
                ", symbol='" + symbol + '\'' +
                ", displayStrikePrice='" + displayStrikePrice + '\'' +
                ", displayNetQuantity='" + displayNetQuantity + '\'' +
                ", status='" + status + '\'' +
                ", exchangeStatus='" + exchangeStatus + '\'' +
                ", expiry='" + expiry + '\'' +
                ", pendingQuantity='" + pendingQuantity + '\'' +
                ", instrument='" + instrument + '\'' +
                ", scripName='" + scripName + '\'' +
                ", totalQuanity='" + totalQuanity + '\'' +
                ", optionType='" + optionType + '\'' +
                ", orderPlaceBy='" + orderPlaceBy + '\'' +
                ", lotQuantity='" + lotQuantity + '\'' +
                ", parentOrderId='" + parentOrderId + '\'' +
                '}';
    }
}
