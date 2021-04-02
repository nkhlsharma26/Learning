package com.nikhil.optionstradingapp;

import com.nikhil.optionstradingapp.model.UserDetails;
import com.nikhil.optionstradingapp.service.AuthorizationService;
import com.nikhil.optionstradingapp.service.AuthorizationServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

@SpringBootApplication
@ComponentScan({"com.nikhil"})
@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
@EnableRetry
@EnableSwagger2
@EnableScheduling
public class OptionsTradingAppApplication {

	@Autowired
	UserDetails userDetails;
	public static void main(String[] args) {

		SpringApplication.run(OptionsTradingAppApplication.class, args);
	}
	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

	@Bean
	public Docket swaggerConfiguration(){
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.paths(PathSelectors.ant("/api/*"))
				.apis(RequestHandlerSelectors.basePackage("com.nikhil.optionstradingapp.controller"))
				.build()
				.apiInfo(apiDetails());
	}

	private ApiInfo apiDetails(){
		return new ApiInfo(
			"Options Application: Temporary UI",
				"Swagger UI to place and square off orders",
				"BETA",
				"",
				null,
				"Built for Shashank Shrivastava",
				"",
				Collections.emptyList());

	}

}
