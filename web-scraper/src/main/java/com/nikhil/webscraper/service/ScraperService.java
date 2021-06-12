package com.nikhil.webscraper.service;

import com.nikhil.webscraper.modal.TechAnalysisResponse;
import com.nikhil.webscraper.modal.TechnicalDetailsRequest;
import com.nikhil.webscraper.modal.TechnicalParameters;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.sql.Timestamp;
import java.util.*;

@Service
public class ScraperService {

    @Value("${streakUrl}")
    private String streakURL;

    @Value("${brokerId}")
    private String brokerId;

    @Autowired
    RestTemplate restTemplate;

    private final Logger logger = LogManager.getLogger(ScraperService.class);

    public ResponseEntity<List<TechnicalParameters>> scrape(List<String> scripNames) {
        TechnicalDetailsRequest detailsRequest = new TechnicalDetailsRequest();
        detailsRequest.setTime_frame("min");
        detailsRequest.setUser_broker_id(brokerId);
        detailsRequest.setStocks(scripNames);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        headers.add("authority", "mo.streak.tech");
        headers.add("origin", "https://streakv3.zerodha.com");

        HttpEntity<TechnicalDetailsRequest> placeOrderEntity = new HttpEntity<>(detailsRequest, headers);
        logger.info("Going to buy stock at :" + new Timestamp(System.currentTimeMillis()));
        ResponseEntity<Object> response = restTemplate.exchange(streakURL, HttpMethod.POST, placeOrderEntity, Object.class);
        List<TechnicalParameters> tpList = new ArrayList<>();
        if (response != null && response.getStatusCodeValue() == 200){

            Map<String, TechAnalysisResponse> techAnalysisResponseDataSet;
            techAnalysisResponseDataSet = (Map<String, TechAnalysisResponse>) response.getBody();
            Map<String, TechAnalysisResponse> mapOfData = (Map<String, TechAnalysisResponse>) techAnalysisResponseDataSet.get("data");

            for (Map.Entry<String, TechAnalysisResponse> entry : mapOfData.entrySet()) {
                TechnicalParameters tp = new TechnicalParameters();
                tp.setScripName(entry.getKey());
                Object tar = entry.getValue();
                tp.setSma50(((LinkedHashMap) tar).get("sma50").toString());
                tp.setSma200(((LinkedHashMap) tar).get("sma200").toString());
                tp.setLastClose(((LinkedHashMap) tar).get("close").toString());
                tp.setRsi(((LinkedHashMap) tar).get("rsi").toString());
                tpList.add(tp);
            }
        }
        logger.info("Response: "+ Arrays.toString(tpList.toArray()));
        return tpList.size()>0 ? new ResponseEntity<>(tpList, HttpStatus.OK)
                : new ResponseEntity<>(tpList, HttpStatus.NOT_FOUND);
    }
}
