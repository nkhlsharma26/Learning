package com.nikhil.tradingadvisory.samco.service;

import com.nikhil.tradingadvisory.emailService.EmailService;
import com.nikhil.tradingadvisory.samco.Abstraction.StockSelectionService;
import com.nikhil.tradingadvisory.samco.Abstraction.WebScraperService;
import com.nikhil.tradingadvisory.samco.model.GlobalUtilities;
import com.nikhil.tradingadvisory.samco.model.PlaceOrder;
import com.nikhil.tradingadvisory.samco.model.ReferenceData;
import com.nikhil.tradingadvisory.samco.model.TechnicalParameters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class StockSelectionServiceImpl implements StockSelectionService {

    @Autowired
    WebScraperService webScraperService;

    @Autowired
    ReferenceDataService referenceDataService;

    @Autowired
    DataPollingService dataPollingService;

    @Autowired
    EmailService emailService;

    @Value("${volumeThreshold}")
    private int volumeThreshold;

    private final static Logger LOGGER = LoggerFactory.getLogger(StockSelectionServiceImpl.class);

    public ReferenceData selectStockToBuy(String fromDate, String toDate) {
        Map<String, ReferenceData> referenceData = referenceDataService.getReferenceData().stream().collect(Collectors.toMap(ReferenceData::getSymbol, Function.identity()));
        Map<String, ReferenceData> polledData = dataPollingService.pollData(fromDate, toDate, "5", GlobalUtilities.initialTrend);
        ReferenceData winner = findWinner(referenceData, polledData);
        emailService.sendSuggestionMail(winner);
        return winner;
    }

    private ReferenceData findWinner(Map<String, ReferenceData> referenceData, Map<String, ReferenceData> polledData) {

        List<ReferenceData> winners;
        String trendToday = GlobalUtilities.initialTrend;
        if (trendToday.equalsIgnoreCase("BUY")) {
            LOGGER.info("Trend is upwards. We will place Buy orders only.");
            winners = new ArrayList<>(findBuySideWinner(referenceData, polledData).values());
        } else {
            LOGGER.info("Trend is downwards. We will place Sell orders only.");
            winners = new ArrayList<>(findSellSideWinner(referenceData, polledData).values());
        }

        ReferenceData winner = null;

        if (winners.size() > 0) {
            winner = winners.stream().max(Comparator.comparing(v -> Integer.parseInt(v.getVolume()))).get(); //get the winner with highest volume
            LOGGER.info("Stock selected to buy : " + winner.toString());
        } else {
            LOGGER.info("No winner found.");
        }
        return winner;
    }

    private Map<String, ReferenceData> findBuySideWinner(Map<String, ReferenceData> refData, Map<String, ReferenceData> polledData) {
        Map<String, ReferenceData> selectedScrips = new HashMap<>();
        polledData.forEach((k, v) -> {
            if(refData.containsKey(k) && Double.parseDouble(v.getHigh()) > Double.parseDouble(refData.get(k).getHigh())){
                if(Double.parseDouble(v.getHigh()) > Double.parseDouble(refData.get(k).getPreviousDayData().getHigh()))
                    selectedScrips.put(k, v);
            }
        });

        Map<String, ReferenceData> potentialWinners = new HashMap<>();
        List<TechnicalParameters> techDetails = webScraperService.getTechnicalParameters(new ArrayList<>(selectedScrips.keySet()));
        List<TechnicalParameters> scripsToReject = new ArrayList<>();

        if(techDetails.size() > 0) {
            for (TechnicalParameters tp : techDetails) {
                if ((Double.parseDouble(tp.getRsi()) > 60)
                        && (Double.parseDouble(tp.getSma20()) > Double.parseDouble(tp.getSma50()))
                        && (Double.parseDouble(tp.getSma20()) > Double.parseDouble(tp.getSma200())))
                {
                    potentialWinners.put(tp.getScripName(), selectedScrips.get(tp.getScripName()));
                } else {
                    scripsToReject.add(tp);
                }
            }
        }
        else{
            LOGGER.info("Unable to get Technical details. Rejecting all the stocks");
            polledData.forEach((k, v) -> LOGGER.info(k));
        }

        if(scripsToReject.size() > 0){
            scripsToReject.forEach( s -> LOGGER.info("We are rejecting following scrip based off of technical analysis:" +s.toString()));
        }
        return potentialWinners;
    }

    private Map<String, ReferenceData> findSellSideWinner(Map<String, ReferenceData> refData, Map<String, ReferenceData> polledData) {

        Map<String, ReferenceData> selectedScrips = new HashMap<>();
        polledData.forEach((k, v) -> {
            if(refData.containsKey(k) && Double.parseDouble(v.getLow()) < Double.parseDouble(refData.get(k).getLow())){
                if(Double.parseDouble(v.getLow()) < Double.parseDouble(refData.get(k).getPreviousDayData().getLow()))
                    selectedScrips.put(k, v);
            }
        });

        Map<String, ReferenceData> potentialWinners = new HashMap<>();
        List<TechnicalParameters> techDetails = webScraperService.getTechnicalParameters(new ArrayList<>(selectedScrips.keySet()));
        List<TechnicalParameters> scripsToReject = new ArrayList<>();

        if(techDetails.size() > 0) {
            for (TechnicalParameters tp : techDetails) {
                if ((Double.parseDouble(tp.getRsi()) < 60)
                        && (Double.parseDouble(tp.getSma20()) < Double.parseDouble(tp.getSma50()))
                        && (Double.parseDouble(tp.getSma20()) < Double.parseDouble(tp.getSma200())))
                {
                    potentialWinners.put(tp.getScripName(), selectedScrips.get(tp.getScripName()));
                } else {
                    scripsToReject.add(tp);
                }
            }
        }
        else{
            LOGGER.info("Unable to get Technical details. Rejecting all the stocks");
            polledData.forEach((k, v) -> LOGGER.info(k));
        }

        if(scripsToReject.size() > 0){
            scripsToReject.forEach( s -> LOGGER.info("We are rejecting following scrip based off of technical analysis:" +s.toString()));
        }

        return potentialWinners;
    }
}
