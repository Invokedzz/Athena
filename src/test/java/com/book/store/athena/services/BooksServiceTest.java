package com.book.store.athena.services;

import com.book.store.athena.model.dto.books.FindAllBooksDto;
import com.book.store.athena.model.dto.books.UpdateBooksDto;
import com.book.store.athena.model.entities.Books;
import com.book.store.athena.model.repository.BooksRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
class BooksServiceTest {

    @MockitoBean
    private BooksRepository booksRepository;

    @MockitoBean
    private BooksService booksService;

    @Test
    void saveBook_ThenReturnSavedBook() {

        Mockito.when(booksRepository.save(Mockito.any(Books.class))).thenReturn(new Books());

        booksRepository.save(new Books());

        Mockito.verify(booksRepository, Mockito.times(1)).save(Mockito.any(Books.class));

    }

    @Test
    void findAllByActive_ThenReturnAllActiveBooks() {

        Mockito.when(booksRepository.findAllByActive(true)).thenReturn(List.of(new Books()));

        List <FindAllBooksDto> findAllBooksDtoList = booksService.findAll();

        Mockito.when(booksService.findAll()).thenReturn(findAllBooksDtoList);

        booksRepository.findAllByActive(true);

        Mockito.verify(booksRepository, Mockito.times(1)).findAllByActive(true);

        Mockito.verify(booksService, Mockito.times(1)).findAll();

    }

    @Test
    void updateBook_SearchForId_ThenReturnUpdatedBook() {

        Mockito.when(booksRepository.save(Mockito.any(Books.class))).thenReturn(new Books());

        UpdateBooksDto updateBooksDto = new UpdateBooksDto("Book Title", "Author Title",
                                                        "Lorem ipsum dolor sit amet", "text");

        booksService.updateById(1L, updateBooksDto);

        Assertions.assertThat(updateBooksDto.name())
                    .isNotNull().isNotEmpty()
                    .isEqualTo("Book Title");

        Assertions.assertThat(updateBooksDto.author())
                    .isNotNull().isNotEmpty()
                    .isEqualTo("Author Title");

        Assertions.assertThat(updateBooksDto.description())
                    .isNotNull().isNotEmpty()
                    .isEqualTo("Lorem ipsum dolor sit amet");

        Assertions.assertThat(updateBooksDto.pdfPath())
                    .isNotNull().isNotEmpty()
                    .isEqualTo("text");

        Mockito.verify(booksService, Mockito.times(1))
                    .updateById(Mockito.anyLong(), Mockito.any());

    }

}