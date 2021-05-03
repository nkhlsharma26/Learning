package com.nikhil.Intraday.equities.service;

import com.nikhil.Intraday.equities.modal.IntraDayCandleData;
import com.nikhil.Intraday.equities.modal.IntraDayCandleDataList;
import com.nikhil.Intraday.equities.modal.SessionInfo;
import com.nikhil.Intraday.equities.service.Abstraction.DataGatheringService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.concurrent.*;

@Service
public class DataGatheringServiceImpl implements DataGatheringService {

    @Autowired
    ParameterService parameterService;

    @Autowired
    SessionInfo sessionInfo;

    @Autowired
    RestTemplate restTemplate;

    private final Logger logger = LogManager.getLogger(DataGatheringServiceImpl.class);
    private static final String COMMA_DELIMITER = ",";
    private static final String FILE_NAME_WITH_STOCK_NAME_LIST = "src/main/resources/data/stocksNameData.csv";
    private static final int THREAD_POOL_SIZE = 5;

    List<String> symbols = null;
    public List<String[]> getDataForStock(String fromDate, String toDate, String interval, String symbolName) {

        if(symbolName.isEmpty() || symbolName == null) {
            symbols = getSymbolList();
        }
        else{
            symbols.add(symbolName);
        }
        List<Future<ResponseEntity<IntraDayCandleDataList>>> failedSymbols = new ArrayList<>();
        List<String[]> writableData = new ArrayList<>();

        parameterService.setParameters(symbols, fromDate, toDate, interval);
        ExecutorService executorService = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
        List<Future<ResponseEntity<IntraDayCandleDataList>>> list = new ArrayList<>();
        int i = 0;
        for (String symbol : symbols){
            if(i >= 5){
                i = 0;
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            GatherData gatherData = new GatherData(symbol, sessionInfo, parameterService, restTemplate);
            Future<ResponseEntity<IntraDayCandleDataList>> future = executorService.submit(gatherData);
            list.add(future);
            i++;
        }

        for (Future<ResponseEntity<IntraDayCandleDataList>> future : list){
            ResponseEntity<IntraDayCandleDataList> x = null;
            try {
                x = future.get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
            if(x.getStatusCodeValue() != 200){
                failedSymbols.add(future);
                continue;
            }
            List<IntraDayCandleData> candleDataList = x.getBody().getIntradayCandleData();
            double maxHigh = getMaxHigh(candleDataList);
            double minLow = getMinLow(candleDataList);
            int maxVolume = getMaxVolume(candleDataList);
            double close = getMaxClose(candleDataList);
            writableData.add(new String[]{x.getBody().getSymbol(), String.valueOf(maxHigh), String.valueOf(minLow), String.valueOf(close), String.valueOf(maxVolume)});
        }
        for(Future<ResponseEntity<IntraDayCandleDataList>> future : failedSymbols){
            try {
                logger.error("Failed to get data for: "+future.get().getBody().getSymbol()+", reason: "+future.get().getBody().getStatusMessage());
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }
        return writableData;
    }

    private int getMaxVolume(List<IntraDayCandleData> candleDataList) {
        int max = 0;
        for(IntraDayCandleData data : candleDataList){
            int val = Integer.parseInt(data.getVolume());
            if(val > max){
                max = val;
            }
        }
        return max;
    }

    private double getMaxClose(List<IntraDayCandleData> candleDataList) {
        double max = 0.0;
        for(IntraDayCandleData data : candleDataList){
            double val = Double.parseDouble(data.getClose());
            if(val > max){
                max = val;
            }
        }
        return max;
    }

    private double getMinLow(List<IntraDayCandleData> candleDataList) {
        double min = Double.MAX_VALUE;
        for(IntraDayCandleData data : candleDataList){
            double val = Double.parseDouble(data.getLow());
            if(val < min){
                min = val;
            }
        }
        return min;
    }

    private double getMaxHigh(List<IntraDayCandleData> candleDataList) {
        double max = 0.0;
        for(IntraDayCandleData data : candleDataList){
            double val = Double.parseDouble(data.getHigh());
            if(val > max){
                max = val;
            }
        }
        return max;
    }

    public List<String> getSymbolList() {
        List<String> records = new ArrayList<>();
        try (Scanner scanner = new Scanner(new File(FILE_NAME_WITH_STOCK_NAME_LIST))) {
            while (scanner.hasNextLine()) {
                records.add(getRecord(scanner.nextLine()));
            }
            logger.info("Data read successfully from stocksNameData.csv");
        }
        catch (FileNotFoundException ex){
            logger.error("File "+ FILE_NAME_WITH_STOCK_NAME_LIST +" was not found.");
        }
        return records;
    }

    private String getRecord(String line) {
        String value = null;
        try (Scanner rowScanner = new Scanner(line)) {
            rowScanner.useDelimiter(COMMA_DELIMITER);
            while (rowScanner.hasNext()) {
                value = rowScanner.next();
            }
        }
        return value;
    }
}

