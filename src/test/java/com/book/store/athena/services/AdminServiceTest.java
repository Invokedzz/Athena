package com.book.store.athena.services;

import com.book.store.athena.model.dto.client.FindAllActiveUsersDTO;
import com.book.store.athena.model.dto.client.FindUserByIdDTO;
import com.book.store.athena.model.dto.client.RegisterUserDTO;
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
import java.util.Optional;
import java.util.Set;

@SpringBootTest
@RunWith(SpringRunner.class)
class AdminServiceTest {

    @MockitoBean
    private AdminService adminService;

    @MockitoBean
    private UserRepository userRepository;

    @Test
    void registerAdmin_ThenReturnIt () {

        RegisterUserDTO registerAdminDto = new RegisterUserDTO("Picasso", "Picasso@gmail.com",
                "pic123", LocalDate.parse("1999-10-02"));

        User admin = new User(registerAdminDto);

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

        Mockito.verify(adminService, Mockito.times(1)).create(Mockito.any(RegisterUserDTO.class));

    }

    @Test
    void findAdminById_ThenReturnIt () {

        FindUserByIdDTO user = new FindUserByIdDTO(1L, "Bowser",
                "Bowser@gmail.com", LocalDate.parse("1999-10-02"));

        Mockito.when(adminService.findAdminById(Mockito.anyLong())).thenReturn(Optional.of(user));

        adminService.findAdminById(1L);

        Assertions.assertThat(user.username())
                  .isEqualTo("Bowser")
                  .isNotNull();

        Assertions.assertThat(user.email())
                  .isEqualTo("Bowser@gmail.com")
                  .isNotNull();

        Assertions.assertThat(user.birthDate())
                  .isEqualTo(LocalDate.parse("1999-10-02"))
                  .hasDayOfMonth(2);

        Mockito.verify(adminService, Mockito.times(1)).findAdminById(Mockito.anyLong());

    }

    @Test
    void findAllAdmins_ThenReturnThem () {

        FindAllActiveUsersDTO activeUsers = new FindAllActiveUsersDTO(1L, "Waluigi",
                "Waluigi@gmail.com", LocalDate.parse("1999-10-02"));

        Mockito.when(adminService.findAll()).thenReturn(Set.of(activeUsers));

        adminService.findAll();

        Assertions.assertThat(activeUsers.email())
                .isEqualTo("Waluigi@gmail.com")
                .isNotNull();

        Assertions.assertThat(activeUsers.username())
                .isEqualTo("Waluigi")
                .isNotNull();

        Assertions.assertThat(activeUsers.birthDate())
                .isEqualTo(LocalDate.parse("1999-10-02"))
                .hasDayOfMonth(2);

        Mockito.verify(adminService, Mockito.times(1)).findAll();

    }

}