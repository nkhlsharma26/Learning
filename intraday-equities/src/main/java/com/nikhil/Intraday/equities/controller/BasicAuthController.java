package com.nikhil.Intraday.equities.controller;

import com.nikhil.Intraday.equities.config.model.AuthenticationBean;
import com.nikhil.Intraday.equities.modal.OrderDetails;
import com.nikhil.Intraday.equities.modal.PlaceOrder;
import com.nikhil.Intraday.equities.service.Abstraction.PlaceOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins="http://localhost:4200")
@RestController
@RequestMapping("/api/v1")
public class BasicAuthController {

    @Autowired
    PlaceOrderService placeOrderService;

    @GetMapping(path = "/basicauth")
    public PlaceOrder getPlacedOrder() {
        return placeOrderService.getOrderDetails();
    }
}
