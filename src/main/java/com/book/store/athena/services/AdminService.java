package com.book.store.athena.services;

import com.book.store.athena.infra.SecurityConfig;
import com.book.store.athena.model.dto.admin.RegisterAdminDto;
import com.book.store.athena.model.entities.Admin;
import com.book.store.athena.model.repository.AdminRepository;
import com.book.store.athena.model.repository.RoleRepository;
import org.springframework.stereotype.Service;

@Service
public class AdminService {

    private final AdminRepository adminRepository;

    private final RoleRepository roleRepository;

    private final SecurityConfig securityConfig;

    public AdminService (AdminRepository adminRepository, RoleRepository roleRepository, SecurityConfig securityConfig) {

        this.adminRepository = adminRepository;

        this.roleRepository = roleRepository;

        this.securityConfig = securityConfig;

    }

    public Admin create (RegisterAdminDto registerAdminDto) {

        var role = roleRepository.findById(2L);

        Admin admin = new Admin(registerAdminDto);

        String adminPassword = securityConfig.passwordEncoder().encode(admin.getPassword());

        admin.setPassword(adminPassword);

        var createdAdmin = adminRepository.save(admin);

        if (role.isPresent()) {

            var obtainedRole = role.get();

            roleRepository.insertAdminRole(createdAdmin.getId(), obtainedRole.getId());

            return admin;

        }

        return null;

    }

}
