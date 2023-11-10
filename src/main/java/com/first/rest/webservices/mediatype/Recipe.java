package com.first.rest.webservices.mediatype;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.hateoas.server.core.Relation;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Data
@Relation(collectionRelation = "content")
public class Recipe {

    private String id;

    @NotNull
    private String name;

    @NotNull
    private String description;

    @NotNull
    private String imagePath;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String createdBy;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String modifiedBy;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Date createdDate;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Date modifiedDate;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<Ingredient> ingredients;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Map<String, String> _links;

}