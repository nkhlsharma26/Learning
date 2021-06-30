package com.nikhil.tradingadvisory.samco.model;

import lombok.Data;

@Data
public class ModifyOrderPayload {
    private String orderType;

    private String quantity;
    private String disclosedQuantity;
    private String orderValidity;
    private String price;
    private String triggerPrice;
    private String parentOrderId;
    private String marketProtection;

    @Override
    public String toString() {
        return "ModifyOrderPayload{" +
                "orderType='" + orderType + '\'' +
                ", quantity='" + quantity + '\'' +
                ", disclosedQuantity='" + disclosedQuantity + '\'' +
                ", orderValidity='" + orderValidity + '\'' +
                ", price='" + price + '\'' +
                ", triggerPrice='" + triggerPrice + '\'' +
                ", parentOrderId='" + parentOrderId + '\'' +
                ", marketProtection='" + marketProtection + '\'' +
                '}';
    }
}
