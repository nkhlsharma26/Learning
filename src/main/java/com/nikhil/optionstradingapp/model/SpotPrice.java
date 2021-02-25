package com.nikhil.optionstradingapp.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class SpotPrice {
    @JsonProperty("timestamp")
    private String timeStamp;
    @JsonProperty("lastPrice")
    private String lastPrice;
}
