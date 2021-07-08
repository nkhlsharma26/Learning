package com.nikhil.tradingadvisory.samco.model;

import lombok.*;
import org.springframework.stereotype.Service;

import javax.persistence.*;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PreviousDayData {
    @Id
    @Column(name = "id")
    private Long id;
    private String high;
    private String low;
    private String close;

    @OneToOne(cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "id")
    @MapsId
    private ReferenceData referenceData;

}
