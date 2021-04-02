package com.nikhil.optionstradingapp.service;

import com.nikhil.optionstradingapp.model.SessionInfo;
import com.nikhil.optionstradingapp.model.TradeBookResponse;
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

@Service
public class TradeBookService {
    @Autowired
    RestTemplate restTemplate;

    @Autowired
    SessionInfo sessionInfo;

    @Value("${stocknoteURI}")
    private String stockNoteURI;

    private Logger logger = LogManager.getLogger(TradeBookService.class);

    public ResponseEntity<TradeBookResponse> getTradeBook(){
        String currentPriceURI = stockNoteURI + "/trade/tradeBook";
        HttpHeaders headers = new HttpHeaders();
        headers.set("x-session-token", sessionInfo.getSessionToken());
        HttpEntity<?> entity = new HttpEntity<>(headers);
        ResponseEntity<TradeBookResponse> response = null;
        try{
            response = restTemplate.exchange(currentPriceURI, HttpMethod.GET, entity, TradeBookResponse.class);
        }catch (RuntimeException e){
            logger.error("No trades found for this user.", e);
        }

        return response;
    }
}
