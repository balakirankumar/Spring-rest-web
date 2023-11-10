package com.first.rest.webservices.domain;


import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
public class Ingredient {

    @Id
    private String id;

    @Column(name = "name")
    private String name;

    @Column(name = "amount")
    private String amount;

    @JoinColumn(name = "createdBy")
    @ManyToOne(fetch = FetchType.LAZY)
    private UserProfile createdBy;

    @JoinColumn(name = "modifiedBy")
    @ManyToOne(fetch = FetchType.LAZY)
    private UserProfile modifiedBy;

    @Column(name = "createdDate")
    private Date createdDate;

    @Column(name = "modifiedDate")
    private Date modifiedDate;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "recipe_id")
    private Recipe recipe;

}
