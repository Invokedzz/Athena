package com.book.store.athena.controllers;

import com.book.store.athena.model.dto.admin.LoginAdminDto;
import com.book.store.athena.model.dto.admin.RegisterAdminDto;
import com.book.store.athena.model.dto.admin.UpdateAdminDto;
import com.book.store.athena.services.AdminService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {

        this.adminService = adminService;

    }

    @PostMapping("/register")
    protected ResponseEntity <Void> registerAdmin (@RequestBody @Valid RegisterAdminDto registerAdminDto) {

        var admin = adminService.create(registerAdminDto);

        if (admin != null) {

            return ResponseEntity.status(HttpStatus.CREATED).build();

        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

    }

    @PostMapping("/login")
    protected ResponseEntity <Void> loginAdmin (@RequestBody @Valid LoginAdminDto loginAdminDto) {

        return ResponseEntity.ok().body(null);

    }

    @GetMapping("/profile/{id}")
    protected  ResponseEntity <Void> adminProfile (@PathVariable Long id) {

        return ResponseEntity.ok().body(null);

    }

    @PutMapping("/profile/edit/{id}")
    protected ResponseEntity <Void> editProfile (@PathVariable Long id, @RequestBody @Valid UpdateAdminDto updateAdminDto) {

        adminService.update(id, updateAdminDto);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

    }

    @PutMapping("/reactivate/{id}")
    protected ResponseEntity <Void> reactivateAdmin (@PathVariable Long id) {

        adminService.reactivate(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

    }

    @DeleteMapping("/disable/{id}")
    protected ResponseEntity <Void> disableAdmin (@PathVariable Long id) {

        adminService.disable(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

    }

}
