package com.book.store.athena.infra.aspect;

import com.book.store.athena.model.dto.client.UpdateUserDto;
import com.book.store.athena.model.repository.UserRepository;
import com.book.store.athena.services.UserServices;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.util.function.Supplier;

@Aspect
@Component
public class UserAspect {

    private final UserRepository userRepository;

    private final UserServices userServices;

    public UserAspect(UserRepository userRepository, UserServices userServices) {

        this.userRepository = userRepository;

        this.userServices = userServices;

    }

    private void validateUserExistence (Long id) {

        var user = userRepository.findById(id);

        if (user.isEmpty()) {

            throw new NotFoundException("User not found");

        }

    }

    private void validateServiceExistence (Supplier <Object> service) {

        var user = service.get();

        if (user == null) {

            throw new NotFoundException("Service not found");

        }

    }

    @Before("execution(* com.book.store.athena.controllers.UserController.*(..)) && args(id,..)")
    public void notFound (Long id) {

        validateUserExistence(id);

    }

    @Before(value = "execution(* com.book.store.athena.controllers.UserController.*(..)) && args (id, updateUserDto)", argNames = "id,updateUserDto")
    public void invalidUpdate (Long id, UpdateUserDto updateUserDto) {

        validateServiceExistence(() -> userServices.update(id, updateUserDto));

    }

    @Before(value = "execution(* com.book.store.athena.controllers.UserController.*(..)) && args (id, ..)")
    public void invalidDisable (Long id) {

        validateServiceExistence(() -> userServices.disable(id));

    }

    @Before(value = "execution(* com.book.store.athena.controllers.UserController.*(..)) && args (id, ..)")
    public void invalidReactivation (Long id) {

        validateServiceExistence(() -> userServices.reactivate(id));

    }

}
