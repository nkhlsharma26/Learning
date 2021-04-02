package com.nikhil.optionstradingapp.service;

import com.nikhil.optionstradingapp.model.PlaceOrder;
import com.nikhil.optionstradingapp.model.PlaceOrderResponse;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;

public interface PlaceOrderService {
    ResponseEntity<PlaceOrderResponse> placeOrderRequest(String placeOrderUrl, HttpEntity<PlaceOrder> placeOrderEntity);
}
