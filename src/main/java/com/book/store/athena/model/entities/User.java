package com.book.store.athena.model.entities;

import com.book.store.athena.model.dto.client.FindAllActiveUsersDto;
import com.book.store.athena.model.dto.client.RegisterUserDto;
import com.book.store.athena.model.dto.client.UpdateUserDto;
import jakarta.persistence.*;
import jakarta.validation.constraints.Past;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

@Data
@Entity
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
    private List <Favorite> favoriteBooks;

    private Boolean active;

    public User (RegisterUserDto registerUserDto) {

        this.active = true;

        this.name = registerUserDto.username();

        this.email = registerUserDto.email();

        this.password = registerUserDto.password();

        this.birthDate = registerUserDto.birthDate();

    }

    public void activateAccount () {

        this.active = true;

    }

    public void disableAccount () {

        this.active = false;

    }

    public void update (UpdateUserDto updateUserDto) {

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

        return List.of(new SimpleGrantedAuthority("ROLE_USER"));

    }

    @Override
    public String getUsername() {

        return name;

    }


}
