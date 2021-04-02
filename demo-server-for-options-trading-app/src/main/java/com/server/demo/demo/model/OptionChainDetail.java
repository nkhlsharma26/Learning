package com.server.demo.demo.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class OptionChainDetail {
    @JsonProperty("tradingSymbol")
    private String tradingSymbol;
    @JsonProperty("exchange")
    private String exchange;
    @JsonProperty("symbol")
    private String symbol;
    @JsonProperty("strikePrice")
    private String strikePrice;
    @JsonProperty("expiryDate")
    private String expiryDate;
    @JsonProperty("instrument")
    private String instrument;
    @JsonProperty("optionType")
    private String optionType;
    @JsonProperty("underLyingSymbol")
    private String underLyingSymbol;
    @JsonProperty("spotPrice")
    private String spotPrice;
    @JsonProperty("lastTradedPrice")
    private String lastTradedPrice;
    @JsonProperty("openInterest")
    private String openInterest;
    @JsonProperty("openInterestChange")
    private String openInterestChange;
    @JsonProperty("oichangePer")
    private String oichangePer;
    @JsonProperty("volume")
    private String volume;
    @JsonProperty("bestBids")
    private List<BestBids> bestBids;
    @JsonProperty("bestAsks")
    private List<BestAsks> bestAsks;

    @Override
    public String toString() {
        return "OptionChainDetail{" +
                "tradingSymbol='" + tradingSymbol + '\'' +
                ", exchange='" + exchange + '\'' +
                ", symbol='" + symbol + '\'' +
                ", strikePrice='" + strikePrice + '\'' +
                ", expiryDate='" + expiryDate + '\'' +
                ", instrument='" + instrument + '\'' +
                ", optionType='" + optionType + '\'' +
                ", underLyingSymbol='" + underLyingSymbol + '\'' +
                ", spotPrice='" + spotPrice + '\'' +
                ", lastTradedPrice='" + lastTradedPrice + '\'' +
                ", openInterest='" + openInterest + '\'' +
                ", openInterestChange='" + openInterestChange + '\'' +
                ", oichangePer='" + oichangePer + '\'' +
                ", volume='" + volume + '\'' +
                ", bestBids=" + bestBids +
                ", bestAsks=" + bestAsks +
                '}';
    }
}
