package com.nikhil.tradingadvisory.samco.service;

import com.nikhil.tradingadvisory.samco.Abstraction.WebScraperService;
import com.nikhil.tradingadvisory.samco.model.TechnicalParameters;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class WebScraperServiceImpl implements WebScraperService {

    @Autowired
    RestTemplate restTemplate;

    private static final String BASEURL = "http://localhost:8083";
    private static final String END_POINT = "/webScraper/getTechParameters";

    private final static Logger LOGGER = LoggerFactory.getLogger(WebScraperServiceImpl.class);


    public List<TechnicalParameters> getTechnicalParameters(List<String> scripNames){
        String parameterNames = StringUtils.join(scripNames,",");
        String reqUrl = BASEURL + END_POINT+"/"+parameterNames;
        ResponseEntity<List<TechnicalParameters>> response = null;
        try{
            HttpHeaders headers = new HttpHeaders();
            headers.set("x-session-token", sessionInfo.getSessionToken());
            HttpEntity<?> entity = new HttpEntity<>(headers);
            response = restTemplate.exchange(reqUrl, HttpMethod.GET, entity, new ParameterizedTypeReference<List<TechnicalParameters>>(){});
            if (response.getStatusCodeValue() == 200) {
                LOGGER.info("Index data retrieved successfully. Details: " + response.getBody().toString());
            } else {
                LOGGER.error("Something went wrong. Details: " + response.getStatusCodeValue() + "message: " + response.getBody());
            }
        }catch (Exception e){
            LOGGER.error("Error communicating with web scrapper service.");
        }

        return response != null ? response.getBody() : new ArrayList<>();
    }
}
