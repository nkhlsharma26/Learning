package com.nikhil.optionstradingapp.controller;

import com.nikhil.optionstradingapp.event.CallLongOrderEvent;
import com.nikhil.optionstradingapp.model.OrderEventData;
import com.nikhil.optionstradingapp.model.PlaceOrderRequest;
import com.nikhil.optionstradingapp.model.SquareOffRequestModel;
import com.nikhil.optionstradingapp.model.SquareOffResponseModel;
import com.nikhil.optionstradingapp.service.SquareOffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class Controller {

    @Autowired
    SquareOffService squareOffService;

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;
    @PostMapping("/place-order")
    public String placeOrder(@RequestBody PlaceOrderRequest placeOrderRequest ) {
        if(placeOrderRequest.getLotSize()<=0){
            return "Please enter positive value for lot size.";
        }
        OrderEventData eventData = new OrderEventData();
        eventData.setLotSize(placeOrderRequest.getLotSize());
        CallLongOrderEvent callLongOrderEvent = new CallLongOrderEvent(this,eventData);
        applicationEventPublisher.publishEvent(callLongOrderEvent);
        return "Order initiated successfully.";
    }

    @PostMapping("/square-off")
    public String squareOff(@RequestBody(required = false) SquareOffRequestModel squareOffRequestModel) throws InterruptedException {
        SquareOffResponseModel resp = squareOffService.squareOffPositions(squareOffRequestModel);
        return "Square off initiated successfully.";
    }
}
