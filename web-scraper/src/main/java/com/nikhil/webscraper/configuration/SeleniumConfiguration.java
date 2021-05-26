package com.nikhil.webscraper.configuration;

import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
public class SeleniumConfiguration {
    @PostConstruct
    void postConstruct(){
        System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");
    }

}
