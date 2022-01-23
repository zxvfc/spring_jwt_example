package io.hexlet.application.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public record UserRegistrationDto(
        @NotBlank @Email
        String email,
        @NotBlank
        String firstName,
        @NotBlank
        String password
) {

}
