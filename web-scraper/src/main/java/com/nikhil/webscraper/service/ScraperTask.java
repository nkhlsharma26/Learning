package com.nikhil.webscraper.service;

import com.nikhil.webscraper.modal.TechnicalParameters;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.concurrent.Callable;

public class ScraperTask implements Callable<TechnicalParameters> {

    private String scripName;

    private static final String URL = "https://www.icharts.in/screener-eod.html";

    public ScraperTask(String scripName) {
        this.scripName = scripName;
    }

    @Override
    public TechnicalParameters call() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        ChromeDriver driver = new ChromeDriver(options);
        driver.get(URL);
        WebElement searchBar = driver.findElement(By.id("searchString"));
        searchBar.sendKeys(scripName);
        WebElement searchButton = driver.findElement(By.xpath("//*[@id=\"searchButtonsLayer\"]/button[1]"));
        searchButton.click();
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String sma50 = driver.findElement(By.xpath("//*[@id=\"row_"+scripName.toUpperCase()+"\"]/td[17]")).getText();
        String rsi = driver.findElement(By.xpath("//*[@id=\"row_"+scripName.toUpperCase()+"\"]/td[30]")).getText();
        String sma200 = driver.findElement(By.xpath("//*[@id=\"row_"+scripName.toUpperCase()+"\"]/td[21]")).getText();
        TechnicalParameters tp = new TechnicalParameters();
        tp.setSma50(sma50);
        tp.setRsi(rsi);
        tp.setSma200(sma200);
        tp.setScripName(scripName);
        WebElement clearButton = driver.findElement(By.xpath("//*[@id=\"searchButtonsLayer\"]/button[2]"));
        clearButton.click();
        driver.close();
        return tp;
    }
}
