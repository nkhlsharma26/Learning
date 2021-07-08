package com.nikhil.tradingadvisory.samco.service;

import com.nikhil.tradingadvisory.exception.InvalidTokenException;
import com.nikhil.tradingadvisory.samco.Abstraction.DataGatheringService;
import com.nikhil.tradingadvisory.samco.Abstraction.SamcoAuthService;
import com.nikhil.tradingadvisory.samco.model.*;
import com.nikhil.tradingadvisory.samco.repository.UserTokenRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URI;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

@Service
public class DataGatheringServiceImpl implements DataGatheringService {

    private final static Logger LOGGER = LoggerFactory.getLogger(DataGatheringServiceImpl.class);
    private static final String NSE = "NSE";

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    SamcoAuthService samcoAuthService;

    @Autowired
    UserTokenRepository userTokenRepository;

    @Value("${adminUser}")
    private String adminUser;

    @Value("${stocknoteURI}")
    private String stockNoteURI;

    private static final String COMMA_DELIMITER = ",";
    private static final String FILE_NAME_WITH_STOCK_NAME_LIST = "src/main/resources/data/stocksNameData.csv";

    public List<ReferenceData> getDataForStock(String fromDate, String toDate, String interval, List<String> symbolName, boolean referenceData) {
        List<String> symbols = new ArrayList<>();
        if(symbolName == null || symbolName.size() == 0) {
            symbols = getSymbolList();
        }
        else{
            symbols.addAll(symbolName);
        }
        List<ReferenceData> writableData = new ArrayList<>();

        String samcoToken;
        Optional<UserTokenModel> optional = userTokenRepository.findById(adminUser);
        if(!optional.isPresent()){
            samcoToken = samcoAuthService.loginUser(adminUser);
        }
        else {
            samcoToken = optional.get().getToken();
        }

        int i = 0;
        for (String symbol : symbols){
            {
                if(i>9)
                {
                    try {
                        Thread.sleep(1500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    i=0;
                }
                IntraDayCandleData intraDayCandleData = null;
                try{
                    intraDayCandleData = getCandleDataForScrip(symbol, samcoToken, fromDate, toDate, interval);
                }catch (InvalidTokenException ex){
                    samcoToken = samcoAuthService.loginUser(adminUser);
                    intraDayCandleData = getCandleDataForScrip(symbol, samcoToken, fromDate, toDate, interval);
                }

                Double percentageChange = 0.0;
                PreviousDayData previousDayData = null;
                if(referenceData){
                    try{
                        percentageChange = getPercentageChange(symbol, samcoToken);
                        previousDayData = getPreviousDayDataForSymbol(symbol, samcoToken);
                    }
                    catch (InvalidTokenException ex){
                        samcoToken = samcoAuthService.loginUser(adminUser);
                        percentageChange = getPercentageChange(symbol, samcoToken);
                        previousDayData = getPreviousDayDataForSymbol(symbol, samcoToken);
                    }

                }

                ReferenceData rData = ReferenceData.builder()
                        .high(intraDayCandleData.getHigh())
                        .low(intraDayCandleData.getLow())
                        .close(intraDayCandleData.getClose())
                        .volume(intraDayCandleData.getVolume())
                        .symbol(symbol)
                        .previousDayData(previousDayData)
                        .percentage(percentageChange)
                        .build();
                if(previousDayData!=null){
                    previousDayData.setReferenceData(rData);
                }
                writableData.add(rData);
                i++;
            }
        }
        return writableData;
    }

    private Double getPercentageChange(String symbol, String token) {
        final String END_POINT = "/quote/getQuote";
        String url = stockNoteURI + END_POINT;
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("symbolName", symbol.replace("M&M","M%26M"))
                .queryParam("exchange", NSE);
        URI uri = builder.build(true).toUri();
        HttpHeaders headers = new HttpHeaders();
        headers.set("x-session-token", token);
        HttpEntity<?> entity = new HttpEntity<>(headers);
        LOGGER.info("calling /quote/getQuote (to get % change) with Symbol: "+symbol+" and on thread :"+ Thread.currentThread().getName());
        ResponseEntity<QuoteResponse> response = null;
        try{
            response = restTemplate.exchange(uri, HttpMethod.GET, entity, QuoteResponse.class);
        }catch (Exception e){
            LOGGER.info(e.getMessage());
            if(e.getMessage().contains("Session Expired. Please login again.")){
                throw new InvalidTokenException(e.getMessage());
            }
        }

        return Double.parseDouble(response.getBody().getChangePercentage());
    }

    public IntraDayCandleData getCandleDataForScrip(String symbol, String samcoToken, String fromDate, String toDate, String interval) throws InvalidTokenException{
        final String END_POINT = "/intraday/candleData";
        String url = stockNoteURI + END_POINT;
        /* Some weired string manipulations done here because I needed to get around the string encoding of RestTemplate.exchange()
         * M&M is a special case as it contains "&" which is a reserved character and hence dealt with in a strange fashion.
         * TODO: Try uri replacement and check if it can be used.
         */
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("symbolName", symbol.replace("M&M","M%26M"))
                .queryParam("fromDate", fromDate.replace(" ","%20"))
                .queryParam("toDate", toDate.replace(" ","%20"))
                .queryParam("interval", interval);
        URI uri = builder.build(true).toUri();
        HttpHeaders headers = new HttpHeaders();
        headers.set("x-session-token", samcoToken);
        HttpEntity<?> entity = new HttpEntity<>(headers);
        LOGGER.info("Getting candle data.");
        LOGGER.info("calling URL with Symbol: "+symbol+" , URL:"+ url);
        ResponseEntity<IntraDayCandleDataList> response = null;
        try{
            response = restTemplate.exchange(uri, HttpMethod.GET, entity, IntraDayCandleDataList.class);
        }catch (Exception e){
            LOGGER.info(e.getMessage());
            if(e.getMessage().contains("Session Expired. Please login again.")){
                throw new InvalidTokenException(e.getMessage());
            }
        }

        response.getBody().setSymbol(symbol);
        return response.getBody().getIntradayCandleData().get(0);
    }

    private PreviousDayData getPreviousDayDataForSymbol(String symbol, String samcoToken) {
        final String ENDPOINT = "/history/candleData";
        String url = stockNoteURI+ENDPOINT;
        LocalDate fromDate = LocalDate.now().minusDays(1);
        PreviousDayData previousDayData = null;
        DayOfWeek day = LocalDate.now().getDayOfWeek();

        if(day == DayOfWeek.MONDAY){
            fromDate = LocalDate.now().minusDays(3);
        }

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("symbolName", symbol.replace("M&M","M%26M"))
                .queryParam("fromDate", fromDate);
        URI uri = builder.build(true).toUri();
        HttpHeaders headers = new HttpHeaders();
        headers.set("x-session-token", samcoToken);
        HttpEntity<?> entity = new HttpEntity<>(headers);
        LOGGER.info("Getting previous day data for symbol: "+symbol);
        LOGGER.info("calling URL with Symbol: "+symbol+" , URL:"+url);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ResponseEntity<PreviousDayDataResponse> response = null;
        try{
            response = restTemplate.exchange(uri, HttpMethod.GET, entity, PreviousDayDataResponse.class);
        }catch (Exception e){
            LOGGER.info(e.getMessage());
            if(e.getMessage().contains("Session Expired. Please login again.")){
                throw new InvalidTokenException(e.getMessage());
            }
        }

        if(response.getBody() != null){
            HistoricalCandleData data = response.getBody().getHistoricalCandleData().get(0); //this will always have one element as it it candle for whole day
            previousDayData =  PreviousDayData.builder()
                    .high(data.getHigh())
                    .close(data.close)
                    .low(data.low)
                    .build();
        }
        return previousDayData;
    }

    public List<String> getSymbolList() {
        List<String> records = new ArrayList<>();
        try (Scanner scanner = new Scanner(new File(FILE_NAME_WITH_STOCK_NAME_LIST))) {
            while (scanner.hasNextLine()) {
                records.add(getRecord(scanner.nextLine()));
            }
            LOGGER.info("Data read successfully from stocksNameData.csv");
        }
        catch (FileNotFoundException ex){
            LOGGER.error("File "+ FILE_NAME_WITH_STOCK_NAME_LIST +" was not found.");
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

