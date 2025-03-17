package com.book.store.athena.services;

import com.book.store.athena.model.entities.User;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
class AuthServiceTest {

    @MockitoBean
    private AuthService authService;

    @Test
    void getUserAuth_ThenReturnIt () {

        Mockito.when(authService.loadUserByUsername(Mockito.any())).thenReturn(Mockito.mock(User.class));

        authService.loadUserByUsername("admin");

        Mockito.verify(authService).loadUserByUsername(Mockito.eq("admin"));

    }

}