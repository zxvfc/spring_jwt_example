package io.hexlet.application.dto;

import io.hexlet.application.entity.User;
import java.util.Date;

public record UserDto(
        Long id,
        String email,
        String firstName,
        Date creationDate
) {
    public UserDto(final User user) {
        this(
                user.getId(),
                user.getEmail(),
                user.getFirstName(),
                user.getCreationDate()
        );
    }
}
