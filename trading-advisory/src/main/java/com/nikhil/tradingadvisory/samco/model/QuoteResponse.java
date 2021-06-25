package com.nikhil.tradingadvisory.samco.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuoteResponse {

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
    private String tickSize;
    private String openInterest;
    private List<BestBid> bestBids = null;
    private List<BestAsk> bestAsks = null;
    private String expiryDate;
    private String spotPrice;
    private String instrument;
    private String lotQuantity;
    private String listingId;
    private String openInterestChange;
    private String getoIChangePer;

}