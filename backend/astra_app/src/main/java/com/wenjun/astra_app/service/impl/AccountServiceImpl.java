package com.wenjun.astra_app.service.impl;

import com.wenjun.astra_app.model.AstraException;
import com.wenjun.astra_app.model.dto.LinkAccountDTO;
import com.wenjun.astra_app.model.enums.accounts.Provider;
import com.wenjun.astra_app.service.AccountService;
import com.wenjun.astra_app.util.ThreadLocalUser;
import com.wenjun.astra_persistence.models.AccountEntity;
import com.wenjun.astra_persistence.repository.AccountRepository;
import com.wenjun.astra_third_party_services.firebase.model.AuthenticatedUser;

import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;

    @Override
    public void linkAccount(LinkAccountDTO request) throws AstraException {
        Provider provider = Provider.getByProviderId(request.getProvider());
        AuthenticatedUser authenticatedUser = ThreadLocalUser.getAuthenticatedUser();
        String userId = authenticatedUser.getUid();

        // We don't throw an error to keep it idempotent
        Boolean doesAccountExist = accountRepository.doesAccountExist(userId, provider.getProviderId());
        if (doesAccountExist) {
            return;
        }

        AccountEntity accountEntity = new AccountEntity();
        accountEntity.setProviderId(provider.getProviderId());
        accountEntity.setUid(userId);
        accountRepository.insertSelective(accountEntity);
    }

    @Override
    public void unlinkAccount(LinkAccountDTO request) throws AstraException {
        Provider provider = Provider.getByProviderId(request.getProvider());
        AuthenticatedUser authenticatedUser = ThreadLocalUser.getAuthenticatedUser();
        String userId = authenticatedUser.getUid();
        accountRepository.deleteByUidAndProvider(userId, provider.getProviderId());
    }
}
