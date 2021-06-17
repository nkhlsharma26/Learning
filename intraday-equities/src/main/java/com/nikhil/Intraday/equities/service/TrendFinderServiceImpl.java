package com.nikhil.Intraday.equities.service;

import com.nikhil.Intraday.equities.modal.GlobalUtilities;
import com.nikhil.Intraday.equities.modal.IndexDetailResponse;
import com.nikhil.Intraday.equities.modal.SessionInfo;
import com.nikhil.Intraday.equities.service.Abstraction.QuoteService;
import com.nikhil.Intraday.equities.service.Abstraction.TrendFinderService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class TrendFinderServiceImpl implements TrendFinderService {
    @Autowired
    QuoteService quoteService;

    private final Logger logger = LogManager.getLogger(TrendFinderServiceImpl.class);

    @Override
    @Scheduled(cron = "0 29 9 * * MON-FRI")
    public void findTrend() {
        IndexDetailResponse initialIndexData = getInitialIndexData();
        double percentage = Double.valueOf(initialIndexData.getChangePercentage());
        GlobalUtilities.initialTrend = percentage > 0 ? "BUY" : "SELL";
        logger.info("Trend for today is :"+GlobalUtilities.initialTrend);
    }

    private IndexDetailResponse getInitialIndexData() {
        return quoteService.getIndexQuote();
    }
}
