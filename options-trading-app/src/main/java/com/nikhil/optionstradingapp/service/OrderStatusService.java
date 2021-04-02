package com.nikhil.optionstradingapp.service;

import com.nikhil.optionstradingapp.exception.OrderPendingException;
import com.nikhil.optionstradingapp.exception.OrderServiceUnavailableException;
import com.nikhil.optionstradingapp.model.*;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class OrderStatusService {
    @Autowired
    SessionInfo sessionInfo;

    @Autowired
    RestTemplate restTemplate;

    @Value("${stocknoteURI}")
    private String stockNoteURI;

    private Logger logger = LogManager.getLogger(OrderStatusService.class);


    @Retryable(value = OrderPendingException.class, maxAttempts = 4, backoff = @Backoff(value = 1000))
    public OrderBookDetail getOrderStatus(HttpEntity<PlaceOrderResponse> responseEntity){
        String orderNumber = responseEntity.getBody().getOrderNumber();
        String uri = stockNoteURI+"/order/orderBook";
        HttpHeaders headers = new HttpHeaders();
        headers.set("x-session-token", sessionInfo.getSessionToken());
        HttpEntity<PlaceOrder> entity = new HttpEntity<>(headers);
        System.out.println("trying...");
        ResponseEntity<OrderBookResponse> orderStatusResponse = restTemplate.exchange(uri, HttpMethod.GET,entity, OrderBookResponse.class);
        OrderBookDetail orderStatus = orderStatusResponse.getBody().getOrderBookDetails().stream().filter(orderBookDetail -> orderBookDetail.getOrderNumber().equals(orderNumber)).findAny().orElse(null);

        switch (orderStatus.getExchangeStatus().toUpperCase()){
            case "PENDING":
            case "OPEN":
                logger.info("Order is in pending state. will check status again...");
                throw new OrderPendingException("Order is in pending state.");
            case "EXECUTED":
            case "COMPLETE":
                logger.info("Order completed.");
                break;
            case "REJECTED":
                logger.info("Order rejected.");
                break;
            case "CANCELLED":
                logger.info("Order canclled.");
                break;
            default:
                logger.info("Service not responding");
                throw new OrderServiceUnavailableException("Order service unavailable.");
        }
        logger.info("Order status for order number: "+orderNumber+" is: "+orderStatus.getOrderStatus());
        return orderStatus;
    }
    @Recover
    public void recoverOrderStatusService(){
        logger.error("Max retry attempts exhausted for checking Order status.");
        throw new OrderServiceUnavailableException("Order service is not available.");
    }
}
