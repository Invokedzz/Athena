package com.book.store.athena.controllers;

import com.book.store.athena.exceptions.ForbiddenRequestException;
import com.book.store.athena.infra.TokenAuthService;
import com.book.store.athena.model.dto.client.*;
import com.book.store.athena.model.entities.User;
import com.book.store.athena.services.UserServices;
import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
public class UserController {

    private final UserServices userServices;

    private final AuthenticationManager authenticationManager;

    private final TokenAuthService tokenAuthService;

    public UserController(UserServices userServices, AuthenticationManager authenticationManager, TokenAuthService tokenAuthService) {

        this.userServices = userServices;

        this.authenticationManager = authenticationManager;

        this.tokenAuthService = tokenAuthService;

    }

    @PostMapping("/register")
    protected ResponseEntity <Void> register (@RequestBody @Valid RegisterUserDTO registerUserDto) {

        userServices.create(registerUserDto);

        return ResponseEntity.status(HttpStatus.CREATED).build();

    }

    @PostMapping("/login")
    protected ResponseEntity <TokenDataDTO> login (@RequestBody @Valid UserLoginDTO userLoginDto) {

        var searchForUser = new UsernamePasswordAuthenticationToken(userLoginDto.username(), userLoginDto.password());

        var authenticateToken = authenticationManager.authenticate(searchForUser);

        var generateToken = tokenAuthService.generateUserJWToken((User)authenticateToken.getPrincipal());

        if (((User) authenticateToken.getPrincipal()).getActive().equals(false)) {

            throw new ForbiddenRequestException("This account is disabled. Please try again.");

        }

        return ResponseEntity.ok(new TokenDataDTO(generateToken));

    }

    @GetMapping("/profile/{id}")
    protected ResponseEntity<Set<FindUserByIdDTO>> findUserBooksById (@PathVariable Long id, @RequestHeader HttpHeaders headers) {

        tokenAuthService.validateUserByToken(headers, id);

        var profile = userServices.findUserById(id);

        return ResponseEntity.status(HttpStatus.OK).body(profile);

    }

    @PutMapping("/update-profile/{id}")
    protected ResponseEntity <Void> updateUserById (@PathVariable Long id, @RequestBody @Valid UpdateUserDTO updateUserDto, @RequestHeader HttpHeaders headers) {

        tokenAuthService.validateUserByToken(headers, id);

        userServices.update(id, updateUserDto);

        SecurityContextHolder.clearContext();

        return ResponseEntity.status(HttpStatus.NO_CONTENT).header(HttpHeaders.LOCATION, "/login").build();

    }

    @GetMapping("/all-users")
    protected ResponseEntity <Set<FindAllActiveUsersDTO>> findAllUsers () {

        var users = userServices.findAll();

        return ResponseEntity.status(HttpStatus.OK).body(users);

    }

    @PutMapping("/reactivate-account")
    protected ResponseEntity <Void> reactivate (@RequestBody @Valid ReactivateUserDTO reactivateUserDto) {

        userServices.reactivate(reactivateUserDto.email());

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

    }

    @DeleteMapping("/profile/disable/{id}")
    protected ResponseEntity <Void> disable (@RequestHeader HttpHeaders headers, @PathVariable Long id) {

        tokenAuthService.validateUserByToken(headers, id);

        userServices.disable(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

    }

}
