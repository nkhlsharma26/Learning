package com.nikhil.Intraday.equities.controller;

import com.nikhil.Intraday.equities.modal.OrderDetails;
import com.nikhil.Intraday.equities.modal.PlaceOrder;
import com.nikhil.Intraday.equities.modal.UserDetails;
import com.nikhil.Intraday.equities.service.Abstraction.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins="http://localhost:4200")
@RestController
@RequestMapping("/v1/user")
public class UserController {

    @Autowired
    UserService userService;

    @CrossOrigin(origins="http://localhost:4200")
    @PostMapping("/samcoLogin")
    public Boolean userLogin(@RequestBody UserDetails userDetails){

        return userService.loginUser(userDetails);
    }

    @CrossOrigin(origins="http://localhost:4200")
    @GetMapping("/getOrderDetails")
    public PlaceOrder getOrderDetails(){
        return userService.getOrderDetails();
    }

    @CrossOrigin(origins="http://localhost:4200")
    @PostMapping("/purchaseOrder/{userId}")
    public Boolean purchaseOrder(@RequestBody OrderDetails orderDetails, @PathVariable String userId){
        return userService.purchaseOrder(orderDetails, userId);
    }
}
