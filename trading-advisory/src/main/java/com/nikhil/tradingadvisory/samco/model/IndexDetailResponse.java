package com.nikhil.tradingadvisory.samco.model;
import lombok.Data;

@Data
public  class IndexDetailResponse {
    private  String serverTime;
    private  String msgId;
    private  String status;
    private  String statusMessage;
    private  String indexName;
    private  int listingId;
    private  String lastTradedTime;
    private  double spotPrice;
    private  double changePercentage;
    private  double averagePrice;
    private  double openValue;
    private  double highValue;
    private  double lowValue;
    private  double closeValue;
    private  int totalBuyQuantity;
    private  int totalSellQuantity;
    private  int totalTradedValue;
    private  int totalTradedVolume;
    private  double change;

    @Override
    public String toString() {
        return "IndexDetailResponse{" +
                "serverTime='" + serverTime + '\'' +
                ", msgId='" + msgId + '\'' +
                ", status='" + status + '\'' +
                ", statusMessage='" + statusMessage + '\'' +
                ", indexName='" + indexName + '\'' +
                ", listingId=" + listingId +
                ", lastTradedTime='" + lastTradedTime + '\'' +
                ", spotPrice=" + spotPrice +
                ", changePercentage=" + changePercentage +
                ", averagePrice=" + averagePrice +
                ", openValue=" + openValue +
                ", highValue=" + highValue +
                ", lowValue=" + lowValue +
                ", closeValue=" + closeValue +
                ", totalBuyQuantity=" + totalBuyQuantity +
                ", totalSellQuantity=" + totalSellQuantity +
                ", totalTradedValue=" + totalTradedValue +
                ", totalTradedVolume=" + totalTradedVolume +
                ", change=" + change +
                '}';
    }
}
