package com.nikhil.Intraday.equities.service;

import com.nikhil.Intraday.equities.modal.GlobalUtilities;
import com.nikhil.Intraday.equities.service.Abstraction.DataGatheringService;
import com.nikhil.Intraday.equities.service.Abstraction.DataWriterService;
import com.nikhil.Intraday.equities.service.Abstraction.ModifyOrderService;
import com.nikhil.Intraday.equities.service.Abstraction.StockBuyService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

@Service
public class ApplicationStaterService {

    private final Logger logger = LogManager.getLogger(ApplicationStaterService.class);

    @Autowired
    DataGatheringService dataGatheringService;

    @Autowired
    DataWriterService dataWriterService;

    @Autowired
    StockBuyService stockBuyService;

    @Autowired
    ModifyOrderService modifyOrderService;

    public static final String START_TIME = "09:30:00";
    public static final String END_TIME = "09:45:00";
    public static final String DATE_PATTERN = "yyyy-MM-dd";
    public static final String TIME_PATTERN = "HH:mm";

    @Scheduled(cron = "0 30 9 * * MON-FRI")
    public void getMorningCandleData(){
        String date = new SimpleDateFormat(DATE_PATTERN).format(new Date());
        String fromDate = date + " " + START_TIME;
        String toDate = date + " " + END_TIME;
        String interval = "15";
        List<String[]> data = dataGatheringService.getDataForStock(fromDate, toDate, interval, null, true);// symbol name is null as we want data for all the scrip names in the csv.
        dataWriterService.writeData(data); //write data to csv and populate map.
    }

    @Scheduled(cron = "0 31/5 9-10 * * MON-FRI")
    @Scheduled(cron = "0 0/5 10-15 * * MON-FRI")
    public void findStockToBuy(){
        boolean isScheduled = GlobalUtilities.findStockScheduler;
        if(isScheduled){
            logger.info("StockBuyService is running.");
            String date = new SimpleDateFormat(DATE_PATTERN).format(new Date());
            String startTime = LocalTime.now().minusMinutes(5).format(DateTimeFormatter.ofPattern(TIME_PATTERN))+":00";
            String endTIme = LocalTime.now().format(DateTimeFormatter.ofPattern(TIME_PATTERN))+":00";
            String fromDate = date +" "+startTime;
            String toDate = date+" "+endTIme;
            stockBuyService.getScripToPlaceOrder(fromDate, toDate, null);//symbol name is null as we want to get data for all the scrips in the list.
        }
        else{
            logger.info("StockBuyService is temporarily paused.");
        }
    }

    @Scheduled(cron = "0 31/5 9-10 * * MON-FRI")
    @Scheduled(cron = "0 0/5 10-15 * * MON-FRI")
    public void startOrderModification(){
        boolean startOrderModification = GlobalUtilities.startOrderModification;
        String boughtStock = GlobalUtilities.boughtStock;
        if(startOrderModification && (!boughtStock.isEmpty() || boughtStock != null)){
            logger.info("Modification service is running.");
            String date = new SimpleDateFormat(DATE_PATTERN).format(new Date());
            String startTime = LocalTime.now().minusMinutes(5).format(DateTimeFormatter.ofPattern(TIME_PATTERN))+":00";
            String endTIme = LocalTime.now().format(DateTimeFormatter.ofPattern(TIME_PATTERN))+":00";
            String fromDate = date +" "+startTime;
            String toDate = date+" "+endTIme;
            modifyOrderService.modifyOrder(fromDate, toDate, boughtStock);
        }
        else{
            logger.info("Modification service is temporarily paused.");
        }
    }
}
