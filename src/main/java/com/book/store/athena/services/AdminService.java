package com.book.store.athena.services;

import com.book.store.athena.infra.SecurityConfig;
import com.book.store.athena.model.dto.admin.FindAdminByIdDto;
import com.book.store.athena.model.dto.admin.RegisterAdminDto;
import com.book.store.athena.model.dto.client.UpdateUserDto;
import com.book.store.athena.model.entities.Role;
import com.book.store.athena.model.entities.User;
import com.book.store.athena.model.repository.RoleRepository;
import com.book.store.athena.model.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class AdminService {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final SecurityConfig securityConfig;

    public AdminService (UserRepository userRepository, RoleRepository roleRepository,SecurityConfig securityConfig) {

        this.userRepository = userRepository;

        this.roleRepository = roleRepository;

        this.securityConfig = securityConfig;

    }

    public User create (RegisterAdminDto registerAdminDto) {

        var role = roleRepository.findById(2L);

        User admin = new User(registerAdminDto);

        String adminPassword = securityConfig.passwordEncoder().encode(admin.getPassword());

        admin.setPassword(adminPassword);

        var createdAdmin = userRepository.save(admin);

        if (role.isPresent()) {

            var obtainedRole = role.get();

            roleRepository.insertRole(createdAdmin.getId(), obtainedRole.getId());

            return admin;

        }

        return null;

    }

    public Set <FindAdminByIdDto> findAdminById (Long id) {

        return null;

    }

}
