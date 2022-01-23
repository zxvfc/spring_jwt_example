package io.hexlet.application.controller;

import io.hexlet.application.dto.UserDto;
import io.hexlet.application.dto.UserEditDto;
import io.hexlet.application.dto.UserRegistrationDto;
import io.hexlet.application.entity.User;
import io.hexlet.application.service.UserService;
import java.util.List;
import javax.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static io.hexlet.application.controller.UserController.USER_CONTROLLER_PATH;

@RestController
@RequestMapping(USER_CONTROLLER_PATH)
public class UserController {

    public static final String USER_CONTROLLER_PATH = "/users";
    public static final String ID = "/{id}";

    private static final String ONLY_OWNER_BY_ID = """
            @userRepository.findById(#id).get().getEmail() == authentication.getName()
        """;

    private final UserService userService;

    public UserController(final UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public UserDto createNewUser(@RequestBody @Valid final UserRegistrationDto dto) {
        final User user = userService.createUser(dto);
        return new UserDto(user);
    }

    @GetMapping
    public List<UserDto> getAllUsers() {
        return userService.getAll()
                .stream()
                .map(UserDto::new)
                .toList();
    }

    @PutMapping(ID)
    @PreAuthorize(ONLY_OWNER_BY_ID)
    public UserDto editUser(@PathVariable final Long id,
                            @RequestBody @Valid UserEditDto dto) {
        final User user = userService.updateUser(id, dto);
        return new UserDto(user);
    }
}
