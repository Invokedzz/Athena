package com.book.store.athena.controllers;

import com.book.store.athena.infra.TokenAuthService;
import com.book.store.athena.model.dto.admin.FindAdminByIdDto;
import com.book.store.athena.model.dto.admin.LoginAdminDto;
import com.book.store.athena.model.dto.admin.RegisterAdminDto;
import com.book.store.athena.model.entities.User;
import com.book.store.athena.services.AdminService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/admin")
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
    protected ResponseEntity <Void> registerAdmin (@RequestBody @Valid RegisterAdminDto registerAdminDto) {

        adminService.create(registerAdminDto);

        return ResponseEntity.status(HttpStatus.CREATED).build();

    }

    @PostMapping("/login")
    protected ResponseEntity <String> loginAdmin (@RequestBody @Valid LoginAdminDto loginAdminDto) {

        var token = new UsernamePasswordAuthenticationToken(loginAdminDto.username(), loginAdminDto.password());

        var authentication = authenticationManager.authenticate(token);

        return ResponseEntity.ok(tokenAuthService.generateUserJWToken((User)authentication.getPrincipal()));

    }

    @GetMapping("/profile/{id}")
    protected  ResponseEntity <Set<FindAdminByIdDto>> adminProfile (@PathVariable Long id) {

        var profile = adminService.findAdminById(id);

        return ResponseEntity.ok().body(profile);

    }

}
