package com.book.store.athena.services;

import com.book.store.athena.model.dto.client.FindAllActiveUsersDto;
import com.book.store.athena.model.dto.client.FindUserByIdDto;
import com.book.store.athena.model.dto.client.RegisterUserDto;
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

        RegisterUserDto registerUserDto = new RegisterUserDto("Shinji", "Ikari@gmail.com",
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

    @Test
    void findUserBooksById_ReturnUserBooks () {

        Mockito.when(userRepository.findById(1L))
                .thenReturn(Optional.of(new User()));

        Mockito.when(userServices.findUserBooksById(1L)).thenReturn(new ArrayList<>());

        userServices.findUserBooksById(1L);

        userRepository.findById(1L);

        Mockito.verify(userRepository, Mockito.times(1)).findById(1L);

        Mockito.verify(userServices, Mockito.times(1)).findUserBooksById(1L);

    }

    @Test
    void findUserById_ReturnUser () {

        Mockito.when(userRepository.findUserById(1L))
                .thenReturn(List.of(new User()));

        FindUserByIdDto user = new FindUserByIdDto(1L, "Asuka",
                                            "asuka@gmail.com", LocalDate.now());

        Mockito.when(userServices.findUserById(1L)).thenReturn(List.of(user));

        userRepository.findUserById(1L);

        List <FindUserByIdDto> list = userServices.findUserById(1L);

        Mockito.verify(userRepository, Mockito.times(1)).findUserById(1L);

        Mockito.verify(userServices, Mockito.times(1)).findUserById(1L);

        Assertions.assertThat(list.size()).isEqualTo(1);

        Assertions.assertThat(list.getFirst()).isNotNull();

    }

    @Test
    void findAllUsersByActive_ThenReturnThem () {

        FindAllActiveUsersDto activeUsers = new FindAllActiveUsersDto(1L, "Shinji Ika",
                                        "shinji@gmail.com", LocalDate.now());

        Mockito.when(userServices.findAll(Mockito.anyBoolean())).thenReturn(List.of(activeUsers));

        userServices.findAll(Mockito.anyBoolean());

        Mockito.verify(userServices, Mockito.times(1)).findAll(Mockito.anyBoolean());

    }

}