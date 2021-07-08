package com.nikhil.tradingadvisory.samco.model;

import lombok.*;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class ReferenceData {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    private String symbol;
    private String high;
    private String low;
    private String close;
    @Builder.Default
    private Double percentage = 0.0;
    private String volume;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "referenceData")
    private PreviousDayData previousDayData;

    @Override
    public String toString() {
        return "Data{" +
                "symbol='" + symbol + '\'' +
                ", high='" + high + '\'' +
                ", low='" + low + '\'' +
                ", close='" + close + '\'' +
                ", volume='" + volume + '\'' +
                '}';
    }

}
