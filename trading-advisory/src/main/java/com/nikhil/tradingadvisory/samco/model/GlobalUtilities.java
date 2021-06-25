package com.nikhil.tradingadvisory.samco.model;

import lombok.Data;

@Data
public class GlobalUtilities {
    public static boolean findStockScheduler = true;
    public static Boolean isPollingServiceEnabled = true;
    public static boolean startOrderModification = false;
    public static String boughtStock = "";
}
