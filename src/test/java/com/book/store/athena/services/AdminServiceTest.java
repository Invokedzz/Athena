package com.book.store.athena.services;

import com.book.store.athena.model.dto.admin.RegisterAdminDto;
import com.book.store.athena.model.dto.client.UpdateUserDto;
import com.book.store.athena.model.entities.User;
import com.book.store.athena.model.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;

@SpringBootTest
@RunWith(SpringRunner.class)
class AdminServiceTest {

    @MockitoBean
    private AdminService adminService;

    @MockitoBean
    private UserRepository userRepository;

    @Test
    void registerAdmin_ThenReturnIt () {

        RegisterAdminDto registerAdminDto = new RegisterAdminDto("Picasso", "Picasso@gmail.com",
                "pic123", LocalDate.parse("1999-10-02"));

        User admin = new User(registerAdminDto);

        Mockito.when(adminService.create(Mockito.any(RegisterAdminDto.class))).thenReturn(admin);

        adminService.create(registerAdminDto);

        Assertions.assertThat(userRepository.findById(admin.getId())).isNotNull();

        Assertions.assertThat(registerAdminDto.username())
                        .isEqualTo("Picasso")
                        .isNotNull();

        Assertions.assertThat(registerAdminDto.email())
                        .isEqualTo("Picasso@gmail.com")
                        .isNotNull();

        Assertions.assertThat(registerAdminDto.password())
                        .isEqualTo("pic123")
                        .isNotNull();

        Mockito.verify(adminService, Mockito.times(1)).create(Mockito.any(RegisterAdminDto.class));

    }

    @Test
    void updateAdmin_ThenReturnIt () {

        UpdateUserDto updateAdminDto = new UpdateUserDto("Hayden Jast", "hayden@gmail.com", "123456");

        Mockito.when(adminService.update(Mockito.eq(1L), Mockito.eq(updateAdminDto))).thenReturn(new User());

        adminService.update(1L, updateAdminDto);

        Assertions.assertThat(updateAdminDto.username())
                        .isEqualTo("Hayden Jast")
                        .isNotNull();

        Assertions.assertThat(updateAdminDto.email())
                        .isEqualTo("hayden@gmail.com")
                        .isNotNull();

        Assertions.assertThat(updateAdminDto.password())
                        .isEqualTo("123456")
                        .isNotNull();

        Mockito.verify(adminService, Mockito.times(1)).update(Mockito.eq(1L), Mockito.eq(updateAdminDto));

    }

    @Test
    void reactivateAdmin_ThenReturnIt () {

        Mockito.when(adminService.reactivate(Mockito.any())).thenReturn(new User());

        adminService.reactivate(Mockito.any());

        Mockito.verify(adminService, Mockito.times(1)).reactivate(Mockito.any());

    }

    @Test
    void disableAdmin_ThenReturnIt () {

        Mockito.when(adminService.disable(Mockito.any())).thenReturn(new User());

        adminService.disable(Mockito.any());

        Mockito.verify(adminService, Mockito.times(1)).disable(Mockito.any());

    }

}