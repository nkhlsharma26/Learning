package com.nikhil.Intraday.equities.service;

import com.nikhil.Intraday.equities.modal.SymbolInfoModel;
import com.nikhil.Intraday.equities.service.Abstraction.DataGatheringService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DataPollingService {

    @Autowired
    DataGatheringService dataGatheringService;

    private final Logger logger = LogManager.getLogger(DataPollingService.class);

    private static final String INTERVAL = "5";


    public Map<String, SymbolInfoModel> pollData(String fromDate, String toDate, String symbol){
        logger.info("Fetching data from server at "+ LocalDateTime.now()+", for symbol: " +symbol);
        List<String[]> pollingData = dataGatheringService.getDataForStock(fromDate, toDate, INTERVAL, symbol, false);
        Map<String, SymbolInfoModel> polledData = writeDataToMap(pollingData);
        return polledData;
    }

    private Map<String, SymbolInfoModel> writeDataToMap(List<String[]> pollingDataList) {
        Map<String, SymbolInfoModel> dataMap = new HashMap<>();
        for(String[] pollingData : pollingDataList){
            SymbolInfoModel sp = new SymbolInfoModel(pollingData[0], pollingData[1],pollingData[2],pollingData[3], pollingData[4],"0.0");
            dataMap.put(pollingData[0],sp);
        }
        return dataMap;
    }
}
