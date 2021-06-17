package com.nikhil.Intraday.equities.service.Abstraction;

import com.nikhil.Intraday.equities.modal.OrderDetails;
import com.nikhil.Intraday.equities.modal.PlaceOrder;
import com.nikhil.Intraday.equities.modal.UserDetails;

public interface UserService {
    Boolean loginUser(UserDetails userDetails);

    PlaceOrder getOrderDetails();

    Boolean purchaseOrder(OrderDetails orderDetails, String userId);
}
