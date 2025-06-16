package com.wenjun.astra_app.plugins.accounts;

import com.wenjun.astra_app.model.AstraException;
import com.wenjun.astra_app.model.dto.CreateUserDTO;
import com.wenjun.astra_app.model.enums.accounts.Provider;
import com.wenjun.astra_persistence.models.AccountEntity;
import com.wenjun.astra_persistence.models.UserEntity;
import com.wenjun.astra_persistence.repository.AccountRepository;
import com.wenjun.astra_persistence.repository.UserRepository;
import com.wenjun.astra_third_party_services.firebase.model.AuthenticatedUser;
import com.wenjun.astra_third_party_services.firebase.service.FirebaseClient;

import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class PasswordProviderPlugin implements ProviderPlugin {
    private final FirebaseClient firebaseClient;
    private final AccountRepository accountRepository;
    private final UserRepository userRepository;

    @Override
    public Provider getProvider() {
        return Provider.PASSWORD;
    }

    @Override
    public void createUser(CreateUserDTO request) throws AstraException {
        AuthenticatedUser authenticatedUser = firebaseClient.createUser(request.getEmail(), request.getPassword());
        AccountEntity account = new AccountEntity();
        account.setProviderId(Provider.PASSWORD.getProviderId());
        account.setUid(authenticatedUser.getUid());
        accountRepository.insertSelective(account);

        UserEntity user = new UserEntity();
        user.setEmail(request.getEmail());
        user.setUid(authenticatedUser.getUid());
        userRepository.insertSelective(user);
    }
}
