package com.wenjun.astra_app.service.impl;

import com.wenjun.astra_app.model.AstraException;
import com.wenjun.astra_app.model.dto.CreateUserDTO;
import com.wenjun.astra_app.model.dto.UpdateUserDTO;
import com.wenjun.astra_app.model.enums.AstraExceptionEnum;
import com.wenjun.astra_app.model.enums.users.Gender;
import com.wenjun.astra_app.service.UserService;
import com.wenjun.astra_app.util.ThreadLocalUser;
import com.wenjun.astra_persistence.models.AccountEntity;
import com.wenjun.astra_persistence.models.UserEntity;
import com.wenjun.astra_persistence.repository.AccountRepository;
import com.wenjun.astra_persistence.repository.UserRepository;
import com.wenjun.astra_third_party_services.firebase.model.AuthenticatedUser;
import com.wenjun.astra_third_party_services.firebase.service.FirebaseClient;

import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.Date;

@AllArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final AccountRepository accountRepository;
    private final FirebaseClient firebaseClient;

    @Override
    public void updateUser(UpdateUserDTO request) throws AstraException {
        UserEntity user = getUser();
        Boolean didEmailChange = !user.getEmail().equalsIgnoreCase(request.getEmail().trim());

        if (didEmailChange) {
            // If the email is in use, we need to throw a 409 CONFLICT error to let the user know they cannot use that email
            Boolean isEmailInUse = userRepository.isEmailInUse(request.getEmail().trim());
            if (isEmailInUse) {
                throw new AstraException(AstraExceptionEnum.CONFLICT, "Email");
            }
            // If the email is not in use, we need to update FirebaseAuth first
            firebaseClient.updateUser(ThreadLocalUser.getAuthenticatedUser().getUid(), request.getEmail().trim());
        }

        if (request.getGender() != null) {
            Gender gender = Gender.getByAlias(request.getGender());
            user.setGender(gender.getAlias());
        }

        user.setEmail(request.getEmail());
        user.setFullName(request.getName());
        user.setBio(request.getBio());
        user.setDateOfBirth(request.getDob());
        user.setDateUpdated(new Date());
        userRepository.updateByPrimaryKey(user);
    }

    @Override
    public void createUserWithEmailAndPassword(CreateUserDTO request) throws AstraException {
        boolean isEmailInUse = userRepository.isEmailInUse(request.getEmail());
        if (isEmailInUse) {
            throw new AstraException(AstraExceptionEnum.CONFLICT, "Email");
        }
        AuthenticatedUser authenticatedUser = firebaseClient.createUser(request.getEmail(), request.getPassword());
        UserEntity user = new UserEntity();
        user.setEmail(request.getEmail());
        user.setUid(authenticatedUser.getUid());
        userRepository.insertSelective(user);

        AccountEntity account = new AccountEntity();
        account.setUid(user.getUid());
        account.setProviderId("password");
        accountRepository.insertSelective(account);
    }

    @Override
    public void deleteUser() throws AstraException {
        UserEntity user = getUser();
        userRepository.deleteByUid(user.getUid());
        firebaseClient.deleteUser(user.getUid());
    }

    @Override
    public UserEntity getUser() throws AstraException {
        AuthenticatedUser authenticatedUser = ThreadLocalUser.getAuthenticatedUser();
        UserEntity user = userRepository.getUserByUid(authenticatedUser.getUid());
        if (user == null) {
            throw new AstraException(AstraExceptionEnum.RESOURCE_NOT_FOUND_EXCEPTION, "User");
        }
        return user;
    }

    /**
     * Create a new user with the following provider
     *
     * @throws AstraException
     */
    @Override
    public void createUserWithProvider() throws AstraException {}
}
