package com.nikhil.Intraday.equities.modal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IntraDayCandleData {
    private String dateTime;
    private String open;
    private String high;
    private String low;
    private String close;
    private String volume;
}
