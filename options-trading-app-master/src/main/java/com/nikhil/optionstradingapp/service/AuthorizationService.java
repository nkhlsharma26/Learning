package com.nikhil.optionstradingapp.service;

import com.nikhil.optionstradingapp.model.UserDetails;

public interface AuthorizationService {
    void authorizeUser(UserDetails userDetails);
}
