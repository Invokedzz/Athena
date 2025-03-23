package com.book.store.athena.services;

import com.book.store.athena.exceptions.NotFoundException;
import com.book.store.athena.infra.SecurityConfig;
import com.book.store.athena.model.dto.client.FindAllActiveUsersDTO;
import com.book.store.athena.model.dto.client.FindUserByIdDTO;
import com.book.store.athena.model.dto.client.RegisterUserDTO;
import com.book.store.athena.model.entities.User;
import com.book.store.athena.model.repository.RoleRepository;
import com.book.store.athena.model.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AdminService {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final SecurityConfig securityConfig;

    public AdminService (UserRepository userRepository, RoleRepository roleRepository, SecurityConfig securityConfig) {

        this.userRepository = userRepository;

        this.roleRepository = roleRepository;

        this.securityConfig = securityConfig;

    }

    @Transactional
    public void create (RegisterUserDTO registerUserDTO) {

        var roleUser = roleRepository.findById(1L);

        var roleAdmin = roleRepository.findById(2L);

        User admin = new User(registerUserDTO);

        String adminPassword = securityConfig.passwordEncoder().encode(admin.getPassword());

        admin.setPassword(adminPassword);

        var createdAdmin = userRepository.save(admin);

        if (roleAdmin.isPresent() && roleUser.isPresent()) {

            var obtainedAdminRole = roleAdmin.get();

            var obtainedUserRole = roleUser.get();

            roleRepository.insertRole(createdAdmin.getId(), obtainedAdminRole.getId());

            roleRepository.insertRole(createdAdmin.getId(), obtainedUserRole.getId());

        }

    }

    public Optional <FindUserByIdDTO> findAdminById (Long id) {

        var adm = userRepository.findUserAccordingToRole(id, true, "ROLE_ADMIN")
                    .stream().map(FindUserByIdDTO::new).findFirst();

        verifyIfCollectionOfAdminsExist(adm);

        return adm;

    }

    public Set <FindAllActiveUsersDTO> findAll () {

        return userRepository.findAllUsersByActive(true, "ROLE_ADMIN")
                .stream().map(FindAllActiveUsersDTO::new).collect(Collectors.toSet());

    }

    private void verifyIfCollectionOfAdminsExist (Optional <?> adm) {

        if (adm.isEmpty()) {

            throw new NotFoundException("Admin not found");

        }

    }

}
