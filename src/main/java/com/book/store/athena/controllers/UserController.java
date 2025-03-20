package com.book.store.athena.controllers;

import com.book.store.athena.infra.TokenAuthService;
import com.book.store.athena.model.dto.client.*;
import com.book.store.athena.model.entities.User;
import com.book.store.athena.services.UserServices;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/users")
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
    protected ResponseEntity <Void> register (@RequestBody @Valid RegisterUserDto registerUserDto) {

        userServices.create(registerUserDto);

        return ResponseEntity.status(HttpStatus.CREATED).build();

    }

    @PostMapping("/login")
    protected ResponseEntity<String> login (@RequestBody @Valid UserLoginDto userLoginDto) {

        var token = new UsernamePasswordAuthenticationToken(userLoginDto.username(), userLoginDto.password());

        var authToken = authenticationManager.authenticate(token);

        return ResponseEntity.ok(tokenAuthService.generateUserJWToken((User)authToken.getPrincipal()));

    }

    @GetMapping("/profile/books/{id}")
    protected ResponseEntity <Set<FindUserBooksByIdDto>> findAllFavorites (@PathVariable Long id) {

        var favorites = userServices.findUserBooksById(id);

        return ResponseEntity.status(HttpStatus.OK).body(favorites);

    }

    @GetMapping("/profile/{id}")
    protected ResponseEntity<Set<FindUserByIdDto>> findUserBooksById (@PathVariable Long id) {

        var profile = userServices.findUserById(id);

        return ResponseEntity.status(HttpStatus.OK).body(profile);

    }

    @PutMapping("/profile/update/{id}")
    protected ResponseEntity <Void> updateUserById (@PathVariable Long id, @RequestBody @Valid UpdateUserDto updateUserDto) {

        userServices.update(id, updateUserDto);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

    }

    @GetMapping("/all")
    protected ResponseEntity <Set<FindAllActiveUsersDto>> findAllUsers () {

        var users = userServices.findAll();

        return ResponseEntity.status(HttpStatus.OK).body(users);

    }

    @PutMapping("/profile/reactivate/{id}")
    protected ResponseEntity <Void> reactivate (@PathVariable Long id) {

        userServices.reactivate(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

    }

    @DeleteMapping("/profile/disable/{id}")
    protected ResponseEntity <Void> disable (@PathVariable Long id) {

        userServices.disable(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

    }

}
