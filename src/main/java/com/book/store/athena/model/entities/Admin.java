package com.book.store.athena.model.entities;

import com.book.store.athena.model.dto.admin.RegisterAdminDto;
import com.book.store.athena.model.dto.admin.UpdateAdminDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    public void updateAdm (UpdateAdminDto updateAdminDto) {

        if (updateAdminDto.email() != null) {

            this.email = updateAdminDto.email();

        }

        if (updateAdminDto.username() != null) {

            this.name = updateAdminDto.username();

        }

        if (updateAdminDto.password() != null) {

            this.password = updateAdminDto.password();

        }

    }

    public void activate () {

        this.active = true;

    }

    public void disable () {

        this.active = false;

    }

}
