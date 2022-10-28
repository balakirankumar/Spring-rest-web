package com.first.rest.webservices.domain;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.util.Date;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
public class UserProfile {

    @Id
    private String id;

    @NotNull
    private String email;

    @Column(name = "first_name")
    @NotNull
    private String firstName;

    @Column(name = "last_name")
    @NotNull
    private String lastName;

    @Column(name = "birth_date")
    @NotNull
    @Past
    private Date birthDate;

    @OneToMany(mappedBy = "userProfile")
    private List<Post> postList;


}
