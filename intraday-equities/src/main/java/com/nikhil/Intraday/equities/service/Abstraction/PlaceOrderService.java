package com.nikhil.Intraday.equities.service.Abstraction;

import com.nikhil.Intraday.equities.modal.PlaceOrder;

public interface PlaceOrderService {
    void buyStock(PlaceOrder placeOrder);
}
