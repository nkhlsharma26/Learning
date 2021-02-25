package com.nikhil.optionstradingapp.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;
@Data
public class SquareOffResponseModel {
    @JsonProperty("serverTime")
    private String serverTime;
    @JsonProperty("msgId")
    private String msgId;
    @JsonProperty("positionSquareOffResponseList")
    private List<PositionSquareOffResponseList> positionSquareOffResponseList = null;

    @Override
    public String toString() {
        return "SquareOffResponseModel{" +
                "serverTime='" + serverTime + '\'' +
                ", msgId='" + msgId + '\'' +
                ", positionSquareOffResponseList=" + positionSquareOffResponseList +
                '}';
    }
}
