package com.book.store.athena.services;

import com.book.store.athena.model.dto.client.FindUserBooksByIdDto;
import com.book.store.athena.model.dto.client.FindUserByIdDto;
import com.book.store.athena.model.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServices {

    @Autowired
    private UserRepository userRepository;

    public List <FindUserBooksByIdDto> findUserBooksById (Long userId) {

        return userRepository.findById(userId).stream().map(FindUserBooksByIdDto::new).toList();

    }

    public List <FindUserByIdDto> findUserById (Long userId) {

        return userRepository.findUserById(userId).stream().map(FindUserByIdDto::new).toList();

    }

}
