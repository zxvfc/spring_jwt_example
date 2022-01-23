package io.hexlet.application.service;

import io.hexlet.application.dto.UserEditDto;
import io.hexlet.application.dto.UserRegistrationDto;
import io.hexlet.application.entity.User;
import java.util.List;

public interface UserService {

    User createUser(UserRegistrationDto dto);

    List<User> getAll();

    User updateUser(Long id, UserEditDto dto);

}
