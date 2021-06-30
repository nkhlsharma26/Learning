package com.nikhil.tradingadvisory.samco.Abstraction;


import com.nikhil.tradingadvisory.samco.model.PlaceOrder;

public interface PlaceOrderService {
    void buyStock(PlaceOrder placeOrder, String userId);
}
