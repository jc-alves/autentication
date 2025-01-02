package com.dev.autenticacao.core.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Entity(name = "users")
@Table(name = "users")
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(name = "email")
    private String email;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    @ToString.Exclude
    private List<Roles> roles;

}
