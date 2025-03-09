package com.book.store.athena.services;

import com.book.store.athena.infra.SecurityConfig;
import com.book.store.athena.model.dto.client.*;
import com.book.store.athena.model.entities.Role;
import com.book.store.athena.model.entities.User;
import com.book.store.athena.model.repository.RoleRepository;
import com.book.store.athena.model.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServices {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final SecurityConfig securityConfig;

    public UserServices(UserRepository userRepository, RoleRepository roleRepository, SecurityConfig securityConfig) {

        this.userRepository = userRepository;

        this.roleRepository = roleRepository;

        this.securityConfig = securityConfig;

    }

    public void createUser (RegisterUserDto registerUserDto) {

        var role = roleRepository.findById(1L);

        String userPassword = securityConfig.passwordEncoder().encode(registerUserDto.password());

        User user = new User(registerUserDto);

        user.setPassword(userPassword);

        var savedUser = userRepository.save(user);

        if (role.isPresent()) {

            var obtainedRole = role.get();

            roleRepository.insertUserRole(savedUser.getId(), obtainedRole.getId());

        }

    }

    public Set <FindUserBooksByIdDto> findUserBooksById (Long userId) {

        return userRepository.findById(userId).stream().map(FindUserBooksByIdDto::new).collect(Collectors.toSet());

    }

    public Set <FindUserByIdDto> findUserById (Long userId) {

        return userRepository.findUserById(userId).stream().map(FindUserByIdDto::new).collect(Collectors.toSet());

    }

    public User updateUser (Long id, UpdateUserDto updateUserDto) {

        var searchForUser = userRepository.findById(id);

        if (searchForUser.isPresent()) {

            var obtainedUser = searchForUser.get();

            obtainedUser.update(updateUserDto);

            userRepository.save(obtainedUser);

            return obtainedUser;

        }

        return null;

    }

    public User disableUser (Long id) {

        var searchForUser = userRepository.findById(id);

        if (searchForUser.isPresent()) {

            var obtainedUser = searchForUser.get();

            obtainedUser.disableAccount();

            userRepository.save(obtainedUser);

            return obtainedUser;

        }

        return null;

    }

    public User reactivateUser (Long id) {

        var searchForUser = userRepository.findById(id);

        if (searchForUser.isPresent()) {

            var obtainedUser = searchForUser.get();

            obtainedUser.activateAccount();

            userRepository.save(obtainedUser);

            return obtainedUser;

        }

        return null;

    }

    public Set <FindAllActiveUsersDto> findAll (Boolean active) {

        return userRepository.findAllByActive(active).stream().map(FindAllActiveUsersDto::new).collect(Collectors.toSet());

    }

    public boolean isUserAgeAbove15 (LocalDate date) {

        LocalDate today = LocalDate.now();

        int userAge = today.getYear() - date.getYear();

        if (today.getMonthValue() < date.getMonthValue() || today.getMonthValue() == date.getMonthValue()) userAge--;

        return userAge >= 16;

    }

}
