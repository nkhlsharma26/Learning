package com.nikhil.Intraday.equities.service.Abstraction;

import com.nikhil.Intraday.equities.modal.ModifyOrderPayload;

public interface ModifyOrderService {
    void modifyOrder(String fromDate, String toDate, String boughtStock);
}
