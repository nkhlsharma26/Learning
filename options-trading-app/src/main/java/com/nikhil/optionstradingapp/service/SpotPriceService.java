package com.nikhil.optionstradingapp.service;

import com.nikhil.optionstradingapp.model.SpotPrice;
import org.springframework.http.ResponseEntity;

public interface SpotPriceService {
    SpotPrice fetchSpotPrice();
}
