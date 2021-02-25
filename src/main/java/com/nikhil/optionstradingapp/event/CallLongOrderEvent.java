package com.nikhil.optionstradingapp.event;

import com.nikhil.optionstradingapp.model.OrderEventData;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class CallLongOrderEvent extends ApplicationEvent {
    private OrderEventData data;

     public CallLongOrderEvent(Object source, OrderEventData eventData) {
        super(source);
        this.data = eventData;
    }
}
