package com.nikhil.tradingadvisory.samco.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PreviousDayData {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String high;
    private String low;
    private String close;

}
