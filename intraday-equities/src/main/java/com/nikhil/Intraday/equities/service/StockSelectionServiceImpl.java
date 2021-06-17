package com.nikhil.Intraday.equities.service;

import com.nikhil.Intraday.equities.modal.GlobalUtilities;
import com.nikhil.Intraday.equities.modal.SymbolInfoModel;
import com.nikhil.Intraday.equities.modal.TechnicalParameters;
import com.nikhil.Intraday.equities.service.Abstraction.QuoteService;
import com.nikhil.Intraday.equities.service.Abstraction.StockSelectionService;
import com.nikhil.Intraday.equities.service.Abstraction.TrendFinderService;
import com.nikhil.Intraday.equities.service.Abstraction.WebScraperService;
import javafx.util.Pair;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class StockSelectionServiceImpl implements StockSelectionService {

    @Autowired
    QuoteService quoteService;

    @Autowired
    TrendFinderService trendFinderService;

    @Autowired
    WebScraperService webScraperService;

    @Value("${volumeThreshold}")
    private int volumeThreshold;

    private final Logger logger = LogManager.getLogger(StockSelectionServiceImpl.class);

    public SymbolInfoModel selectStockToBuy(Map<String, SymbolInfoModel> csvData, Map<String, SymbolInfoModel> polledData) {
        return findWinner(csvData, polledData);
    }

    private SymbolInfoModel findWinner(Map<String, SymbolInfoModel> csvData, Map<String, SymbolInfoModel> polledData) {

        List<SymbolInfoModel> winners;
        String trendToday = GlobalUtilities.initialTrend;
        if (trendToday.equalsIgnoreCase("BUY")) {
            logger.info("Trend is upwards. We will place Buy orders only.");
            winners = new ArrayList<SymbolInfoModel>(findUpSideWinner(csvData, polledData).values());
            //winners = getStockWithHighestChangePercent(upWinners);
        } else {
            logger.info("Trend is downwards. We will place Sell orders only.");
            winners = new ArrayList<SymbolInfoModel>(findDownSideWinner(csvData, polledData).values());
            //winners = getStockWithHighestChangePercent(downWinners);
        }

        SymbolInfoModel winner = null;

        if (winners.size() > 0) {
            winner = winners.stream().max(Comparator.comparing(v -> Integer.parseInt(v.getVolume()))).get(); //get the winner with highest volume
            logger.info("Stock selected to buy : " + winner.toString());
        } else {
            logger.info("No winner found.");
        }
        return winner;
    }

    private Map<String, SymbolInfoModel> findUpSideWinner(Map<String, SymbolInfoModel> csvData, Map<String, SymbolInfoModel> polledData) {
        //double delta = 0;
        Map<String, SymbolInfoModel> potentialWinners = new HashMap<>();
        for (Map.Entry<String, SymbolInfoModel> data : polledData.entrySet()) {
            Pair<String, SymbolInfoModel> positivelyChangedStocks = getStockWithPositivelyOrNegativelyChangedStock(csvData, data, "Positive");
            if(positivelyChangedStocks != null){
                potentialWinners.put(positivelyChangedStocks.getKey(), positivelyChangedStocks.getValue());
            }
            /*SymbolInfoModel csvVal = csvData.get(data.getKey());
            double csvHigh = Double.parseDouble(csvVal.getHigh());
            double polledHigh = Double.parseDouble(data.getValue().getHigh());
            double currentDelta = polledHigh - csvHigh;

            if (currentDelta > 0 && currentDelta > delta) {
                delta = currentDelta;
                potentialWinners.clear();
                potentialWinners.put(data.getKey(), data.getValue());
            }*/
        }
        List<String> potentialWinnerNames = potentialWinners.keySet().stream().collect(Collectors.toList());
        List<TechnicalParameters> techDetails = webScraperService.getTechnicalParameters(potentialWinnerNames);
        List<TechnicalParameters> scripsToReject = new ArrayList<>();
        if(techDetails.size() > 0) {
            for (TechnicalParameters tp : techDetails) {
                if ((Double.parseDouble(tp.getRsi()) > 60) && (Double.parseDouble(tp.getSma50()) > Double.parseDouble(tp.getSma200())) && (Double.parseDouble(tp.getSma50()) > Double.parseDouble(polledData.get(tp.getScripName()).getClose())) &&
                        (Double.parseDouble(tp.getSma200()) > Double.parseDouble(polledData.get(tp.getScripName()).getClose()))) {
                    // we do nothing in this case
                } else {
                    scripsToReject.add(tp);
                }
            }
        }
            if(scripsToReject != null){
                for (TechnicalParameters param: scripsToReject) {
                    potentialWinners.remove(param.getScripName());//Now we have the list of scrips we want to remove from potential winners
                }
        }
        return potentialWinners;
    }

    private Map<String, SymbolInfoModel> findDownSideWinner(Map<String, SymbolInfoModel> csvData, Map<String, SymbolInfoModel> polledData) {
        Map<String, SymbolInfoModel> potentialWinners = new HashMap<>();
        //double delta = 0;
        for (Map.Entry<String, SymbolInfoModel> data : polledData.entrySet()) {
            Pair<String, SymbolInfoModel> negativelyChangedStocks = getStockWithPositivelyOrNegativelyChangedStock(csvData, data, "Negative");
            if(negativelyChangedStocks != null){
                potentialWinners.put(negativelyChangedStocks.getKey(), negativelyChangedStocks.getValue());
            }
            /*SymbolInfoModel csvVal = csvData.get(data.getKey());
            double csvLow = Double.parseDouble(csvVal.getLow());
            double polledLow = Double.parseDouble(data.getValue().getLow());
            double currentDelta = polledLow - csvLow;

            if (currentDelta > 0 && currentDelta > delta) {
                delta = currentDelta;
                potentialWinners.clear();
                potentialWinners.put(data.getKey(), data.getValue());
            }*/
        }
        List<String> potentialWinnerNames = potentialWinners.keySet().stream().collect(Collectors.toList());
        List<TechnicalParameters> techDetails = webScraperService.getTechnicalParameters(potentialWinnerNames);
        if(techDetails.size() > 0){
            List<TechnicalParameters> picks = techDetails.stream().filter(tp -> Double.parseDouble(tp.getRsi()) < 60 &&
                    Double.parseDouble(tp.getSma50()) > Double.parseDouble(tp.getSma200()) &&
                    Double.parseDouble(tp.getSma50()) > Double.parseDouble(polledData.get(tp.getScripName()).getClose()) &&
                    Double.parseDouble(tp.getSma200()) > Double.parseDouble(polledData.get(tp.getScripName()).getClose())).collect(Collectors.toList());
            potentialWinnerNames.removeAll(picks);//Now we have the list of scrips we want to remove from potential winners
        }
        return potentialWinners;
    }

    private Pair<String, SymbolInfoModel> getStockWithPositivelyOrNegativelyChangedStock(Map<String, SymbolInfoModel> csvData, Map.Entry<String, SymbolInfoModel> data, String direction) {

        SymbolInfoModel sInfo = csvData.get(data.getKey());
        String percentage = sInfo.getPercentage().isEmpty()? "0.0":sInfo.getPercentage();

        if(direction.equalsIgnoreCase("Positive")){
            if(Double.parseDouble(percentage)>=0){
                return new Pair<>(sInfo.getSymbol(), sInfo);
            }
            return null;
        }
        else{
            if(Float.parseFloat(percentage)<=0){
                return new Pair<>(sInfo.getSymbol(), sInfo);
            }
            return null;
        }

    }
}
