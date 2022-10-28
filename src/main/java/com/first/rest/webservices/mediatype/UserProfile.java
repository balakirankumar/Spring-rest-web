package com.first.rest.webservices.mediatype;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;


@Data
@SuperBuilder
@AllArgsConstructor
public class UserProfile {

    @JsonPropertyDescription("The unique identifier of the User")
    private String id;


    @JsonInclude(JsonInclude.Include.NON_NULL)
    @NotNull
    private String email;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String firstName;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String lastName;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @NotNull
    private Date birthDate;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<Post> posts;


}
