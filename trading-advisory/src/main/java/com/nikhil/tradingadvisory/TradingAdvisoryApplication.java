package com.nikhil.tradingadvisory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

@SpringBootApplication
@EnableScheduling
public class TradingAdvisoryApplication {

	public static void main(String[] args) {
		SpringApplication.run(TradingAdvisoryApplication.class, args);
	}
	@PostConstruct
	void init() {
		TimeZone.setDefault(TimeZone.getTimeZone("IST"));
	}

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
}
