package com.wenjun.astra_app.service.impl;

import com.wenjun.astra_app.model.AstraException;
import com.wenjun.astra_app.model.dto.UpdateUserDTO;
import com.wenjun.astra_app.model.enums.AstraExceptionEnum;
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
    public void updateUser(UpdateUserDTO request) throws AstraException {
        FirebaseToken token = ThreadLocalUser.get();
        if (token == null) {
            throw new AstraException(AstraExceptionEnum.UNAUTHORIZED);
        }
        String uid = token.getUid();
        UserEntity user = userRepository.getUserByUid(uid);
        if (user == null) {
            throw new AstraException(AstraExceptionEnum.RESOURCE_NOT_FOUND_EXCEPTION, "User");
        }
        user.setFullName(request.getFullName());
        user.setGoalDate(request.getGoalDate());
        user.setGoalWeight(request.getGoalWeight());
        user.setProfilePhotoUrl(request.getProfilePhotoUrl());
        userRepository.updateByPrimaryKey(user);
    }
}
