package com.nikhil.Intraday.equities.service;

import com.nikhil.Intraday.equities.modal.SymbolInfoModel;
import com.nikhil.Intraday.equities.service.Abstraction.StockSelectionService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

public class StockSelectionServiceImpl implements StockSelectionService {

    private final Logger logger = LogManager.getLogger(StockSelectionServiceImpl.class);

    public Map<String, SymbolInfoModel> selectStockToBuy(Map<String, SymbolInfoModel> csvData, Map<String, SymbolInfoModel> polledData){
        return findWinner(csvData, polledData);
    }

    private Map<String, SymbolInfoModel> findWinner(Map<String, SymbolInfoModel> csvData, Map<String, SymbolInfoModel> polledData){

        Map<String, SymbolInfoModel> potentialWinners = new HashMap<>();
        Map<String, SymbolInfoModel> winner = new HashMap<>();

        for(Map.Entry<String,SymbolInfoModel> data : polledData.entrySet()){
            SymbolInfoModel csvVal = csvData.get(data.getKey());
            double csvHigh = Double.parseDouble(csvVal.getHigh());
            double polledHigh = Double.parseDouble(data.getValue().getClose());
            if(polledHigh > csvHigh){
                potentialWinners.put(data.getKey(), data.getValue());
            }
        }

        if(potentialWinners.size() > 1){
            int volume = 0;
            for(Map.Entry<String,SymbolInfoModel> data :potentialWinners.entrySet()){
                if(Integer.parseInt(data.getValue().getVolume())> volume){
                    winner.put(data.getKey(), data.getValue());
                }
            }
        }
        else {
            winner = potentialWinners;
        }

        return winner;
    }
}
