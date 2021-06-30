package com.nikhil.tradingadvisory.samco.model;

import lombok.*;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Builder
public class PlaceOrderResponseEntity {
    @Id
    @NotNull
    private String userId;
    private String serverTime;
    private String msgId;
    private String orderNumber;
    private String status;
    private String statusMessage;
    private String exchangeOrderStatus;
    private String rejectionReason;
    @OneToOne(cascade= CascadeType.ALL)
    private OrderDetails orderDetails;
}

