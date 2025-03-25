package com.book.store.athena.services;

import com.book.store.athena.exceptions.NotFoundException;
import com.book.store.athena.infra.SecurityConfig;
import com.book.store.athena.exceptions.AgeRestrictionException;
import com.book.store.athena.model.dto.client.*;
import com.book.store.athena.model.entities.User;
import com.book.store.athena.model.repository.RoleRepository;
import com.book.store.athena.model.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserServices {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final SecurityConfig securityConfig;

    private final ProfanityContentService profanityContentService;

    public UserServices (UserRepository userRepository, RoleRepository roleRepository, SecurityConfig securityConfig, ProfanityContentService profanityContentService) {

        this.userRepository = userRepository;

        this.roleRepository = roleRepository;

        this.securityConfig = securityConfig;

        this.profanityContentService = profanityContentService;

    }

    @Transactional
    public void create (RegisterUserDTO registerUserDto) {

        var role = roleRepository.findById(1L);

        String userPassword = securityConfig.passwordEncoder().encode(registerUserDto.password());

        User user = new User(registerUserDto);

        user.setPassword(userPassword);

        if (role.isPresent()) {

            var obtainedRole = role.get();

            profanityContentService.checkProfanityLevel(registerUserDto.toString());

            if (!isUserAgeAbove15(registerUserDto.birthDate())) throw new AgeRestrictionException("You must have more than 15 years to enter the website");

            var savedUser = userRepository.save(user);

            roleRepository.insertRole(savedUser.getId(), obtainedRole.getId());

        }

    }

    public Set <FindUserByIdDTO> findUserById (Long userId) {

        var user = userRepository.findUserAccordingToRole(userId, true, "ROLE_USER")
                .stream().map(FindUserByIdDTO::new).collect(Collectors.toSet());

        verifyIfCollectionOfUsersExist(user);

        return user;

    }

    @Transactional
    public void update (Long id, UpdateUserDTO updateUserDto) {

        var searchForUser = userRepository.findById(id);

        if (searchForUser.isPresent()) {

            var obtainedUser = searchForUser.get();

            obtainedUser.update(updateUserDto);

            profanityContentService.checkProfanityLevel(obtainedUser.getUsername());

            userRepository.save(obtainedUser);

        }

        verifyIfUserExists(searchForUser);

    }

    @Transactional
    public void disable (Long id) {

        var searchForUser = userRepository.findById(id);

        if (searchForUser.isPresent()) {

            var obtainedUser = searchForUser.get();

            obtainedUser.disable();

            userRepository.save(obtainedUser);

        }

        verifyIfUserExists(searchForUser);

    }

    @Transactional
    public void reactivate (String email) {

        var searchForUser = userRepository.findUserByEmail(email);

        if (searchForUser == null) {

            throw new NotFoundException("Sorry. We weren't able to find any user with that email!");

        }

        searchForUser.activate();

        userRepository.save(searchForUser);

    }

    public Set <FindAllActiveUsersDTO> findAll () {

        return userRepository.findAllUsersByActive(true, "ROLE_USER")
                .stream().map(FindAllActiveUsersDTO::new).collect(Collectors.toSet());

    }

    private void verifyIfUserExists (Optional <?> users) {

        if (users.isEmpty()) {

            throw new NotFoundException("User not found");

        }

    }

    private void verifyIfCollectionOfUsersExist (Set <?> users) {

        if (users.isEmpty()) {

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
