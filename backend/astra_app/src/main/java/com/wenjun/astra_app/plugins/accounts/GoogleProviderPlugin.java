package com.wenjun.astra_app.plugins.accounts;

import com.wenjun.astra_app.model.AstraException;
import com.wenjun.astra_app.model.dto.CreateUserDTO;
import com.wenjun.astra_app.model.enums.accounts.Provider;
import com.wenjun.astra_persistence.models.AccountEntity;
import com.wenjun.astra_persistence.models.UserEntity;
import com.wenjun.astra_persistence.repository.AccountRepository;
import com.wenjun.astra_persistence.repository.UserRepository;

import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class GoogleProviderPlugin implements ProviderPlugin {
    private final AccountRepository accountRepository;
    private final UserRepository userRepository;

    private boolean doesAccountExist(CreateUserDTO request) throws AstraException {
        Provider provider = Provider.getByProviderId(request.getProvider());
        return accountRepository.doesAccountExist(request.getUid(), provider.getProviderId());
    }

    @Override
    public Provider getProvider() {
        return Provider.GOOGLE;
    }

    @Override
    public void createUser(CreateUserDTO request) throws AstraException {
        boolean doesAccountExist = doesAccountExist(request);
        if (doesAccountExist) {
            return;
        }

        UserEntity user = new UserEntity();
        user.setEmail(request.getEmail());
        user.setUid(request.getUid());
        userRepository.insertSelective(user);

        AccountEntity account = new AccountEntity();
        account.setUid(request.getUid());
        account.setProviderId(Provider.GOOGLE.getProviderId());
        accountRepository.insertSelective(account);
    }
}
