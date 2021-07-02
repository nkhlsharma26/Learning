package com.nikhil.tradingadvisory.samco.service;

import com.nikhil.tradingadvisory.samco.Abstraction.PlaceOrderService;
import com.nikhil.tradingadvisory.samco.Abstraction.SamcoAuthService;
import com.nikhil.tradingadvisory.samco.model.*;
import com.nikhil.tradingadvisory.samco.repository.PlaceOrderResponseRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.sql.Timestamp;
import java.util.Map;

@Service
public class PlaceOrderServiceImpl implements PlaceOrderService {

    @Autowired
    RestTemplate restTemplate;

    @Value("${stocknoteURI}")
    private String stockNoteURI;

    @Value("${placeOrderEndPoint}")
    private String placeOrderEndPoint;

    @Autowired
    PlaceOrderResponseRepository placeOrderResponseRepository;

    @Autowired
    SamcoAuthService samcoAuthService;

    private final static Logger LOGGER = LoggerFactory.getLogger(PlaceOrderServiceImpl.class);

    @Override
    public void buyStock(PlaceOrder placeOrder, String userId) {
        //prepare payload from methods below
        String placeOrderUrl = stockNoteURI + placeOrderEndPoint;

        HttpHeaders headers = new HttpHeaders();
        samcoAuthService.loginUser(userId);
        HttpEntity<PlaceOrder> placeOrderEntity = new HttpEntity<>(placeOrder, headers);
        LOGGER.info("Going to buy stock at :"+ new Timestamp(System.currentTimeMillis()));
        ResponseEntity<PlaceOrderResponse> response = restTemplate.exchange(placeOrderUrl, HttpMethod.POST, placeOrderEntity, PlaceOrderResponse.class);
        PlaceOrderResponse body = response.getBody();
        if(response.getStatusCodeValue() == 200){
            PlaceOrderResponseEntity orderResponseEntity = PlaceOrderResponseEntity.builder()
                    .userId(userId)
                    .serverTime(body.getServerTime())
                    .msgId(body.getMsgId())
                    .orderNumber(body.getOrderNumber())
                    .status(body.getStatus())
                    .statusMessage(body.getStatusMessage())
                    .exchangeOrderStatus(body.getExchangeOrderStatus())
                    .rejectionReason(body.getRejectionReason())
                    .orderDetails(body.getOrderDetails())
                    .build();
            placeOrderResponseRepository.save(orderResponseEntity);
            LOGGER.info("Order placed successfully. Details: "+ body.toString());
            GlobalUtilities.findStockScheduler = false; //Stop finding stocks to buy
            GlobalUtilities.startOrderModification = true; //Start order modification service
            GlobalUtilities.boughtStock = placeOrder.getSymbolName();
        }
        else{
            LOGGER.error("Something went wrong while placing order. Details: "+response.getStatusCodeValue() + "message: "+ body.getStatusMessage());
        }
    }

    private PlaceOrder preparePlaceOrderPayload(Map<String, ReferenceData> selectedStock, int stockQuantity) {
        PlaceOrder order  = new PlaceOrder();

        Map.Entry<String, ReferenceData> data = selectedStock.entrySet().iterator().next();
        double price  = Double.parseDouble(data.getValue().getClose());
        double triggerPrice = price - ((price*1)/100);

        order.setSymbolName(data.getKey());
        order.setExchange("NSE");
        order.setTransactionType(getOrderType());
        order.setOrderType("MKT");
        order.setQuantity(String.valueOf(stockQuantity));
        order.setPrice(String.valueOf(price));
        order.setPriceType("LTP");
        order.setOrderValidity("DAY");
        order.setAfterMarketOrderFlag("NO");
        order.setProductType("MIS");
        order.setTriggerPrice(String.valueOf(triggerPrice));
        return order;
    }

    private String getOrderType() {

        return "BUY";
    }
}
