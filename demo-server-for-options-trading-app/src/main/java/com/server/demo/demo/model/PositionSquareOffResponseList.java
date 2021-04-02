package com.server.demo.demo.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class PositionSquareOffResponseList {
    @JsonProperty("serverTime")
    private String serverTime;
    @JsonProperty("msgId")
    private String msgId;
    @JsonProperty("status")
    private String status;
    @JsonProperty("statusMessage")
    private String statusMessage;

    @Override
    public String toString() {
        return "PositionSquareOffResponseList{" +
                "serverTime='" + serverTime + '\'' +
                ", msgId='" + msgId + '\'' +
                ", status='" + status + '\'' +
                ", statusMessage='" + statusMessage + '\'' +
                '}';
    }
}
