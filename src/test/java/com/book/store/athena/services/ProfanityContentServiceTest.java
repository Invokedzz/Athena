package com.book.store.athena.services;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
class ProfanityContentServiceTest {

    @MockitoBean
    private ProfanityContentService profanityContentService;

    @Test
    void lookingForTheFirstFunctionResponse_Test () {

        profanityContentService.checkProfanityLevel(Mockito.anyString());

        Mockito.verify(profanityContentService, Mockito.times(1)).checkProfanityLevel(Mockito.anyString());

    }

    @Test
    void lookingForTheSecondFunctionResponse_Test () {

        profanityContentService.checkHarmfulLinksAndUrls(Mockito.anyString());

        Mockito.verify(profanityContentService, Mockito.times(1)).checkHarmfulLinksAndUrls(Mockito.anyString());

    }

}