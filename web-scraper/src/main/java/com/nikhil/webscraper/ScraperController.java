package com.nikhil.webscraper;

import com.nikhil.webscraper.modal.TechnicalParameters;
import com.nikhil.webscraper.service.ScraperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/webScraper")
public class ScraperController {

    @Autowired
    ScraperService scraperService;

    @GetMapping("/getParameters")
    public ResponseEntity<List<TechnicalParameters>> getTechnicalParameters(@RequestParam String [] scripNames){
        return scraperService.scrape(scripNames);
    }
}
