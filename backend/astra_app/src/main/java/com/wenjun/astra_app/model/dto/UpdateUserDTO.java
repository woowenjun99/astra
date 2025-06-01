package com.wenjun.astra_app.model.dto;

import lombok.Data;

import java.util.Date;

@Data
public class UpdateUserDTO {
    private final String profilePhotoUrl;
    private final Double goalWeight;
    private final Date goalDate;
    private final String fullName;
}
