package com.nikhil.webscraper.modal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TechnicalParameters {
    private String sma50;
    private String sma200;
    private String rsi;
    private String scripName;

    public String getSma50() {
        return sma50;
    }

    public void setSma50(String sma50) {
        this.sma50 = sma50;
    }

    public String getSma200() {
        return sma200;
    }

    public void setSma200(String sma200) {
        this.sma200 = sma200;
    }

    public String getRsi() {
        return rsi;
    }

    public void setRsi(String rsi) {
        this.rsi = rsi;
    }

    public String getScripName() {
        return scripName;
    }

    public void setScripName(String scripName) {
        this.scripName = scripName;
    }

    @Override
    public String toString() {
        return "TechnicalParameters{" +
                "sma50='" + sma50 + '\'' +
                ", sma200='" + sma200 + '\'' +
                ", rsi='" + rsi + '\'' +
                ", scripName='" + scripName + '\'' +
                '}';
    }
}
