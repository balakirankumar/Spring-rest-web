package com.first.rest.webservices.mediatype;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Date;


@Data
public class Ingredient {

    private String id;

    @NotNull
    private String name;

    @NotNull
    private String amount;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String createdBy;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String modifiedBy;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Date createdDate;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Date modifiedDate;
}


