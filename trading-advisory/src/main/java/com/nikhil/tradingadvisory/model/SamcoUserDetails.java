package com.nikhil.tradingadvisory.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity
@Table(name = "samco_user_detail")
public class SamcoUserDetails {
    @Id
    @Column(name = "id")
    private Long id;
    private String userId;
    private String password;
    private String yob;

    public SamcoUserDetails(String userId, String password, String yob){
        this.yob = yob;
        this.userId = userId;
        this.password = password;
    }
}
