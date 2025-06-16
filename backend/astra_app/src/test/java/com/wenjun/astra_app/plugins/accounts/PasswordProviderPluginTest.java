package com.wenjun.astra_app.plugins.accounts;

import com.wenjun.astra_app.model.enums.accounts.Provider;
import com.wenjun.astra_persistence.repository.AccountRepository;
import com.wenjun.astra_persistence.repository.UserRepository;
import com.wenjun.astra_third_party_services.firebase.service.FirebaseClient;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class PasswordProviderPluginTest {
    @Mock
    private FirebaseClient firebaseClient;
    @Mock
    private AccountRepository accountRepository;
    @Mock
    private UserRepository userRepository;

    @Test
    public void getProvider_shouldReturnPasswordProvider() {
        PasswordProviderPlugin plugin = new PasswordProviderPlugin(firebaseClient, accountRepository, userRepository);
        Assertions.assertEquals(Provider.PASSWORD, plugin.getProvider());
    }
}
