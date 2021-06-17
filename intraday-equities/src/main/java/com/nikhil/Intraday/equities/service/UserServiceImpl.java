package com.nikhil.Intraday.equities.service;

import com.nikhil.Intraday.equities.modal.OrderDetails;
import com.nikhil.Intraday.equities.modal.PlaceOrder;
import com.nikhil.Intraday.equities.modal.UserDetails;
import com.nikhil.Intraday.equities.service.Abstraction.AuthorizationService;
import com.nikhil.Intraday.equities.service.Abstraction.PlaceOrderService;
import com.nikhil.Intraday.equities.service.Abstraction.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    PlaceOrderService placeOrderService;
    @Autowired
    AuthorizationService authorizationService;

    public static Map<String, String> userTokenMap = new HashMap<>();

    @Override
    public Boolean loginUser(UserDetails userDetails) {
        String authToken = authorizationService.authorizeUser(userDetails);
        if(authToken !=null){
            userTokenMap.put(userDetails.getUserId(), authToken);
            return true;
           }
        else{
            return false;
        }
    }

    @Override
    public PlaceOrder getOrderDetails() {
        return placeOrderService.getOrderDetails();
    }

    @Override
    public Boolean purchaseOrder(OrderDetails orderDetails, String userId) {
        String authtoken = userTokenMap.get(userId);
        PlaceOrder placeOrder = new PlaceOrder(orderDetails.getTradingSymbol(),orderDetails.getExchange(),orderDetails.getTransactionType(),orderDetails.getOrderType(),orderDetails.getQuantity(),"",orderDetails.getOrderPrice(),orderDetails.getOrderType(),"1",orderDetails.getOrderValidity(),"",orderDetails.getProductType(),orderDetails.getTriggerPrice());
        Boolean result = placeOrderService.buyStock(placeOrder,authtoken);
        return result;
    }
}
