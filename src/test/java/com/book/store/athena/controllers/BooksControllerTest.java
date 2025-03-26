package com.book.store.athena.controllers;

import com.book.store.athena.api.OpenLibraryService;
import com.book.store.athena.model.dto.books.UpdateBooksDTO;
import com.book.store.athena.services.BooksService;
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


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(BooksController.class)
@AutoConfigureMockMvc(addFilters = false)
@ContextConfiguration(classes = BooksController.class)
class BooksControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private BooksService booksService;

    @MockitoBean
    private OpenLibraryService openLibraryService;

    @Test
    void createBook_Test () throws Exception {

        mockMvc.perform(post("/books/create")
                .contentType("application/json").content("""
                                    {
                                       \s
                                        "name": "The Book of Bill",
                                        "author": "Alex Hirsch ",
                                        "genre":["FANTASY"],
                                        "release-date":"2024-07-23",
                                        "publisher":"JBC",
                                        "description": "The demon that terrorized Gravity Falls is back from the great beyond to finally tell his side of the story in The Book of Bill, written by none other than Bill Cipher himself. Inside, Bill sheds light on his bizarre origins, his sinister effects on human history, the Pines family's most embarrassing secrets, and the key to overthrowing the world (laid out in a handy step-by-step guide). This chaotic and beautifully illustrated tome contains baffling riddles, uncrackable ciphers, lost Journal 3 pages, ways to cheat death, the meaning of life, and a whole chapter on Silly Straws. But most importantly, The Book of Bill is deeply, deeply cursed.",
                                        "pdfPath": "https://archive.org/details/the-book-of-bill"
                                
                                    }\
                                """))
                .andExpect(status().isCreated());

    }

    @Test
    void getAllBooks_Test () throws Exception {

        var findAll = booksService.findAll();

        mockMvc.perform(get("/books/collection")
                .contentType("application/json")
                .content(new ObjectMapper().writeValueAsString(findAll)))
                .andExpect(status().isOk());

    }

    @Test
    void updateBookById_Test () throws Exception {

        UpdateBooksDTO updateBooksDto = new UpdateBooksDTO("Book1", "Author1",
                "Description1", "pdf");

        mockMvc.perform(put("/books/update/{id}", 1L)
                        .contentType("application/json")
                        .content(new ObjectMapper().writeValueAsString(updateBooksDto)))
                        .andExpect(status().isNoContent());

    }

    @Test
    void reactivateBookById_Test () throws Exception {

        mockMvc.perform(put("/books/reactivate/{id}", 1L)
                .contentType("application/json"))
                .andExpect(status().isNoContent());

    }

    @Test
    void deleteBookById_Test () throws Exception {

        mockMvc.perform(delete("/books/delete/{id}", 1L)
                .contentType("application/json"))
                .andExpect(status().isNoContent());

    }

}