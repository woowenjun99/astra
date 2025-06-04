package com.wenjun.astra_app.service.impl;

import com.wenjun.astra_app.model.AstraException;
import com.wenjun.astra_app.model.dto.CreateUserDTO;
import com.wenjun.astra_app.model.dto.UpdateUserDTO;
import com.wenjun.astra_app.model.enums.AstraExceptionEnum;
import com.wenjun.astra_app.service.AuthService;
import com.wenjun.astra_app.service.UserService;
import com.wenjun.astra_persistence.models.UserEntity;
import com.wenjun.astra_persistence.repository.UserRepository;

import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import com.google.firebase.auth.UserRecord;

import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final AuthService authService;
    private final UserRepository userRepository;

    @Override
    public void updateUser(UpdateUserDTO request) throws AstraException, FirebaseAuthException {
        UserEntity user = getUser();
        Boolean needChangeNameOrEmail = user.getFullName().equals(request.getName()) || user.getEmail().equals(request.getEmail());
        user.setFullName(request.getName());
        user.setBio(request.getBio());
        user.setEmail(request.getEmail());
        userRepository.updateByPrimaryKey(user);
        if (needChangeNameOrEmail) {
            authService.updateUser(user.getUid(), request.getEmail(), request.getName());
        }
    }

    @Override
    public void createUser(CreateUserDTO request) throws AstraException, FirebaseAuthException {
        boolean isEmailInUse = userRepository.isEmailInUse(request.getEmail());
        if (isEmailInUse) {
            throw new AstraException(AstraExceptionEnum.CONFLICT, "Email");
        }
        UserRecord userRecord = authService.createUser(request.getEmail(), request.getPassword());
        UserEntity user = new UserEntity();
        user.setEmail(request.getEmail());
        user.setUid(userRecord.getUid());
        userRepository.insertSelective(user);
    }

    @Override
    public void deleteUser() throws AstraException, FirebaseAuthException {
        UserEntity user = getUser();
        userRepository.deleteByUid(user.getUid());
        authService.deleteUser(user.getUid());
    }

    @Override
    public UserEntity getUser() throws AstraException {
        FirebaseToken firebaseToken = ThreadLocalUser.get();
        if (firebaseToken == null) {
            throw new AstraException(AstraExceptionEnum.UNAUTHORIZED);
        }
        UserEntity user = userRepository.getUserByUid(firebaseToken.getUid());
        if (user == null) {
            throw new AstraException(AstraExceptionEnum.RESOURCE_NOT_FOUND_EXCEPTION, "User");
        }
        return user;
    }
}
