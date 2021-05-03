package com.nikhil.Intraday.equities.service;

import com.nikhil.Intraday.equities.modal.*;
import com.nikhil.Intraday.equities.service.Abstraction.SquareOffOrderService;
import com.nikhil.Intraday.equities.service.Abstraction.TradeBookService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;


@Service
public class SquareOffServiceImpl implements SquareOffOrderService {
    @Value("${stocknoteURI}")
    private String stockNoteURI;
    @Autowired
    SessionInfo sessionInfo;

    @Autowired
    RestTemplate restTemplate;
    
    @Autowired
    TradeBookService tradeBookService;

    private static final String END_POINT = "/position/squareOff";

    private final Logger logger = LogManager.getLogger(SquareOffServiceImpl.class);

    @Scheduled(cron = "57 2 * * 1-5")
    public void squareOffOrder(){
        String squareOffOrderUrl = stockNoteURI+END_POINT;
        ResponseEntity<TradeBookResponse> activeOrders = tradeBookService.getOrderDetail();
        List<TradeBookDetail> tradeBookDetails = activeOrders.getBody().getTradeBookDetails();
        if(tradeBookDetails.size() > 0){
            List<SquareOffPayload> squareOffPayloadList = new ArrayList<>();
            for(TradeBookDetail tradeBookEntry : tradeBookDetails){
                squareOffPayloadList.add(new SquareOffPayload(tradeBookEntry.getExchange(), tradeBookEntry.getTradingSymbol(), tradeBookEntry.getProductCode(), tradeBookEntry.getQuantity(), tradeBookEntry.getTransactionType()));
            }
            HttpHeaders headers = new HttpHeaders();
            headers.set("x-session-token", sessionInfo.getSessionToken());
            HttpEntity<List<SquareOffPayload>> squareOffOrderEntity = new HttpEntity<>(squareOffPayloadList, headers);
            logger.info("Going to square off order at :"+ new Timestamp(System.currentTimeMillis()));
            ResponseEntity<SquareOffResponseList> response = restTemplate.exchange(squareOffOrderUrl, HttpMethod.POST, squareOffOrderEntity, SquareOffResponseList.class);
            if(response.getStatusCodeValue() == 200){
                logger.info("Square off successful. Details: "+response.getBody().toString());
            }
            else{
                logger.error("Something went wrong. Details: "+response.getStatusCodeValue() + "message: "+response.getBody());
            }
        }
        else{
            logger.info("Found nothing to square off.");
        }
    }
}

