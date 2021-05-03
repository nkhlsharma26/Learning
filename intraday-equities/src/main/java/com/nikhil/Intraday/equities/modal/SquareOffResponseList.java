package com.nikhil.Intraday.equities.modal;

import lombok.Data;

import java.util.List;

@Data
class SquareOffResponse{
    private String serverTime;
    private String msgId;
    private String status;
    private String statusMessage;

    @Override
    public String toString() {
        return "SquareOffResponse{" +
                "serverTime='" + serverTime + '\'' +
                ", msgId='" + msgId + '\'' +
                ", status='" + status + '\'' +
                ", statusMessage='" + statusMessage + '\'' +
                '}';
    }
}

@Data
public class SquareOffResponseList{
    private String serverTime;
    private String msgId;
    private List<SquareOffResponse> positionSquareOffResponseList;

    @Override
    public String toString() {
        return "SquareOffResponseList{" +
                "serverTime='" + serverTime + '\'' +
                ", msgId='" + msgId + '\'' +
                ", positionSquareOffResponseList=" + positionSquareOffResponseList +
                '}';
    }
}
