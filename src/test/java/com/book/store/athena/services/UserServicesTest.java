package com.book.store.athena.services;

import com.book.store.athena.model.dto.client.FindUserByIdDTO;
import com.book.store.athena.model.dto.client.RegisterUserDTO;
import com.book.store.athena.model.entities.Books;
import com.book.store.athena.model.entities.User;
import com.book.store.athena.model.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mindrot.jbcrypt.BCrypt;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.*;

import org.assertj.core.api.Assertions;

@SpringBootTest
@RunWith(SpringRunner.class)
class UserServicesTest {

    @MockitoBean
    private UserServices userServices;

    @MockitoBean
    private UserRepository userRepository;

    @Test
    void createUser_Save_ReturnUser () {

        RegisterUserDTO registerUserDto = new RegisterUserDTO("Shinji", "Ikari@gmail.com",
                                   "123456", LocalDate.now());

        String hashPassword = BCrypt.hashpw("123456", BCrypt.gensalt());

        User user = new User(registerUserDto);

        Mockito.when(userRepository.save(Mockito.any(User.class)))
                    .thenReturn(user);

        Assertions.assertThat(registerUserDto.username())
                    .isEqualTo("Shinji");

        Assertions.assertThat(registerUserDto.email())
                    .isEqualTo("Ikari@gmail.com");

        Assertions.assertThat(registerUserDto.password())
                    .isEqualTo("123456");

        Assertions.assertThat(BCrypt.checkpw("123456", hashPassword)).isTrue();

    }


}