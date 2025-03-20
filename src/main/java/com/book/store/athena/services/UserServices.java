package com.book.store.athena.services;

import com.book.store.athena.exceptions.NotFoundException;
import com.book.store.athena.infra.SecurityConfig;
import com.book.store.athena.exceptions.AgeRestrictionException;
import com.book.store.athena.model.dto.client.*;
import com.book.store.athena.model.entities.User;
import com.book.store.athena.model.repository.RoleRepository;
import com.book.store.athena.model.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
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

    public void create (RegisterUserDTO registerUserDto) {

        var role = roleRepository.findById(1L);

        String userPassword = securityConfig.passwordEncoder().encode(registerUserDto.password());

        User user = new User(registerUserDto);

        user.setPassword(userPassword);

        if (role.isPresent()) {

            var obtainedRole = role.get();

            if (!isUserAgeAbove15(registerUserDto.birthDate())) throw new AgeRestrictionException("You must have more than 15 years to enter the website");

            var savedUser = userRepository.save(user);

            roleRepository.insertRole(savedUser.getId(), obtainedRole.getId());

        }

    }

    public Set <FindUserByIdDTO> findUserById (Long userId) {

        return null;

    }

    public void update (Long id, UpdateUserDTO updateUserDto) {

        var searchForUser = userRepository.findById(id);

        if (searchForUser.isPresent()) {

            var obtainedUser = searchForUser.get();

            obtainedUser.update(updateUserDto);

            userRepository.save(obtainedUser);

        }

        verifyIfUserExists(searchForUser);

    }

    public void disable (Long id) {

        var searchForUser = userRepository.findById(id);

        if (searchForUser.isPresent()) {

            var obtainedUser = searchForUser.get();

            obtainedUser.disable();

            userRepository.save(obtainedUser);

        }

        verifyIfUserExists(searchForUser);

    }

    public void reactivate (Long id) {

        var searchForUser = userRepository.findById(id);

        if (searchForUser.isPresent()) {

            User obtainedUser = searchForUser.get();

            obtainedUser.activate();

            userRepository.save(obtainedUser);

        }

        verifyIfUserExists(searchForUser);

    }

    public Set <FindAllActiveUsersDTO> findAll () {

        return userRepository.findAllUsersByActive(true, "ROLE_USER")
                .stream().map(FindAllActiveUsersDTO::new).collect(Collectors.toSet());

    }

    private void verifyIfUserExists (Optional <?> user) {

        if (user.isEmpty()) {

            throw new NotFoundException("User not found");

        }

    }

    private boolean isUserAgeAbove15 (LocalDate date) {

        LocalDate today = LocalDate.now();

        int userAge = today.getYear() - date.getYear();

        if (today.getMonthValue() < date.getMonthValue() ||
                (today.getMonthValue() == date.getMonthValue() && today.getDayOfMonth() < date.getDayOfMonth())) {

            userAge--;

        }

        return userAge >= 16;

    }

}
