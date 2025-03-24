package com.book.store.athena.model.entities;

import com.book.store.athena.exceptions.ForbiddenRequestException;
import com.book.store.athena.model.dto.client.RegisterUserDTO;
import com.book.store.athena.model.dto.client.UpdateUserDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Data
@Entity
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username")
    private String name;

    private String email;

    private String password;

    private LocalDate birthDate;

    @OneToMany(mappedBy = "users", cascade = CascadeType.ALL)
    private Set <Favorite> favoriteBooks;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set <Role> roles;

    private Boolean active;

    public User (RegisterUserDTO registerUserDto) {

        this.active = true;

        this.name = registerUserDto.username();

        this.email = registerUserDto.email();

        this.password = registerUserDto.password();

        this.birthDate = registerUserDto.birthDate();

    }

    public void activate () {

        this.active = true;

    }

    public void disable () {

        this.active = false;

    }

    public void update (UpdateUserDTO updateUserDto) {

        if (updateUserDto.username() != null) {

            this.name = updateUserDto.username();

        }

        if (updateUserDto.email() != null) {

            this.email = updateUserDto.email();

        }

        if (updateUserDto.password() != null) {

            this.password = updateUserDto.password();

        }

    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        return roles.stream().map(role ->
                new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());

    }

    @Override
    public String getUsername() {

        return name;

    }


}
