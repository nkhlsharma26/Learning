package com.nikhil.webscraper.service;

import com.nikhil.webscraper.modal.TechnicalParameters;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Service
@AllArgsConstructor
public class ScraperService {

    private static final int THREAD_POOL_SIZE = 5;

    public ResponseEntity<List<TechnicalParameters>> scrape(String[] scripNames){

        List<Future<TechnicalParameters>> list = new ArrayList<>();
        ExecutorService executorService = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
        for (String scripName: scripNames) {
            ScraperTask scraperTask = new ScraperTask(scripName);
            Future<TechnicalParameters> future = executorService.submit(scraperTask);
            list.add(future);
        }
        List<TechnicalParameters> techDetails = new ArrayList<>();
        for (Future<TechnicalParameters> item : list) {
            try {

                techDetails.add(item.get());

            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }
        return new ResponseEntity<>(techDetails, HttpStatus.OK);
    }

}
