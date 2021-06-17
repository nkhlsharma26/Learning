package com.nikhil.Intraday.equities.service;

import com.nikhil.Intraday.equities.modal.*;
import com.nikhil.Intraday.equities.service.Abstraction.TradeBookService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.sql.Timestamp;

@Service
public class TradeBookServiceImpl implements TradeBookService {
    @Value("${stocknoteURI}")
    private String stockNoteURI;
    @Autowired
    SessionInfo sessionInfo;

    @Autowired
    RestTemplate restTemplate;

    private static final String END_POINT = "/trade/tradeBook";

    private final Logger logger = LogManager.getLogger(TradeBookServiceImpl.class);

    public ResponseEntity<TradeBookResponse> getOrderDetail() {
        String tradeBookUrl = stockNoteURI + END_POINT;
        HttpHeaders headers = new HttpHeaders();
        headers.set("x-session-token", sessionInfo.getSessionToken());
        HttpEntity<?> tradeBookEntity = new HttpEntity<>(headers);
        logger.info("Modify order at :" + new Timestamp(System.currentTimeMillis()));
        ResponseEntity<TradeBookResponse> response = restTemplate.exchange(tradeBookUrl, HttpMethod.GET, tradeBookEntity, TradeBookResponse.class);
        if (response.getStatusCodeValue() == 200) {
            logger.info("Trade book data retrieved successfully. Details: " + response.getBody().toString());
        } else {
            logger.error("Something went wrong. Details: " + response.getStatusCodeValue() + "message: " + response.getBody());
        }
        return response;
    }
}
