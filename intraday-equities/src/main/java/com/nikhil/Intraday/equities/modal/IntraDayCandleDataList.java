package com.nikhil.Intraday.equities.modal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IntraDayCandleDataList {
    private String serverTime;
    private String msgId;
    private String status;
    private String statusMessage;
    private String symbol;
    private List<IntraDayCandleData> intradayCandleData;
}

