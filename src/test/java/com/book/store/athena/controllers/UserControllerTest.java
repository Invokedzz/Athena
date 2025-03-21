package com.book.store.athena.controllers;

import com.book.store.athena.infra.TokenAuthService;
import com.book.store.athena.model.dto.client.RegisterUserDTO;
import com.book.store.athena.model.dto.client.UpdateUserDTO;
import com.book.store.athena.services.UserServices;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
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

        RegisterUserDTO registerUserDto = new RegisterUserDTO("Amphitryon", "amphi@gmail.com",
                "1234567", LocalDate.parse("2004-10-12"));

        ObjectMapper objectMapper = new ObjectMapper();

        objectMapper.findAndRegisterModules();

        String userJson = objectMapper.writeValueAsString(registerUserDto);

        mockMvc.perform(post("/register")
                .contentType("application/json")
                .content(userJson))
                .andExpect(status().isCreated());

    }

    @Test
    void loginUser_Test () throws Exception {

        var mockAuthentication = Mockito.mock(Authentication.class);

        Mockito.when(authenticationManager.authenticate(Mockito.any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(mockAuthentication);

        String mockJwtToken = "mocked-jwt-token";

        Mockito.when(tokenAuthService.generateUserJWToken(Mockito.any())).thenReturn(mockJwtToken);

        mockMvc.perform(post("/login")
                        .contentType("application/json")
                        .content("{\"username\":\"username\",\"password\":\"password\"}"))
                        .andExpect(status().isOk());

    }

    @Test
    void getProfileById_Test () throws Exception {

        var profile = userServices.findUserById(1L);

        mockMvc.perform(get("/profile/{id}", 1L)
                        .contentType("application/json")
                        .content(new ObjectMapper().writeValueAsString(profile)))
                        .andExpect(status().isOk());

    }

    @Test
    void updateProfileById_Test () throws Exception {

        UpdateUserDTO updateUserDto = new UpdateUserDTO("NorthernLight", "northern@gmail.com", "15000017");

        mockMvc.perform(put("/update-profile/{id}", 1L)
                        .contentType("application/json")
                        .content(new ObjectMapper().writeValueAsString(updateUserDto)))
                        .andExpect(status().isNoContent());

    }

    @Test
    void findAllActiveUsers_Test () throws Exception {

        var activeUsers = userServices.findAll();

        mockMvc.perform(get("/all-users")
                        .contentType("application/json")
                        .content(new ObjectMapper().writeValueAsString(activeUsers)))
                .andExpect(status().isOk())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(activeUsers)));

    }

    @Test
    void reactivateUser_Test () throws Exception {

        mockMvc.perform(put("/profile/reactivate/{id}", 1L)
                .contentType("application/json"))
                .andExpect(status().isNoContent());

    }

    @Test
    void disableUser_Test () throws Exception {

        mockMvc.perform(delete("/profile/disable/{id}", 1L)
                        .contentType("application/json"))
                        .andExpect(status().isNoContent());

    }

}