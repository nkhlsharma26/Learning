package com.nikhil.optionstradingapp.model;


import java.util.List;

public class AuthResponse {
    private String serverTime;
    private String msgId;
    private String status;
    private String statusMessage;
    private String sessionToken;
    private String accountID;
    private String accountName;
    private List<String> exchangeList;
    private List<String> orderTypeList;
    private List<String> productList;

    public String getServerTime() {
        return serverTime;
    }

    public void setServerTime(String serverTime) {
        this.serverTime = serverTime;
    }

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }

    public String getSessionToken() {
        return sessionToken;
    }

    public void setSessionToken(String sessionToken) {
        this.sessionToken = sessionToken;
    }

    public String getAccountID() {
        return accountID;
    }

    public void setAccountID(String accountID) {
        this.accountID = accountID;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public List<String> getExchangeList() {
        return exchangeList;
    }

    public void setExchangeList(List<String> exchangeList) {
        this.exchangeList = exchangeList;
    }

    public List<String> getOrderTypeList() {
        return orderTypeList;
    }

    public void setOrderTypeList(List<String> orderTypeList) {
        this.orderTypeList = orderTypeList;
    }

    public List<String> getProductList() {
        return productList;
    }

    public void setProductList(List<String> productList) {
        this.productList = productList;
    }

    @Override
    public String toString() {
        return "AuthResponse{" +
                "serverTime='" + serverTime + '\'' +
                ", msgId='" + msgId + '\'' +
                ", status='" + status + '\'' +
                ", statusMessage='" + statusMessage + '\'' +
                ", sessionToken='" + sessionToken + '\'' +
                ", accountId='" + accountID + '\'' +
                ", accountName='" + accountName + '\'' +
                ", exchangeList=" + exchangeList +
                ", orderTypeList=" + orderTypeList +
                ", productList=" + productList +
                '}';
    }
}
