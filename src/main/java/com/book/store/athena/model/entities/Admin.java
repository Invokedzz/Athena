package com.book.store.athena.model.entities;

import com.book.store.athena.model.dto.admin.RegisterAdminDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "admins")
public class Admin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String email;

    private String password;

    @OneToMany
    @JoinTable(name = "admin_roles", joinColumns = @JoinColumn(name = "admin_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set <Role> roles;

    private Boolean active;

    public Admin (RegisterAdminDto registerAdminDto) {

        this.active = true;

        this.name = registerAdminDto.username();

        this.email = registerAdminDto.email();

        this.password = registerAdminDto.password();

    }

}
