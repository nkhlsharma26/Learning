package com.nikhil.webscraper;

import com.nikhil.webscraper.modal.TechnicalParameters;
import com.nikhil.webscraper.service.ScraperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/webScraper")
public class ScraperController {

    @Autowired
    ScraperService scraperService;

    @GetMapping("/getTechParameters/{scripNames}")
    public ResponseEntity<List<TechnicalParameters>> getTechnicalParameters(@PathVariable List<String> scripNames){
        return scraperService.scrape(scripNames);
    }
}
