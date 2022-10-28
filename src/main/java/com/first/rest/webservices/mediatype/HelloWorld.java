package com.first.rest.webservices.mediatype;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import javax.validation.constraints.NotNull;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class HelloWorld {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @NotNull
    private String message;

    @NotNull
    private String id;

}
