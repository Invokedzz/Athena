package com.book.store.athena.controllers;

import com.book.store.athena.model.dto.admin.FindAdminByIdDto;
import com.book.store.athena.model.dto.admin.LoginAdminDto;
import com.book.store.athena.model.dto.admin.RegisterAdminDto;
import com.book.store.athena.model.dto.admin.UpdateAdminDto;
import com.book.store.athena.services.AdminService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {

        this.adminService = adminService;

    }

    @PostMapping("/register")
    protected ResponseEntity <Void> registerAdmin (@RequestBody @Valid RegisterAdminDto registerAdminDto) {

        adminService.create(registerAdminDto);

        return ResponseEntity.status(HttpStatus.CREATED).build();

    }

    @PostMapping("/login")
    protected ResponseEntity <Void> loginAdmin (@RequestBody @Valid LoginAdminDto loginAdminDto) {

        return ResponseEntity.ok().body(null);

    }

    @GetMapping("/profile/{id}")
    protected  ResponseEntity <Set<FindAdminByIdDto>> adminProfile (@PathVariable Long id) {

        var profile = adminService.findAdminById(id);

        return ResponseEntity.ok().body(profile);

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
