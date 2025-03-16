package com.book.store.athena.controllers;

import com.book.store.athena.infra.TokenAuthService;
import com.book.store.athena.model.dto.client.RegisterUserDto;
import com.book.store.athena.model.dto.client.UpdateUserDto;
import com.book.store.athena.services.UserServices;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
@AutoConfigureMockMvc(addFilters = false)
@ContextConfiguration(classes = UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserServices userServices;

    @MockitoBean
    private AuthenticationManager authenticationManager;

    @MockitoBean
    private TokenAuthService tokenAuthService;

    @Test
    void registerUser_Test () throws Exception {

        RegisterUserDto registerUserDto = new RegisterUserDto("Amphitryon", "amphi@gmail.com",
                "1234567", LocalDate.parse("2004-10-12"));

        ObjectMapper objectMapper = new ObjectMapper();

        objectMapper.findAndRegisterModules();

        String userJson = objectMapper.writeValueAsString(registerUserDto);

        mockMvc.perform(post("/users/register")
                .contentType("application/json")
                .content(userJson))
                .andExpect(status().isCreated());

    }

    @Test
    void loginUser_Test () throws Exception {



    }

    @Test
    void findUserBooksById_Test () throws Exception {

        Long userId = 1L;

        var books = userServices.findUserBooksById(userId);

        ObjectMapper objectMapper = new ObjectMapper();

        String booksJson = objectMapper.writeValueAsString(books);

        mockMvc.perform(get("/users/profile/books/{id}", userId)
                .content(booksJson)
                .contentType("application/json"))
                .andExpect(status().isOk());

    }

    @Test
    void getProfileById_Test () throws Exception {

        Long userId = 1L;

        var profile = userServices.findUserById(userId);

        ObjectMapper objectMapper = new ObjectMapper();

        String profileJson = objectMapper.writeValueAsString(profile);

        mockMvc.perform(get("/users/profile/{id}", userId)
                        .contentType("application/json")
                        .content(profileJson))
                        .andExpect(status().isOk());

    }

    @Test
    void updateProfileById_Test () throws Exception {

        Long userId = 1L;

        UpdateUserDto updateUserDto = new UpdateUserDto("NorthernLight", "northern@gmail.com", "15000017");

        ObjectMapper objectMapper = new ObjectMapper();

        String updateJson = objectMapper.writeValueAsString(updateUserDto);

        mockMvc.perform(put("/users/profile/update/{id}", userId)
                        .contentType("application/json")
                        .content(updateJson))
                        .andExpect(status().isNoContent());

    }

    @Test
    void getActiveUsers_Test () throws Exception {

        var activeUsers = userServices.findAll(true);

        ObjectMapper mapper = new ObjectMapper();

        String activeUsersJson = mapper.writeValueAsString(activeUsers);

        mockMvc.perform(get("/users/actives")
                .contentType("application/json")
                .content(activeUsersJson))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(activeUsers)));

    }

    @Test
    void reactivateUser_Test () throws Exception {

        Long userId = 1L;

        mockMvc.perform(put("/users/profile/reactivate/{id}", userId)
                .contentType("application/json"))
                .andExpect(status().isNoContent());

    }

    @Test
    void disableUser_Test () throws Exception {

        Long userId = 1L;

        mockMvc.perform(delete("/users/profile/disable/{id}", userId)
                        .contentType("application/json"))
                        .andExpect(status().isNoContent());

    }

}