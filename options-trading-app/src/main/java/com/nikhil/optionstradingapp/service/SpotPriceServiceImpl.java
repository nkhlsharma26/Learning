package com.nikhil.optionstradingapp.service;

import com.nikhil.optionstradingapp.model.OptionChain;
import com.nikhil.optionstradingapp.model.OptionChainDetail;
import com.nikhil.optionstradingapp.model.SessionInfo;
import com.nikhil.optionstradingapp.model.SpotPrice;
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
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class SpotPriceServiceImpl implements SpotPriceService {
    @Autowired
    SpotPrice spotPrice;
    @Autowired
    RestTemplate restTemplate;
    @Value("${stocknoteURI}")
    private String stocknoteURI;
    @Value("${exchange}")
    private String exchange;
    @Value("${symbolName}")
    private String symbolName;
    private static final String endPoint = "/option/optionChain";
    @Autowired
    SessionInfo sessionInfo;

    Logger logger = LogManager.getLogger(SpotPriceServiceImpl.class);
    @Override
    public SpotPrice fetchSpotPrice() {
        String currentPriceURI = stocknoteURI + endPoint;
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(currentPriceURI)
                .queryParam("exchange",exchange)
                .queryParam("searchSymbolName",symbolName);
        HttpHeaders headers = new HttpHeaders();
        headers.set("x-session-token", sessionInfo.getSessionToken());
        HttpEntity<?> entity = new HttpEntity<>(headers);
        logger.info("Getting spot price with values:\nexchange:"+exchange+" ,symbol name:"+symbolName+".");
        ResponseEntity<OptionChain> response = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, entity, OptionChain.class);
        spotPrice.setLastPrice(response.getBody().getOptionChainDetails().stream().findFirst().map(OptionChainDetail::getSpotPrice).get());
        spotPrice.setTimeStamp(response.getBody().getServerTime());
        logger.info("Returned: spot price :"+spotPrice.getLastPrice()+" at :"+spotPrice.getTimeStamp());
        return spotPrice;
    }
}
