package com.SupportPortalApplication.supportportalapplication.modal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false,updatable = false)
    private Long id;
    private String userId;
    private String firstName;
    private String lastName;
    private String userName;
    private String password;
    private String email;
    private String profileImageUrl;
    private Date lastLoginDate;
    private Date lastLoginDateDisplay;
    private Date joinDate;
    private String[] roles;
    private String[] authorities;
    private Boolean isActive;
    private Boolean isNotLocaked;
}
