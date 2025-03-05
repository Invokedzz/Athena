package com.book.store.athena.controllers;

import com.book.store.athena.model.dto.client.FindUserBooksByIdDto;
import com.book.store.athena.model.dto.client.FindUserByIdDto;
import com.book.store.athena.services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserServices userServices;

    @GetMapping("/display/{id}")
    protected ResponseEntity<List<FindUserBooksByIdDto>> findAllFavorites (@PathVariable Long id) {

        var favorites = userServices.findUserBooksById(id);

        return ResponseEntity.ok(favorites);

    }

    @GetMapping("/userprofile/{id}")
    protected ResponseEntity<List<FindUserByIdDto>> findUserBooksById (@PathVariable Long id) {

        var user = userServices.findUserById(id);

        return ResponseEntity.ok(user);

    }

}
