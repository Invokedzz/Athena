package com.book.store.athena.services;

import com.book.store.athena.infra.SecurityConfig;
import com.book.store.athena.model.dto.admin.RegisterAdminDto;
import com.book.store.athena.model.dto.client.FindAllActiveUsersDto;
import com.book.store.athena.model.dto.client.FindUserByIdDto;
import com.book.store.athena.model.entities.User;
import com.book.store.athena.model.repository.RoleRepository;
import com.book.store.athena.model.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

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

        var roleUser = roleRepository.findById(1L);

        var roleAdmin = roleRepository.findById(2L);

        User admin = new User(registerAdminDto);

        String adminPassword = securityConfig.passwordEncoder().encode(admin.getPassword());

        admin.setPassword(adminPassword);

        var createdAdmin = userRepository.save(admin);

        if (roleAdmin.isPresent() && roleUser.isPresent()) {

            var obtainedAdminRole = roleAdmin.get();

            var obtainedUserRole = roleUser.get();

            roleRepository.insertRole(createdAdmin.getId(), obtainedAdminRole.getId());

            roleRepository.insertRole(createdAdmin.getId(), obtainedUserRole.getId());

            return admin;

        }

        return null;

    }

    public Optional <FindUserByIdDto> findAdminById (Long id) {

        return userRepository.findUserAccordingToRole(id, true, "ROLE_ADMIN")
                    .stream().map(FindUserByIdDto::new).findFirst();

    }

    public Set <FindAllActiveUsersDto> findAll () {

        return userRepository.findAllUsersByActive(true, "ROLE_ADMIN")
                .stream().map(FindAllActiveUsersDto::new).collect(Collectors.toSet());

    }

}
