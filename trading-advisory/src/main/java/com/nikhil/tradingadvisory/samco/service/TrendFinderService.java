package com.nikhil.tradingadvisory.samco.service;

import com.nikhil.tradingadvisory.samco.Abstraction.SamcoAuthService;
import com.nikhil.tradingadvisory.samco.model.GlobalUtilities;
import com.nikhil.tradingadvisory.samco.model.IndexDetailResponse;
import com.nikhil.tradingadvisory.samco.model.UserTokenModel;
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
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

@Service
public class TrendFinderService {

    @Value("${stocknoteURI}")
    private String stockNoteURI;

    @Value("${indexName}")
    private String indexName;

    @Value("${adminUser}")
    private String adminUser;

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    SamcoAuthService samcoAuthService;

    @Autowired
    UserTokenRepository userTokenRepository;

    private static final String END_POINT = "/quote/indexQuote";
    private final static Logger LOGGER = LoggerFactory.getLogger(TrendFinderService.class);

    public void getTrendForToday(){

        IndexDetailResponse indexDetailResponse = getIndexQuote();
        double percentage = indexDetailResponse.getChangePercentage();
        GlobalUtilities.initialTrend = percentage > 0 ? "BUY" : "SELL";
        LOGGER.info("Today's trend in "+GlobalUtilities.initialTrend +".");
    }

    private IndexDetailResponse getIndexQuote() {
        String indexQuoteUrl = stockNoteURI + END_POINT;

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(indexQuoteUrl)
                .queryParam("indexName", indexName.replace(" ","%20"));
        URI uri = builder.build(true).toUri();

        String samcoToken;
        Optional<UserTokenModel> optional = userTokenRepository.findById(adminUser);
        if(!optional.isPresent()){
            samcoToken = samcoAuthService.loginUser(adminUser);
        }
        else {
            samcoToken = optional.get().getToken();
        }

        HttpHeaders headers = new HttpHeaders();
        headers.set("x-session-token", samcoToken);
        HttpEntity<?> indexQuoteEntity = new HttpEntity<>(headers);
        ResponseEntity<IndexDetailResponse> response = restTemplate.exchange(uri, HttpMethod.GET, indexQuoteEntity, IndexDetailResponse.class);
        if (response.getStatusCodeValue() == 200) {
            LOGGER.info("Index data retrieved successfully. Details: " + response.getBody().toString());
        } else {
            LOGGER.error("Something went wrong. Details: " + response.getStatusCodeValue() + "message: " + response.getBody());
        }
        return response.getBody();
    }
}
