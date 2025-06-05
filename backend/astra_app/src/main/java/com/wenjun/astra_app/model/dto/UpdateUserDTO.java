package com.wenjun.astra_app.model.dto;

import lombok.Data;

import java.util.Date;

import jakarta.validation.constraints.Email;

@Data
public class UpdateUserDTO {
    private final String bio;
    @Email
    private final String email;
    private final String name;
    private final Date dob;
    private final String gender;
}
