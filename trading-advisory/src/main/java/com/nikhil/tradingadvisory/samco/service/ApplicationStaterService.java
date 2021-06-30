package com.nikhil.tradingadvisory.samco.service;

import com.nikhil.tradingadvisory.samco.Abstraction.DataGatheringService;
import com.nikhil.tradingadvisory.samco.Abstraction.ModifyOrderService;
import com.nikhil.tradingadvisory.samco.Abstraction.StockSelectionService;
import com.nikhil.tradingadvisory.samco.model.GlobalUtilities;
import com.nikhil.tradingadvisory.samco.model.ReferenceData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

import static com.nikhil.tradingadvisory.samco.model.GlobalUtilities.isPollingServiceEnabled;

@Service
public class ApplicationStaterService {

    private final static Logger LOGGER = LoggerFactory.getLogger(ApplicationStaterService.class);

    @Autowired
    DataGatheringService dataGatheringService;

    @Autowired
    ReferenceDataService referenceDataService;

    @Autowired
    DataPollingService dataPollingService;

    @Autowired
    TrendFinderService trendFinderService;

    @Autowired
    StockSelectionService stockSelectionService;

    /*@Autowired
    DataWriterService dataWriterService;

    @Autowired
    PrepareOrderService prepareOrderService;
*/
    @Autowired
    ModifyOrderService modifyOrderService;

    public static final String START_TIME = "09:30:00";
    public static final String END_TIME = "09:45:00";
    public static final String DATE_PATTERN = "yyyy-MM-dd";
    public static final String TIME_PATTERN = "HH:mm";

    @Scheduled(cron = "0 30 9 * * 1-5")
    public void getMorningCandleData(){
        String date = new SimpleDateFormat(DATE_PATTERN).format(new Date());
        String fromDate = date + " " + START_TIME;
        String toDate = date + " " + END_TIME;
        String interval = "15";
        //Get trend for today
        new Thread(() -> trendFinderService.getTrendForToday()).start();

        referenceDataService.deleteAll();
        LOGGER.info("It's 9:30 AM, starting to get reference data.");
        List<ReferenceData> data = dataGatheringService.getDataForStock(fromDate, toDate, interval, null, true);// symbol name is null as we want data for all the scrip names in the csv.
        LOGGER.info("Reference data collection completed. Saving to DB!");
        referenceDataService.saveAll(data);
        //dataWriterService.writeData(data); //write data to csv and populate map.
    }

    @Scheduled(cron = "0 35/2 9 * * 1-5")
    public void startPollingData(){
        if(isPollingServiceEnabled){
            String date = new SimpleDateFormat(DATE_PATTERN).format(new Date());
            String startTime = LocalTime.now().minusMinutes(5).format(DateTimeFormatter.ofPattern(TIME_PATTERN))+":00";
            String endTIme = LocalTime.now().format(DateTimeFormatter.ofPattern(TIME_PATTERN))+":00";
            String fromDate = date +" "+startTime;
            String toDate = date+" "+endTIme;
            dataPollingService.pollData(fromDate,toDate,"5", "Negative");
            //prepareOrderService.getScripToPlaceOrder(fromDate, toDate, null);//symbol name is null as we want to get data for all the scrips in the list.
        }
    }

    @Scheduled(cron = "0 35/5 9-15 * * MON-FRI")
    public void findStockToBuy(){
        boolean isScheduled = GlobalUtilities.findStockScheduler;
        if(isScheduled){
            String date = new SimpleDateFormat(DATE_PATTERN).format(new Date());
            String startTime = LocalTime.now().minusMinutes(5).format(DateTimeFormatter.ofPattern(TIME_PATTERN))+":00";
            String endTIme = LocalTime.now().format(DateTimeFormatter.ofPattern(TIME_PATTERN))+":00";
            String fromDate = date +" "+startTime;
            String toDate = date+" "+endTIme;
            stockSelectionService.selectStockToBuy(fromDate, toDate);
        }
    }


    @Scheduled(cron = "0 35/5 9-15 * * 1-5")
    public void startOrderModification(){
        boolean startOrderModification = GlobalUtilities.startOrderModification;
        String boughtStock = GlobalUtilities.boughtStock;
        if(startOrderModification && (boughtStock.isEmpty() || boughtStock == null)){
            String date = new SimpleDateFormat(DATE_PATTERN).format(new Date());
            String startTime = LocalTime.now().minusMinutes(5).format(DateTimeFormatter.ofPattern(TIME_PATTERN))+":00";
            String endTIme = LocalTime.now().format(DateTimeFormatter.ofPattern(TIME_PATTERN))+":00";
            String fromDate = date +" "+startTime;
            String toDate = date+" "+endTIme;
            modifyOrderService.modifyOrder(fromDate, toDate, boughtStock);
        }
    }
}
