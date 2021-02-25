package com.nikhil.optionstradingapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class AdjustmentsServiceShort {
    @Autowired
    SpotPriceService spotPriceService;

    @Scheduled(cron = "*/20 * 9-16 * * MON-FRI")
    public void checkSpotPrice(){
        //System.out.println("getting spot price every 10th second. "+ spotPriceService.fetchSpotPrice().getTimeStamp());
        System.out.println(spotPriceService.fetchSpotPrice().getLastPrice());
    }
}
