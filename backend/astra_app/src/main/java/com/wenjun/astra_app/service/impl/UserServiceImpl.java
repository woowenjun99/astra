package com.wenjun.astra_app.service.impl;

import com.wenjun.astra_app.model.AstraException;
import com.wenjun.astra_app.model.dto.CreateUserDTO;
import com.wenjun.astra_app.model.dto.UpdateUserDTO;
import com.wenjun.astra_app.model.enums.AstraExceptionEnum;
import com.wenjun.astra_app.model.enums.users.Gender;
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
        Boolean didEmailChange = !user.getEmail().equalsIgnoreCase(request.getEmail().trim());

        if (didEmailChange) {
            // If the email is in use, we need to throw a 409 CONFLICT error to let the user know they cannot use that email
            Boolean isEmailInUse = userRepository.isEmailInUse(request.getEmail().trim());
            if (isEmailInUse) {
                throw new AstraException(AstraExceptionEnum.CONFLICT, "Email");
            }
            // If the email is not in use, we need to update FirebaseAuth first
            authService.updateUser(ThreadLocalUser.get().getUid(), request.getEmail().trim());
        }

        if (request.getGender() != null) {
            user.setGender(Gender.getByAlias(request.getGender()).getCode());
        }

        user.setEmail(request.getEmail());
        user.setFullName(request.getName());
        user.setBio(request.getBio());
        user.setDateOfBirth(request.getDob());
        userRepository.updateByPrimaryKey(user);
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
