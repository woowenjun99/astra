package com.wenjun.astra_app.service;

import com.wenjun.astra_app.model.AstraException;
import com.wenjun.astra_app.model.dto.UpdateUserDTO;

public interface UserService {
    void updateUser(UpdateUserDTO request) throws AstraException;
}
