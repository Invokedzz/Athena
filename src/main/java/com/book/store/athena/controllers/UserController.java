package com.book.store.athena.controllers;

import com.book.store.athena.model.dto.client.*;
import com.book.store.athena.model.entities.Favorite;
import com.book.store.athena.services.UserServices;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserServices userServices;

    private final AuthenticationManager authenticationManager;

    public UserController(UserServices userServices, AuthenticationManager authenticationManager) {

        this.userServices = userServices;

        this.authenticationManager = authenticationManager;

    }

    @PostMapping("/register") // user
    protected ResponseEntity <Void> register (@RequestBody @Valid RegisterUserDto registerUserDto) {

        if (!userServices.isUserAgeAbove15(registerUserDto.birthDate())) return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

        userServices.createUser(registerUserDto);

        return ResponseEntity.status(HttpStatus.CREATED).build();

    }

    @PostMapping("/login") // user
    protected ResponseEntity<Authentication> login (@RequestBody @Valid UserLoginDto userLoginDto) {

        var token = new UsernamePasswordAuthenticationToken(userLoginDto.username(), userLoginDto.password());

        var authToken = authenticationManager.authenticate(token);

        return ResponseEntity.ok(authToken);

    }

    @GetMapping("/profile/books/{id}") // user
    protected ResponseEntity <Set<FindUserBooksByIdDto>> findAllFavorites (@PathVariable Long id) {

        var favorites = userServices.findUserBooksById(id);

        System.out.println(favorites);

        return ResponseEntity.ok(favorites);

    }

    @GetMapping("/profile/{id}") // user
    protected ResponseEntity<Set<FindUserByIdDto>> findUserBooksById (@PathVariable Long id) {

        var user = userServices.findUserById(id);

        if (user.isEmpty()) return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        return ResponseEntity.ok(user);

    }

    @GetMapping("/{id}")
    protected ResponseEntity<Set<FindUserBooksByIdDto>> findUserBookById (@PathVariable Long id) {

        var user = userServices.findUserBooksById(id);

        return ResponseEntity.ok(user);

    }

    @PutMapping("/profile/update/{id}") // user
    protected ResponseEntity <Void> updateUserById (@PathVariable Long id, @RequestBody @Valid UpdateUserDto updateUserDto) {

        var user = userServices.updateUser(id, updateUserDto);

        if (user == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

    }

    @GetMapping("/actives") // admin
    protected ResponseEntity <Set<FindAllActiveUsersDto>> findAllUsers () {

        Set <FindAllActiveUsersDto> userList = userServices.findAll(true);

        return ResponseEntity.ok(userList);

    }

    @PutMapping("/profile/reactivate/{id}")
    protected ResponseEntity <Void> reactivate (@PathVariable Long id) {

        var user = userServices.reactivateUser(id);

        if (user == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

    }

    @DeleteMapping("/profile/disable/{id}")
    protected ResponseEntity <Void> disable (@PathVariable Long id) {

        var user = userServices.disableUser(id);

        if (user == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

    }

}
