package com.nikhil.Intraday.equities.modal;

import lombok.Data;

@Data
public class GlobalUtilities {
    public static boolean findStockScheduler = true;
    public static boolean startOrderModification = false;
    public static String boughtStock = "";
    public static String initialTrend = "";
}
