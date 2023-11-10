package com.first.rest.webservices.domain;


import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@NoArgsConstructor
public class Role {

    @Id
    private String id;

    private String name;

    private String description;

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
