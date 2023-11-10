package com.first.rest.webservices.domain;


import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@NoArgsConstructor
public class UserProfileRole {

    @Id
    private String id;

    @JoinColumn(name = "userProfileId")
    @ManyToOne(fetch = FetchType.LAZY)
    private UserProfile userProfile;

    @JoinColumn(name = "roleId")
    @ManyToOne(fetch = FetchType.LAZY)
    private Role role;

    @JoinColumn(name = "createdBy")
    @ManyToOne(fetch = FetchType.LAZY)
    private UserProfile createdBy;

    @JoinColumn(name = "modifiedBy")
    @ManyToOne(fetch = FetchType.LAZY)
    private UserProfile modifiedBy;

    @Column(name = "created_date")
    private Date createdDate;

    @Column(name = "modified_date")
    private Date modifiedDate;
}
