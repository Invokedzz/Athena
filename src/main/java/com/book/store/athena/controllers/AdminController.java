package com.book.store.athena.controllers;

import com.book.store.athena.infra.TokenAuthService;
import com.book.store.athena.model.dto.client.FindAllActiveUsersDTO;
import com.book.store.athena.model.dto.client.FindUserByIdDTO;
import com.book.store.athena.model.dto.client.RegisterUserDTO;
import com.book.store.athena.model.dto.client.UserLoginDTO;
import com.book.store.athena.model.entities.User;
import com.book.store.athena.services.AdminService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.Set;

@RestController
public class AdminController {

    private final AdminService adminService;

    private final AuthenticationManager authenticationManager;

    private final TokenAuthService tokenAuthService;

    public AdminController(AdminService adminService, AuthenticationManager authenticationManager, TokenAuthService tokenAuthService) {

        this.adminService = adminService;

        this.authenticationManager = authenticationManager;

        this.tokenAuthService = tokenAuthService;

    }

    @PostMapping("/register")
    protected ResponseEntity <Void> registerAdmin (@RequestBody @Valid RegisterUserDTO registerUserDTO) {

        adminService.create(registerUserDTO);

        return ResponseEntity.status(HttpStatus.CREATED).build();

    }

    @PostMapping("/login")
    protected ResponseEntity <String> loginAdmin (@RequestBody @Valid UserLoginDTO userLoginDTO) {

        var token = new UsernamePasswordAuthenticationToken(userLoginDTO.username(), userLoginDTO.password());

        var authentication = authenticationManager.authenticate(token);

        return ResponseEntity.status(HttpStatus.OK).
                body(tokenAuthService.generateUserJWToken((User)authentication.getPrincipal()));

    }

    @GetMapping("/all")
    protected  ResponseEntity <Set<FindAllActiveUsersDTO>> findAllAdmins() {

        var admins = adminService.findAll();

        return ResponseEntity.status(HttpStatus.OK).body(admins);

    }

    @GetMapping("/profile/{id}")
    protected  ResponseEntity <Optional<FindUserByIdDTO>> adminProfile (@PathVariable Long id) {

        var profile = adminService.findAdminById(id);

        return ResponseEntity.status(HttpStatus.OK).body(profile);

    }

}
