package com.book.store.athena.api;

import com.book.store.athena.model.dto.books.SearchBooksDTO;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
public class OpenLibraryService {

    @Value("${open_library_api}")
    private String apiUrl;

    private final AppConfig appConfig;

    public OpenLibraryService (AppConfig appConfig) {

        this.appConfig = appConfig;

    }

    public List <Object> search (SearchBooksDTO searchBooksDTO) {

        String url = apiUrl + searchBooksDTO.name();

        ObjectNode template = appConfig.getRestTemplate().getForObject(url, ObjectNode.class);

        return Collections.singletonList(template);

    }

}
