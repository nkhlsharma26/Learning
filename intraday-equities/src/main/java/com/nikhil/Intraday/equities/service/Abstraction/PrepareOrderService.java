package com.nikhil.Intraday.equities.service.Abstraction;

import com.nikhil.Intraday.equities.modal.PlaceOrder;

public interface PrepareOrderService {
    void getScripToPlaceOrder(String fromDate, String toDate, String symbol);
}
