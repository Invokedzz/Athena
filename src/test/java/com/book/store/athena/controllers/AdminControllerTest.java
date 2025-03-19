package com.book.store.athena.controllers;

import com.book.store.athena.infra.TokenAuthService;
import com.book.store.athena.model.dto.admin.RegisterAdminDto;
import com.book.store.athena.model.dto.client.UpdateUserDto;
import com.book.store.athena.services.AdminService;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(AdminController.class)
@AutoConfigureMockMvc(addFilters = false)
@ContextConfiguration(classes = AdminController.class)
class AdminControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private AdminService adminService;

    @MockitoBean
    private AuthenticationManager authenticationManager;

    @MockitoBean
    private TokenAuthService tokenAuthService;

    @Test
    void registerAdmin_Test () throws Exception {

        RegisterAdminDto registerAdminDto = new RegisterAdminDto("Astraeus", "Astraeus@gmail.com",
                                "This isn't Game of Thrones, Morty.", LocalDate.parse("1999-10-02"));

        ObjectMapper objectMapper = new ObjectMapper();

        objectMapper.findAndRegisterModules();

        mockMvc.perform(post("/admin/register")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(registerAdminDto)))
                        .andExpect(status().isCreated());

    }

    @Test
    void loginAdmin_Test () throws Exception {

        var authMock = Mockito.mock(Authentication.class);

        Mockito.when(authenticationManager.authenticate(Mockito.any(UsernamePasswordAuthenticationToken.class))).thenReturn(authMock);

        String token = "random-token";

        Mockito.when(tokenAuthService.generateUserJWToken(Mockito.any())).thenReturn(token);

        mockMvc.perform(post("/admin/login")
                .contentType("application/json")
                .content("{\"username\":\"username\",\"password\":\"password\"}"))
                .andExpect(status().isOk());

    }

    @Test
    void getAdminProfileById_Test () throws Exception {

        var profile = adminService.findAdminById(1L);

        mockMvc.perform(get("/admin/profile/{id}", 1L)
                .contentType("application/json")
                .content(new ObjectMapper().writeValueAsString(profile)))
                .andExpect(status().isOk());

    }

    @Test
    void updateAdminById_Test () throws Exception {

        UpdateUserDto updateAdminDto = new UpdateUserDto("Hephaestus", "Hephaestus@gmail.com",
                                "1742216226");

        mockMvc.perform(put("/admin/profile/edit/{id}", 1L)
                        .contentType("application/json")
                        .content(new ObjectMapper().writeValueAsString(updateAdminDto)))
                        .andExpect(status().isNoContent());

    }

}