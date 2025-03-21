package com.book.store.athena.controllers;

import com.book.store.athena.model.dto.client.FindAllActiveUsersDTO;
import com.book.store.athena.model.dto.client.FindUserByIdDTO;
import com.book.store.athena.model.dto.client.RegisterUserDTO;
import com.book.store.athena.services.AdminService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.Set;

@RestController
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {

        this.adminService = adminService;

    }

    @PostMapping("/register-as-administrator")
    protected ResponseEntity <Void> registerAdmin (@RequestBody @Valid RegisterUserDTO registerUserDTO) {

        adminService.create(registerUserDTO);

        return ResponseEntity.status(HttpStatus.CREATED).build();

    }

    @GetMapping("/all-administrators")
    protected  ResponseEntity <Set<FindAllActiveUsersDTO>> findAllAdmins() {

        var admins = adminService.findAll();

        return ResponseEntity.status(HttpStatus.OK).body(admins);

    }

    @GetMapping("/profile-adm/{id}")
    protected  ResponseEntity <Optional<FindUserByIdDTO>> adminProfile (@PathVariable Long id) {

        var profile = adminService.findAdminById(id);

        return ResponseEntity.status(HttpStatus.OK).body(profile);

    }

}
