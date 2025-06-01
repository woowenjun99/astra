package com.wenjun.astra_app.service.impl;

import com.wenjun.astra_app.model.dto.UpdateUserDTO;
import com.wenjun.astra_app.service.UserService;
import com.wenjun.astra_persistence.models.UserEntity;
import com.wenjun.astra_persistence.repository.UserRepository;

import com.google.firebase.auth.FirebaseToken;

import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public void updateUser(UpdateUserDTO request) {
        FirebaseToken token = ThreadLocalUser.get();
        if (token == null) {
            throw new RuntimeException("User is not logged in");
        }
        String uid = token.getUid();
        UserEntity user = userRepository.getUserByUid(uid);
        if (user == null) {
            throw new RuntimeException("User not found in the database");
        }
    }
}
