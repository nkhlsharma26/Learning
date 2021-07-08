package com.nikhil.tradingadvisory.TechnicalIndicators.service;

import com.nikhil.tradingadvisory.samco.Abstraction.DataGatheringService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class RSIIndicator {
    @Autowired
    DataGatheringService dataGatheringService;

    public void collectIntradayData(){
        //read csv for symbol names
        //dataGatheringService.getDataForStock();
    }
}
