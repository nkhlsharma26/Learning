package com.nikhil.Intraday.equities.service;

import com.nikhil.Intraday.equities.modal.ParameterInfo;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ParameterService {
    
    ParameterInfo pi = new ParameterInfo();

    public void setParameters(List<String> symbols, String fromDate, String toDate, String interval){
        pi.setSymbols(symbols);
        pi.setFromDate(fromDate);
        pi.setToDate(toDate);
        pi.setInterval(interval);
    }
    
    public ParameterInfo getParameters(){
        return pi;
    }
}
