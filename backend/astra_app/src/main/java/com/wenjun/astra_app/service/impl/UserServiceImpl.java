package com.wenjun.astra_app.service.impl;

import com.wenjun.astra_app.model.dto.CreateUserDTO;
import com.wenjun.astra_app.service.AuthService;
import com.wenjun.astra_app.service.UserService;
import com.wenjun.astra_persistence.models.UserEntity;
import com.wenjun.astra_persistence.repository.UserRepository;

import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;

import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.Date;

@AllArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final AuthService authService;
    private final UserRepository userRepository;

    @Override
    public UserEntity createUser(CreateUserDTO request) throws FirebaseAuthException {
        boolean userWithEmailExist = userRepository.doesUserWithEmailExists(request.getEmail());
        if (userWithEmailExist) {
            throw new RuntimeException("Email already in use");
        }
        UserRecord userRecord = authService.createUser(request.getEmail(), request.getPassword());
        UserEntity user = new UserEntity();
        Date now = new Date();
        user.setEmail(request.getEmail());
        user.setUid(userRecord.getUid());
        user.setFullName("");
        userRepository.createUser(user);
        return user;
    }
}
