package com.nikhil.tradingadvisory.samco.service;

import com.nikhil.tradingadvisory.samco.Abstraction.DataGatheringService;
import com.nikhil.tradingadvisory.samco.model.ReferenceData;
import com.nikhil.tradingadvisory.samco.repository.ReferenceDataRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class DataPollingService {

    private final static Logger LOGGER = LoggerFactory.getLogger(DataPollingService.class);

    @Autowired
    DataGatheringService dataGatheringService;

    @Autowired
    ReferenceDataRepository referenceDataRepository;

    public Map<String, ReferenceData> pollData(String fromDate, String toDate, String interval, String trend) {
        LOGGER.info("Fetching data from server at " + LocalDateTime.now());
        //Reference Data and polling data are same except percentage field in polling data will be 0 always
        List<String> symbols;
        if(trend.equalsIgnoreCase("POSITIVE")){
            symbols =  referenceDataRepository.getScripsWithPositivePercentageChange();
        }
        else{
            symbols = referenceDataRepository.getScripsWithNegativeOrZeroPercentageChange();
        }

        List<ReferenceData> pollingData = dataGatheringService.getDataForStock(fromDate, toDate, interval, symbols, false);
        Map<String, ReferenceData> polledData = new HashMap<>();
        for (ReferenceData refData : pollingData) {
            polledData.put(refData.getSymbol(), refData);
        }
        return polledData;
    }
}
