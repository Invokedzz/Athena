package com.book.store.athena.controllers;


import com.book.store.athena.model.dto.favorite.RequestFavoriteDto;
import com.book.store.athena.services.FavoriteServices;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(FavoriteController.class)
@AutoConfigureMockMvc(addFilters = false)
@ContextConfiguration(classes = FavoriteController.class)
class FavoriteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private FavoriteServices favoriteServices;

    @Test
    void insertFavoriteRelation_Test () throws Exception {

        RequestFavoriteDto favoriteDto = new RequestFavoriteDto(1L, 1L);

        mockMvc.perform(post("/favorites/insert")
                        .content(new ObjectMapper().writeValueAsString(favoriteDto))
                        .contentType("application/json"))
                        .andExpect(status().isCreated());

    }

    @Test
    void displayAllFavorites_Test () throws Exception {

        var favorites = favoriteServices.findFavoriteByActive(true);

        mockMvc.perform(get("/favorites/display")
                        .contentType("application/json")
                        .content(new ObjectMapper().writeValueAsString(favorites)))
                        .andExpect(status().isOk());

    }

    @Test
    void favoriteReactivate_Test () throws Exception {

        mockMvc.perform(put("/favorites/reactivate/{id}", 1L)
                    .contentType("application/json"))
                    .andExpect(status().isNoContent());

    }

    @Test
    void favoriteDisable_Test () throws Exception {

        mockMvc.perform(delete("/favorites/disable/{id}", 1L)
                        .contentType("application/json"))
                        .andExpect(status().isNoContent());

    }


}