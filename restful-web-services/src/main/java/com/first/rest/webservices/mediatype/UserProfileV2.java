package com.first.rest.webservices.mediatype;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.Date;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class UserProfileV2 {


    @JsonPropertyDescription("The unique identifier of the User")
    private String id;


    @JsonInclude(JsonInclude.Include.NON_NULL)
    @NotNull
    private String email;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Name name;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @NotNull
    private Date birthDate;
}
