package com.joongang.api.domain;

import lombok.Data;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@JsonIgnoreProperties(value = { "hibernateLazyInitializer" , "handler", "password"})
public class User {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private String password;

    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    private Set<UserRole> roles = new HashSet<>();

}
