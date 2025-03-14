package com.book.store.athena.services;

import com.book.store.athena.infra.SecurityConfig;
import com.book.store.athena.model.dto.admin.RegisterAdminDto;
import com.book.store.athena.model.dto.admin.UpdateAdminDto;
import com.book.store.athena.model.entities.Admin;
import com.book.store.athena.model.repository.AdminRepository;
import com.book.store.athena.model.repository.RoleRepository;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class AdminService {

    private final AdminRepository adminRepository;

    private final RoleRepository roleRepository;

    private final SecurityConfig securityConfig;

    public AdminService (AdminRepository adminRepository, RoleRepository roleRepository,SecurityConfig securityConfig) {

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

    public Set <Admin> profile (Long id) {

        return null;

    }

    public Admin update (Long id, UpdateAdminDto updateAdminDto) {

        var adm = adminRepository.findById(id);

        if (adm.isPresent()) {

            var obtainedAdm = adm.get();

            obtainedAdm.updateAdm(updateAdminDto);

            adminRepository.save(obtainedAdm);

            return obtainedAdm;

        }

        return null;

    }

    public Admin disable (Long id) {

        var adm = adminRepository.findById(id);

        if (adm.isPresent()) {

            var obtainedAdm = adm.get();

            obtainedAdm.disable();

            adminRepository.save(obtainedAdm);

            return obtainedAdm;

        }

        return null;

    }

    public Admin reactivate (Long id) {

        var adm = adminRepository.findById(id);

        if (adm.isPresent()) {

            var obtainedAdm = adm.get();

            obtainedAdm.activate();

            adminRepository.save(obtainedAdm);

            return obtainedAdm;

        }

        return null;

    }

}
