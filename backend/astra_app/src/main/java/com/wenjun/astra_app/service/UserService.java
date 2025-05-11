package com.wenjun.astra_app.service;

import com.wenjun.astra_app.model.dto.CreateUserDTO;
import com.wenjun.astra_persistence.models.UserEntity;

import com.google.firebase.auth.FirebaseAuthException;

public interface UserService {
    UserEntity createUser(CreateUserDTO request) throws FirebaseAuthException;
}
