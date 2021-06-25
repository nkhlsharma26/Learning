package com.nikhil.tradingadvisory.samco.model;

import lombok.*;

import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class ParameterInfo {
    private List<String> symbols;
    private String fromDate;
    private String toDate;
    private String interval;
}
