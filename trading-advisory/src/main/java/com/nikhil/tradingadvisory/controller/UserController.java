package com.nikhil.tradingadvisory.controller;

import com.nikhil.tradingadvisory.samco.Abstraction.SamcoAuthService;
import com.nikhil.tradingadvisory.service.Abstraction.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Generated;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    SamcoAuthService samcoAuthService;

    @GetMapping("/login/{userName}")
    public String userLogin(@PathVariable String userName){
            if(userName != null || !userName.isEmpty()){
               return samcoAuthService.loginUser(userName);
            }
            return null;
    }
}
