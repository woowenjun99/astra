package com.wenjun.astra_app.model.dto;

import lombok.Data;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@Data
public class CreateUserDTO {
    @Email
    private final String email;
    @NotNull
    @NotEmpty
    private final String password;
}
