package com.nikhil.Intraday.equities.service;

import com.nikhil.Intraday.equities.modal.IndexDetailResponse;
import com.nikhil.Intraday.equities.modal.ScripQuoteDetailResponse;
import com.nikhil.Intraday.equities.modal.SessionInfo;
import com.nikhil.Intraday.equities.service.Abstraction.QuoteService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.concurrent.CompletableFuture;

@Service
public class QuoteServiceImpl implements QuoteService {

    @Value("${stocknoteURI}")
    private String stockNoteURI;

    @Value("${indexName}")
    private String indexName;

    @Autowired
    SessionInfo sessionInfo;

    @Autowired
    RestTemplate restTemplate;

    private static final String END_POINT = "/quote/indexQuote";

    private final Logger logger = LogManager.getLogger(QuoteServiceImpl.class);

    public IndexDetailResponse getIndexQuote() {
        String indexQuoteUrl = stockNoteURI + END_POINT;

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(indexQuoteUrl)
                .queryParam("indexName", indexName.replace(" ","%20"));
        URI uri = builder.build(true).toUri();

        HttpHeaders headers = new HttpHeaders();
        headers.set("x-session-token", sessionInfo.getSessionToken());
        HttpEntity<?> indexQuoteEntity = new HttpEntity<>(headers);
        ResponseEntity<IndexDetailResponse> response = restTemplate.exchange(uri, HttpMethod.GET, indexQuoteEntity, IndexDetailResponse.class);
        if (response.getStatusCodeValue() == 200) {
            logger.info("Index data retrieved successfully. Details: " + response.getBody().toString());
        } else {
            logger.error("Something went wrong. Details: " + response.getStatusCodeValue() + "message: " + response.getBody());
        }
        return response.getBody();
    }

    @Async
    public CompletableFuture<ScripQuoteDetailResponse> getScripQuote(String symbolName){
        logger.info("Getting data for "+symbolName);

        String scripQuoteUrl = stockNoteURI + "/quote/getQuote";
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(scripQuoteUrl)
                .queryParam("exchange", "NSE")// this is default value, need not pass.
                .queryParam("symbolName",symbolName.replace("M&M","M%26M"));
        URI uri = builder.build(true).toUri();

        HttpHeaders headers = new HttpHeaders();
        headers.set("x-session-token", sessionInfo.getSessionToken());
        HttpEntity<?> scripQuoteEntity = new HttpEntity<>(headers);
        ResponseEntity<ScripQuoteDetailResponse> response = restTemplate.exchange(uri, HttpMethod.GET, scripQuoteEntity, ScripQuoteDetailResponse.class);
        if (response.getStatusCodeValue() == 200) {
            logger.info("Index data retrieved successfully. Details: " + response.getBody().toString());
        } else {
            logger.error("Something went wrong. Details: " + response.getStatusCodeValue() + "message: " + response.getBody());
        }
        return CompletableFuture.supplyAsync(() -> response.getBody());
    }
}
