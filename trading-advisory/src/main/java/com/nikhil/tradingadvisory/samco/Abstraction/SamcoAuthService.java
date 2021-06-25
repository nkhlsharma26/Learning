package com.nikhil.tradingadvisory.samco.Abstraction;

import com.nikhil.tradingadvisory.model.SamcoUserDetails;

public interface SamcoAuthService {
    String authorizeUser(SamcoUserDetails userDetails);

    String loginUser(String userName);
}
