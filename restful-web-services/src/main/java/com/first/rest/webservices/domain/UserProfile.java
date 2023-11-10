package com.first.rest.webservices.domain;


import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
public class UserProfile {

    @Id
    private String id;

    @NotNull
    @Column(name = "email")
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


    @Column(name = "password")
    private String password;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "userProfile", cascade = CascadeType.ALL)
    private List<Post> posts;


}
