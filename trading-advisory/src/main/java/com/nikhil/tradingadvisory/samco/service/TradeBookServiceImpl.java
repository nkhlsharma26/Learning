package com.nikhil.tradingadvisory.samco.service;

import com.nikhil.tradingadvisory.samco.Abstraction.TradeBookService;
import com.nikhil.tradingadvisory.samco.model.GlobalUtilities;
import com.nikhil.tradingadvisory.samco.model.TradeBookResponse;
import com.nikhil.tradingadvisory.samco.repository.UserTokenRepository;
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

@Service
public class TradeBookServiceImpl implements TradeBookService {
    @Value("${stocknoteURI}")
    private String stockNoteURI;

    @Value("${adminUser}")
    private String adminUser;

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    UserTokenRepository userTokenRepository;


    private static final String END_POINT = "/trade/tradeBook";

    private final static Logger LOGGER = LoggerFactory.getLogger(TradeBookServiceImpl.class);

    public ResponseEntity<TradeBookResponse> getOrderDetail() {
        String tradeBookUrl = stockNoteURI + END_POINT;
        HttpHeaders headers = new HttpHeaders();
        String token = userTokenRepository.getById(adminUser).getToken();
        headers.set("x-session-token", token);
        HttpEntity<?> tradeBookEntity = new HttpEntity<>(headers);
        LOGGER.info("Modify order at :" + new Timestamp(System.currentTimeMillis()));
        ResponseEntity<TradeBookResponse> response = restTemplate.exchange(tradeBookUrl, HttpMethod.PUT, tradeBookEntity, TradeBookResponse.class);
        if (response.getStatusCodeValue() == 200) {
            LOGGER.info("Order placed successfully. Details: " + response.getBody().toString());
            GlobalUtilities.findStockScheduler = false;
        } else {
            LOGGER.error("Something went wrong. Details: " + response.getStatusCodeValue() + "message: " + response.getBody());
        }
        return response;
    }
}

