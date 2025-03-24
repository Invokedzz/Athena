package com.book.store.athena.controllers;

import com.book.store.athena.infra.TokenAuthService;
import com.book.store.athena.model.dto.client.RegisterUserDTO;
import com.book.store.athena.services.AdminService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
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
    private TokenAuthService tokenAuthService;

    @Test
    void registerAdmin_Test () throws Exception {

        RegisterUserDTO registerAdminDto = new RegisterUserDTO("Astraeus", "Astraeus@gmail.com",
                                "This isn't Game of Thrones, Morty.", LocalDate.parse("1999-10-02"));

        ObjectMapper objectMapper = new ObjectMapper();

        objectMapper.findAndRegisterModules();

        mockMvc.perform(post("/register-as-administrator")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(registerAdminDto)))
                        .andExpect(status().isCreated());

    }

    @Test
    void findAllActiveAdmins_Test () throws Exception {

        var admins = adminService.findAll();

        mockMvc.perform(get("/all-administrators")
                .contentType("application/json")
                .content(new ObjectMapper().writeValueAsString(admins)))
                .andExpect(status().isOk())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(admins)));

    }

}