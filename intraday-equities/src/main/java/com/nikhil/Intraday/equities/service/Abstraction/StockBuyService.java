package com.nikhil.Intraday.equities.service.Abstraction;

import com.nikhil.Intraday.equities.modal.PlaceOrder;

public interface StockBuyService {
    void getScripToPlaceOrder(String fromDate, String toDate, String symbol);
}
