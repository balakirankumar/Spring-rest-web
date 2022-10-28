package com.first.rest.webservices.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

@Entity
@Data
public class Post {

    @Id
    private String id;

    @NotNull
    private String title;

    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    private UserProfile userProfile;
}
