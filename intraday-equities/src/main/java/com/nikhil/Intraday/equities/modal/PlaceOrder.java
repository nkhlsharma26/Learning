package com.nikhil.Intraday.equities.modal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlaceOrder {
        private String symbolName;
        private String exchange;
        private String transactionType;
        private String orderType;
        private String quantity;
        private String disclosedQuantity;
        private String price;
        private String priceType;
        private String marketProtection;
        private String orderValidity;
        private String afterMarketOrderFlag;
        private String productType;
        private String triggerPrice;
}
