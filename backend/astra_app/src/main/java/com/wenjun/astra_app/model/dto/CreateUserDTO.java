package com.wenjun.astra_app.model.dto;

import lombok.Data;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

@Data
public class CreateUserDTO {
    @Email
    private String email;
    @NotEmpty
    private String password;
}
