package com.nikhil.optionstradingapp.service;

import com.nikhil.optionstradingapp.model.AuthResponse;
import com.nikhil.optionstradingapp.model.SessionInfo;
import com.nikhil.optionstradingapp.model.UserDetails;
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

@Service
public class AuthorizationServiceImpl implements AuthorizationService {

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    UserDetails userDetails;

    @Autowired
    SessionInfo sessionInfo;

    @Value("${stocknoteURI}")
    private String stocknoteUrl;
    @Value("${userId}")
    private String userId;
    @Value("${password}")
    private String password;
    @Value("${yob}")
    private String yob;

    private static final String endpoint = "/login";
    Logger logger = LogManager.getLogger(AuthorizationServiceImpl.class);

    @EventListener
    public void onApplicationReadyEvent(ApplicationReadyEvent event) {
        userDetails.setUserId(userId);
        userDetails.setPassword(password);
        userDetails.setYob(yob);
        logger.info("parameters passed with login request, userId:"+userDetails.getUserId()+", password:"+userDetails.getPassword()+", yob:"+userDetails.getYob());
        authorizeUser(userDetails);
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
