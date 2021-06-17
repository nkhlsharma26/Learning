package com.nikhil.Intraday.equities.modal;

import lombok.Data;

import java.util.List;

@Data
public class ScripQuoteDetailResponse {
    private String serverTime;
    private String msgId;
    private String status;
    private String statusMessage;
    private String symbolName;
    private String tradingSymbol;
    private String exchange;
    private String companyName;
    private String lastTradedTime;
    private String lastTradedPrice;
    private String previousClose;
    private String changeValue;
    private String changePercentage;
    private String lastTradedQuantity;
    private String lowerCircuitLimit;
    private String upperCircuitLimit;
    private String averagePrice;
    private String openValue;
    private String highValue;
    private String lowValue;
    private String closeValue;
    private String totalBuyQuantity;
    private String totalSellQuantity;
    private String totalTradedValue;
    private String totalTradedVolume;
    private String yearlyHighPrice;
    private String yearlyLowPrice;
    private List<BestBid> bestBids;
    private List<BestAsk> bestAsks;
    private String listingId;

    @Override
    public String toString() {
        return "ScripQuoteDetailResponse{" +
                "serverTime='" + serverTime + '\'' +
                ", msgId='" + msgId + '\'' +
                ", status='" + status + '\'' +
                ", statusMessage='" + statusMessage + '\'' +
                ", symbolName='" + symbolName + '\'' +
                ", tradingSymbol='" + tradingSymbol + '\'' +
                ", exchange='" + exchange + '\'' +
                ", companyName='" + companyName + '\'' +
                ", lastTradedTime='" + lastTradedTime + '\'' +
                ", lastTradedPrice='" + lastTradedPrice + '\'' +
                ", previousClose='" + previousClose + '\'' +
                ", changeValue='" + changeValue + '\'' +
                ", changePercentage='" + changePercentage + '\'' +
                ", lastTradedQuantity='" + lastTradedQuantity + '\'' +
                ", lowerCircuitLimit='" + lowerCircuitLimit + '\'' +
                ", upperCircuitLimit='" + upperCircuitLimit + '\'' +
                ", averagePrice='" + averagePrice + '\'' +
                ", openValue='" + openValue + '\'' +
                ", highValue='" + highValue + '\'' +
                ", lowValue='" + lowValue + '\'' +
                ", closeValue='" + closeValue + '\'' +
                ", totalBuyQuantity='" + totalBuyQuantity + '\'' +
                ", totalSellQuantity='" + totalSellQuantity + '\'' +
                ", totalTradedValue='" + totalTradedValue + '\'' +
                ", totalTradedVolume='" + totalTradedVolume + '\'' +
                ", yearlyHighPrice='" + yearlyHighPrice + '\'' +
                ", yearlyLowPrice='" + yearlyLowPrice + '\'' +
                ", bestBids=" + bestBids +
                ", bestAsks=" + bestAsks +
                ", listingId='" + listingId + '\'' +
                '}';
    }
}
