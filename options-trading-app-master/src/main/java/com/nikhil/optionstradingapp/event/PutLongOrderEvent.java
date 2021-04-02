package com.nikhil.optionstradingapp.event;

import com.nikhil.optionstradingapp.model.OrderEventData;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class PutLongOrderEvent extends ApplicationEvent {
    private OrderEventData data;
    public PutLongOrderEvent(Object source, OrderEventData orderEventData) {
        super(source);
        this.data = orderEventData;
    }
}
