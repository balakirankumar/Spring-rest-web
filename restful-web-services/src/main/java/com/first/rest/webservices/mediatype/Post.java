package com.first.rest.webservices.mediatype;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.hateoas.server.core.Relation;

import javax.validation.constraints.NotNull;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Relation(collectionRelation = "content")
public class Post {

    private String id;

    @NotNull
    private String title;

    private String description;
}
