package com.first.rest.webservices.domain;


import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Data
public class Recipe {

    @Id
    private String id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "image_path")
    private String imagePath;

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

    @OneToMany(mappedBy = "recipe")
    private List<Ingredient> ingredients;

}
