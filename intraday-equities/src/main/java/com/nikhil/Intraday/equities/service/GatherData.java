package com.nikhil.Intraday.equities.service;

import com.nikhil.Intraday.equities.modal.IntraDayCandleDataList;
import com.nikhil.Intraday.equities.modal.SessionInfo;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.concurrent.Callable;

public class GatherData implements Callable<ResponseEntity<IntraDayCandleDataList>> {

    RestTemplate restTemplate;

    @Value("${stocknoteURI}")
    private String stockNoteURI = "https://api.stocknote.com";

    private final Logger logger = LogManager.getLogger(GatherData.class);
    private static final String END_POINT = "/intraday/candleData";

    SessionInfo sessionInfo;

    ParameterService parameterService;

    private String symbol;

    GatherData(String symbol, SessionInfo sessionInfo, ParameterService parameterService, RestTemplate restTemplate){
        this.symbol = symbol;
        this.sessionInfo = sessionInfo;
        this.parameterService = parameterService;
        this.restTemplate = restTemplate;
    }

    @Override
    public ResponseEntity<IntraDayCandleDataList> call() throws Exception {
        String url = stockNoteURI + END_POINT;
        /* Some weired string manipulations done here because I needed to get around the string encoding of RestTemplate.exchange()
         * M&M is a special case as it contains "&" which is a reserved character and hence dealt with in a strange fashion.
         * TODO: Try uri replacement and check if it can be used in place of this unusual way.
         */
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("symbolName", symbol.replace("M&M","M%26M"))
                .queryParam("fromDate",parameterService.getParameters().getFromDate().replace(" ","%20"))
                .queryParam("toDate",parameterService.getParameters().getToDate().replace(" ","%20"))
                .queryParam("interval",parameterService.getParameters().getInterval());
        URI uri = builder.build(true).toUri();
        HttpHeaders headers = new HttpHeaders();
        headers.set("x-session-token", sessionInfo.getSessionToken());
        HttpEntity<?> entity = new HttpEntity<>(headers);
        logger.info("calling URL with Symbol: "+symbol+" and on thread :"+ Thread.currentThread().getName());
        //System.out.println(symbol);
        ResponseEntity<IntraDayCandleDataList> response = restTemplate.exchange(uri, HttpMethod.GET, entity, IntraDayCandleDataList.class);
        response.getBody().setSymbol(symbol);
        return response;
    }
}
