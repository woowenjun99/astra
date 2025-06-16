package com.wenjun.astra_app.model.dto;

import lombok.Data;

import jakarta.validation.constraints.Email;

@Data
public class CreateUserDTO {
    @Email
    private final String email;
    private final String password;
    private final String provider;
    private final String uid;
}
