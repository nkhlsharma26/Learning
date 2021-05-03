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
        logger.info("Fetching data from server at "+ LocalDateTime.now());
        List<String[]> pollingData = dataGatheringService.getDataForStock(fromDate, toDate, INTERVAL, symbol);
        Map<String, SymbolInfoModel> polledData = writeDataToMap(pollingData);
        return polledData;
    }

    private Map<String, SymbolInfoModel> writeDataToMap(List<String[]> pollingDataList) {
        Map<String, SymbolInfoModel> dataMap = new HashMap<>();
        for(String[] pollingData : pollingDataList){
            String[] data = pollingData[0].split(",");
            SymbolInfoModel sp = new SymbolInfoModel(data[0], data[1],data[2],data[3], data[4]);
            dataMap.put(data[0],sp);
        }
        return dataMap;
    }
}
