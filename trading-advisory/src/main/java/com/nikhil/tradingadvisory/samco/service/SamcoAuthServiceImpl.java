package com.nikhil.tradingadvisory.samco.service;

import com.nikhil.tradingadvisory.emailService.EmailService;
import com.nikhil.tradingadvisory.model.SamcoUserDetails;
import com.nikhil.tradingadvisory.samco.model.UserTokenModel;
import com.nikhil.tradingadvisory.samco.repository.SamcoUserRepository;
import com.nikhil.tradingadvisory.samco.Abstraction.SamcoAuthService;
import com.nikhil.tradingadvisory.samco.model.AuthResponse;
import com.nikhil.tradingadvisory.samco.repository.UserTokenRepository;
import com.nikhil.tradingadvisory.util.EncryptorDecryptor;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.security.PrivateKey;
import java.util.Optional;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class SamcoAuthServiceImpl implements SamcoAuthService {
    private static final String endpoint = "/login";

    private final static Logger LOGGER = LoggerFactory.getLogger(SamcoAuthServiceImpl.class);

    @Value("${stocknoteURI}")
    private String stocknoteUrl;

    @Value("${adminUser}")
    private String adminUser;

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private SamcoUserRepository samcoUserRepository;
    @Autowired
    private UserTokenRepository userTokenRepository;
    @Autowired
    private EncryptorDecryptor encryptorDecryptor;

    @EventListener
    public void onApplicationReadyEvent(ApplicationReadyEvent event) {

       // loginUser(adminUser);
    }

    @Override
    public String authorizeUser(SamcoUserDetails userDetails) {
        String samcoToken = null;
        String loginUrl = stocknoteUrl + endpoint;
        HttpEntity<SamcoUserDetails> userEntity = new HttpEntity<>(userDetails);
        try{
            ResponseEntity<AuthResponse> response = restTemplate.exchange(loginUrl, HttpMethod.POST, userEntity,AuthResponse.class);
            LOGGER.info("Login Successful, "+response.getBody().toString());
            samcoToken = response.getBody().getSessionToken();
        }
        catch (HttpServerErrorException | HttpClientErrorException ex)
        {
            LOGGER.error("Login Failed with message:"+ex.getMessage(), ex);
        }
        return samcoToken;
    }

    @Override
    public String loginUser(String userId) {
        Optional<SamcoUserDetails> optional = samcoUserRepository.findByUserId(userId);
        String samcoToken = "";
        if(!optional.isPresent()){
            throw new IllegalArgumentException("User not found!");
        }
        else{
            SamcoUserDetails userDetails = optional.get();
            PrivateKey privateKey = null;
            try {
                /*privateKey = encryptorDecryptor.getPrivate("src/main/resources/KeyPair/privateKey");
                String password = encryptorDecryptor.decryptText(userDetails.getPassword(), privateKey);
                userDetails.setPassword(password);*/
                samcoToken = authorizeUser(userDetails);
            } catch (Exception e) {
                e.printStackTrace();
            }

            UserTokenModel tokenModel = new UserTokenModel(userId, samcoToken);
            userTokenRepository.save(tokenModel);
        }
        return samcoToken;
    }
}
