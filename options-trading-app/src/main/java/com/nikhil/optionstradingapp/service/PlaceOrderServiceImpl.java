package com.nikhil.optionstradingapp.service;

import com.nikhil.optionstradingapp.exception.OrderServiceUnavailableException;
import com.nikhil.optionstradingapp.model.*;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class PlaceOrderServiceImpl implements PlaceOrderService {

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    SessionInfo sessionInfo;

    @Autowired
    SpotPriceService spotPriceService;

    @Autowired
    OrderStatusService orderStatusService;

    private Logger logger = LogManager.getLogger(PlaceOrderServiceImpl.class);
    private int retryOrderQuantity = 0;
    private Boolean placeOrderRetry = false;

    @Retryable(value = RuntimeException.class,maxAttempts = 5, backoff = @Backoff(value = 1000,multiplier = 1))
    public ResponseEntity<PlaceOrderResponse> placeOrderRequest(String placeOrderUrl, HttpEntity<PlaceOrder> placeOrderEntity) {
        ResponseEntity<PlaceOrderResponse> responseEntity = null;
        if(placeOrderRetry){
            // update the place order payload with remaining units.
            placeOrderEntity.getBody().setQuantity(String.valueOf(retryOrderQuantity));
        }

        //place order
        logger.info("Placing order with data: "+placeOrderEntity.getBody().toString());
        responseEntity = restTemplate.exchange(placeOrderUrl, HttpMethod.POST, placeOrderEntity, PlaceOrderResponse.class);

        logger.info("Returned response:" + responseEntity.getBody().toString());
        OrderBookDetail orderStatus = null;
        try {
            orderStatus = getOrderStatus(responseEntity);
        }
        catch (OrderServiceUnavailableException e){
            logger.error(e.getMessage(),e);
        }


        int orderedQuantity = Integer.parseInt(orderStatus.getQuantity());// number of required units
        int filledQuantity = Integer.parseInt(orderStatus.getFilledQuantity());// number of allotted units
        // if filled units is less then required units, the order is partially completed and we will retry the remaining units.
        if (filledQuantity < orderedQuantity){
            placeOrderRetry = true;
            retryOrderQuantity = orderedQuantity - filledQuantity;
            logger.info("Last order was partially successful. Retrying with "+ retryOrderQuantity +"units.");
            throw new RuntimeException("Order is not complete. Initiate retry.");
        }
        else{
            logger.info("Order is completely executed.");
            placeOrderRetry = false;
        }
        return responseEntity;
    }

    public OrderBookDetail getOrderStatus(ResponseEntity<PlaceOrderResponse> responseEntity) {
        return orderStatusService.getOrderStatus(responseEntity);
    }
}
