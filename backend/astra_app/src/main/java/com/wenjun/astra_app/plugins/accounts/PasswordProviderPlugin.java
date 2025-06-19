package com.wenjun.astra_app.plugins.accounts;

import com.wenjun.astra_app.model.AstraException;
import com.wenjun.astra_app.model.dto.CreateUserDTO;
import com.wenjun.astra_app.model.enums.AstraExceptionEnum;
import com.wenjun.astra_app.model.enums.accounts.Provider;
import com.wenjun.astra_persistence.models.AccountEntity;
import com.wenjun.astra_persistence.models.UserEntity;
import com.wenjun.astra_persistence.repository.AccountRepository;
import com.wenjun.astra_persistence.repository.UserRepository;
import com.wenjun.astra_third_party_services.firebase.model.AuthenticatedUser;
import com.wenjun.astra_third_party_services.firebase.service.FirebaseClient;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
@Slf4j
public class PasswordProviderPlugin implements ProviderPlugin {
    private final FirebaseClient firebaseClient;
    private final AccountRepository accountRepository;
    private final UserRepository userRepository;

    private Boolean isEmailAlreadyInUsedByPasswordProvider(CreateUserDTO request) {
        return userRepository.isEmailInUse(request.getEmail());
    }

    @Override
    public Provider getProvider() {
        return Provider.PASSWORD;
    }

    @Override
    public void createUser(CreateUserDTO request) throws AstraException {
        Boolean isEmailAlreadyInUsedByPasswordProvider = isEmailAlreadyInUsedByPasswordProvider(request);
        if (isEmailAlreadyInUsedByPasswordProvider) {
            log.error("Email {} is already in used", request.getEmail());
            throw new AstraException(AstraExceptionEnum.CONFLICT, "email");
        }

        AuthenticatedUser authenticatedUser = firebaseClient.createUser(request.getEmail(), request.getPassword());
        UserEntity user = new UserEntity();
        user.setEmail(request.getEmail());
        user.setUid(authenticatedUser.getUid());
        userRepository.insertSelective(user);

        AccountEntity account = new AccountEntity();
        account.setProviderId(Provider.PASSWORD.getProviderId());
        account.setUid(authenticatedUser.getUid());
        accountRepository.insertSelective(account);
    }
}
