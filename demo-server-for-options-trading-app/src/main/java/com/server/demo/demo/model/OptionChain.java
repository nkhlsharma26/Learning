package com.server.demo.demo.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class OptionChain {
    @JsonProperty("serverTime")
    private String serverTime;
    @JsonProperty("msgId")
    private String msgId;
    @JsonProperty("status")
    private String status;
    @JsonProperty("statusMessage")
    private String statusMessage;
    @JsonProperty("optionChainDetails")
    private List<OptionChainDetail> optionChainDetails;

    @Override
    public String toString() {
        return "OptionChain{" +
                "serverTime='" + serverTime + '\'' +
                ", msgId='" + msgId + '\'' +
                ", status='" + status + '\'' +
                ", statusMessage='" + statusMessage + '\'' +
                ", optionChainDetails=" + optionChainDetails +
                '}';
    }
}
