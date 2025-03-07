package com.book.store.athena.controllers;

import com.book.store.athena.model.dto.client.*;
import com.book.store.athena.model.entities.User;
import com.book.store.athena.services.UserServices;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserServices userServices;

    @PostMapping("/register")
    protected ResponseEntity <Void> register (@RequestBody @Valid RegisterUserDto registerUserDto) {

        if (!userServices.isUserAgeAbove15(registerUserDto.birthDate())) return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

        userServices.createUser(registerUserDto);

        return ResponseEntity.status(HttpStatus.CREATED).build();

    }

    @GetMapping("/profile/books/{id}")
    protected ResponseEntity<List<FindUserBooksByIdDto>> findAllFavorites (@PathVariable Long id) {

        var favorites = userServices.findUserBooksById(id);

        if (favorites.isEmpty()) return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        return ResponseEntity.ok(favorites);

    }

    @GetMapping("/profile/{id}")
    protected ResponseEntity<List<FindUserByIdDto>> findUserBooksById (@PathVariable Long id) {

        var user = userServices.findUserById(id);

        if (user.isEmpty()) return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        return ResponseEntity.ok(user);

    }

    @PutMapping("/profile/update/{id}")
    protected ResponseEntity <Void> updateUserById (@PathVariable Long id, @RequestBody @Valid UpdateUserDto updateUserDto) {

        var user = userServices.updateUser(id, updateUserDto);

        if (user == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

    }

    @GetMapping("/actives")
    protected ResponseEntity <List<FindAllActiveUsersDto>> findAllUsers () {

        List <FindAllActiveUsersDto> userList = userServices.findAll(true);

        return ResponseEntity.ok(userList);

    }

    @PutMapping("/reactivate/{id}")
    protected ResponseEntity <Void> reactivate (@PathVariable Long id) {

        var user = userServices.reactivateUser(id);

        if (user == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

    }

    @DeleteMapping("/disable/{id}")
    protected ResponseEntity <Void> disable (@PathVariable Long id) {

        var user = userServices.disableUser(id);

        if (user == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

    }

}
