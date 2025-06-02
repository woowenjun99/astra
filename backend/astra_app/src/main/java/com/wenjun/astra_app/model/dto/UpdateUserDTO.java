package com.wenjun.astra_app.model.dto;

import jakarta.validation.constraints.Email;
import lombok.Data;

@Data
public class UpdateUserDTO {
    private final String bio;
    @Email
    private final String email;
    private final String name;
}
