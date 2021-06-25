package com.nikhil.tradingadvisory.controller;

import com.nikhil.tradingadvisory.model.SamcoUserDetails;
import com.nikhil.tradingadvisory.model.payload.LoginRequest;
import com.nikhil.tradingadvisory.model.payload.SignUpRequest;
import com.nikhil.tradingadvisory.service.Abstraction.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    UserService userService;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        return userService.UserSignin(loginRequest);
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {
        return userService.UserSignup(signUpRequest);
    }

    @PostMapping(value = "/confirm/{token}", consumes = MediaType.ALL_VALUE)
    public String confirm(@PathVariable String token, @RequestParam Map<String, String> samcoUserDetails) {
        return userService.confirmToken(token, samcoUserDetails);
    }
}
