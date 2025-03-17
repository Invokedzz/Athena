package com.book.store.athena.controllers;

import com.book.store.athena.model.dto.admin.RegisterAdminDto;
import com.book.store.athena.model.dto.admin.UpdateAdminDto;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
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

    @Test
    void registerAdmin_Test () throws Exception {

        RegisterAdminDto registerAdminDto = new RegisterAdminDto("Astraeus", "Astraeus@gmail.com",
                                "This isn't Game of Thrones, Morty.");

        mockMvc.perform(post("/admin/register")
                        .contentType("application/json")
                        .content(new ObjectMapper().writeValueAsString(registerAdminDto)))
                        .andExpect(status().isCreated());

    }

    @Test
    void loginAdmin_Test () throws Exception {



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

        UpdateAdminDto updateAdminDto = new UpdateAdminDto("Hephaestus@gmail.com", "Hephaestus",
                                "1742216226");

        mockMvc.perform(put("/admin/profile/edit/{id}", 1L)
                        .contentType("application/json")
                        .content(new ObjectMapper().writeValueAsString(updateAdminDto)))
                        .andExpect(status().isNoContent());

    }

    @Test
    void reactivateAdmin_Test () throws Exception {

        mockMvc.perform(put("/admin/reactivate/{id}", 1L)
                        .contentType("application/json"))
                        .andExpect(status().isNoContent());

    }

    @Test
    void disableAdmin_Test () throws Exception {

        mockMvc.perform(delete("/admin/disable/{id}", 1L)
                .contentType("application/json"))
                .andExpect(status().isNoContent());

    }

}