package com.wenjun.astra_app.model.dto;

import lombok.Data;

@Data
public class UpdateUserDTO {
    private final String bio;
    private final String email;
    private final String name;
}
