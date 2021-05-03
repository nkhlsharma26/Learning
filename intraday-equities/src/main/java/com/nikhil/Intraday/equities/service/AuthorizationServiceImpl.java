package com.nikhil.Intraday.equities.service;

import com.nikhil.Intraday.equities.modal.AuthResponse;
import com.nikhil.Intraday.equities.modal.SessionInfo;
import com.nikhil.Intraday.equities.modal.UserDetails;
import com.nikhil.Intraday.equities.service.Abstraction.AuthorizationService;
import com.nikhil.Intraday.equities.service.Abstraction.DataGatheringService;
import com.nikhil.Intraday.equities.service.Abstraction.DataWriterService;
import com.nikhil.Intraday.equities.service.Abstraction.PrepareOrderService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class AuthorizationServiceImpl implements AuthorizationService {

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    UserDetails userDetails;

    @Autowired
    SessionInfo sessionInfo;

    @Autowired
    DataWriterService dataWriterService;

    @Autowired
    PrepareOrderService prepareOrderService;

    @Autowired
    DataGatheringService dataGatheringService;

    @Value("${stocknoteURI}")
    private String stocknoteUrl;
    @Value("${userId}")
    private String userId;
    @Value("${password}")
    private String password;
    @Value("${yob}")
    private String yob;

    private static final String endpoint = "/login";
    Logger logger = LogManager.getLogger(AuthorizationService.class);

    @EventListener
    public void onApplicationReadyEvent(ApplicationReadyEvent event) {
        userDetails.setUserId(userId);
        userDetails.setPassword(password);
        userDetails.setYob(yob);
        logger.info("parameters passed with login request, userId:"+userDetails.getUserId()+", password:"+userDetails.getPassword()+", yob:"+userDetails.getYob());
        authorizeUser(userDetails);
        List<String[]> data = dataGatheringService.getDataForStock("2020-03-11 09:15:00", "2020-03-11 09:30:00", "15", null);
        dataWriterService.writeData(data);
    }

    @Override
    public void authorizeUser(UserDetails userDetails) {
        String loginUrl = stocknoteUrl + endpoint;
        HttpEntity<UserDetails> userEntity = new HttpEntity<>(userDetails);
        try{
            ResponseEntity<AuthResponse> response = restTemplate.exchange(loginUrl, HttpMethod.POST, userEntity,AuthResponse.class);
            logger.info("Login Successful, "+response.getBody().toString());
            sessionInfo.setSessionToken(response.getBody().getSessionToken());
        }
        catch (HttpClientErrorException ex)
        {
            logger.error("Login Failed with message:"+ex.getMessage(), ex);
        }
    }
}