package com.nikhil.Intraday.equities.service.Abstraction;

import com.nikhil.Intraday.equities.modal.OrderDetails;
import com.nikhil.Intraday.equities.modal.PlaceOrder;

public interface PlaceOrderService {
    Boolean buyStock(PlaceOrder placeOrder, String authToken);
    PlaceOrder getOrderDetails();
}
