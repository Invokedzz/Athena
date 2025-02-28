package com.book.store.athena.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hi")
public class Hello {

    @GetMapping
    public String greetings () {

        return "Hello, World!";

    }

}
