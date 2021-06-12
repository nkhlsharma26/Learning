package com.nikhil.webscraper.modal;

import lombok.Data;

import java.util.List;

@Data
public class TechnicalDetailsRequest {

    private String time_frame;
    private List<String> stocks;
    private String user_broker_id;
}
