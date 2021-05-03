package com.nikhil.Intraday.equities.service;

import com.nikhil.Intraday.equities.modal.PlaceOrder;
import com.nikhil.Intraday.equities.modal.PlaceOrderResponse;
import com.nikhil.Intraday.equities.modal.GlobalUtilities;
import com.nikhil.Intraday.equities.modal.SessionInfo;
import com.nikhil.Intraday.equities.service.Abstraction.PlaceOrderService;
import com.nikhil.Intraday.equities.service.Abstraction.PrepareOrderService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.sql.Timestamp;

public class PlaceOrderServiceImpl implements PlaceOrderService {
    @Value("${placeOrderEndPoint}")
    private String placeOrderEndPoint;

    @Value("${modifyOrderEndPoint}")
    private String modifyOrderEndPoint;

    @Value("${stocknoteURI}")
    private String stockNoteURI;

    @Autowired
    SessionInfo sessionInfo;

    @Autowired
    RestTemplate restTemplate;

    private final Logger logger = LogManager.getLogger(PlaceOrderServiceImpl.class);

    public void buyStock(PlaceOrder placeOrder){
        String placeOrderUrl = stockNoteURI + placeOrderEndPoint;

        HttpHeaders headers = new HttpHeaders();
        headers.set("x-session-token", sessionInfo.getSessionToken());
        HttpEntity<PlaceOrder> placeOrderEntity = new HttpEntity<>(placeOrder, headers);
        logger.info("Going to buy stock at :"+ new Timestamp(System.currentTimeMillis()));
        ResponseEntity<PlaceOrderResponse> response = restTemplate.exchange(placeOrderUrl, HttpMethod.POST, placeOrderEntity, PlaceOrderResponse.class);
        if(response.getStatusCodeValue() == 200){
            logger.info("Order placed successfully. Details: "+response.getBody().toString());
            GlobalUtilities.findStockScheduler = false; //Stop finding stocks to buy
            GlobalUtilities.startOrderModification = true; //Start order modification service
            GlobalUtilities.boughtStock = placeOrder.getSymbolName();
        }
        else{
            logger.error("Something went wrong while placing order. Details: "+response.getStatusCodeValue() + "message: "+response.getBody().getStatusMessage());
        }
    }
}
