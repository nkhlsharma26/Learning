package com.nikhil.tradingadvisory.service.Abstraction;

import com.nikhil.tradingadvisory.model.SamcoUserDetails;
import com.nikhil.tradingadvisory.model.payload.LoginRequest;
import com.nikhil.tradingadvisory.model.payload.SignUpRequest;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface UserService {
    ResponseEntity UserSignup(SignUpRequest signUpRequest);
    ResponseEntity UserSignin(LoginRequest loginRequest);

    String confirmToken(String token, Map<String, String> samcoUserDetails);
}
