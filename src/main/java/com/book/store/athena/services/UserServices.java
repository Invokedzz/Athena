package com.book.store.athena.services;

import com.book.store.athena.model.dto.client.FindUserBooksByIdDto;
import com.book.store.athena.model.dto.client.FindUserByIdDto;
import com.book.store.athena.model.dto.client.RegisterUserDto;
import com.book.store.athena.model.entities.User;
import com.book.store.athena.model.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.mindrot.jbcrypt.BCrypt;

import java.time.LocalDate;
import java.util.List;

@Service
public class UserServices {

    @Autowired
    private UserRepository userRepository;

    public void createUser (RegisterUserDto registerUserDto) {

        String userPassword = BCrypt.hashpw(registerUserDto.password(), BCrypt.gensalt());

        User user = new User(registerUserDto);

        user.setPassword(userPassword);

        userRepository.save(user);

    }

    public List <FindUserBooksByIdDto> findUserBooksById (Long userId) {

        return userRepository.findById(userId).stream().map(FindUserBooksByIdDto::new).toList();

    }

    public List <FindUserByIdDto> findUserById (Long userId) {

        return userRepository.findUserById(userId).stream().map(FindUserByIdDto::new).toList();

    }

    public boolean isUserAgeAbove15 (LocalDate date) {

        LocalDate today = LocalDate.now();

        int userAge = today.getYear() - date.getYear();

        if (today.getMonthValue() < date.getMonthValue() || today.getMonthValue() == date.getMonthValue()) userAge--;

        return userAge >= 16;

    }

}
