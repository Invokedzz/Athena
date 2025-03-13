package com.book.store.athena.infra.aspect;

import com.book.store.athena.model.dto.client.UpdateUserDto;
import com.book.store.athena.model.repository.UserRepository;
import com.book.store.athena.services.UserServices;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class NotFoundUser {

    private final UserRepository userRepository;

    private final UserServices userServices;

    public NotFoundUser (UserRepository userRepository, UserServices userServices) {

        this.userRepository = userRepository;

        this.userServices = userServices;

    }

    @Before("execution(* com.book.store.athena.controllers.UserController.*(..)) && args(id,..)")
    public void notFound (Long id) {

        var user = userRepository.findById(id);

        if (user.isEmpty()) {

            throw new NotFoundUserException("User with id " + id + " not found");

        }

    }

    @Before(value = "execution(* com.book.store.athena.controllers.UserController.*(..)) && args (id, updateUserDto)", argNames = "id,updateUserDto")
    public void invalidUpdate (Long id, UpdateUserDto updateUserDto) {

        var user = userServices.update(id, updateUserDto);

        if (user == null) {

            throw new NotFoundUserException("User with id " + id + " not found");

        }

    }

    @Before(value = "execution(* com.book.store.athena.controllers.UserController.*(..)) && args (id, ..)")
    public void invalidDisable (Long id) {

        var user = userServices.disable(id);

        if (user == null) {

            throw new NotFoundUserException("User with id " + id + " not found");

        }

    }

    @Before(value = "execution(* com.book.store.athena.controllers.UserController.*(..)) && args (id, ..)")
    public void invalidReactivation (Long id) {

        var user = userServices.reactivate(id);

        if (user == null) {

            throw new NotFoundUserException("User with id " + id + " not found");

        }

    }

}
