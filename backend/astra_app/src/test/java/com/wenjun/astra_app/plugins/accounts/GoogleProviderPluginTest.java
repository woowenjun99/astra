package com.wenjun.astra_app.plugins.accounts;

import com.wenjun.astra_app.model.enums.accounts.Provider;
import com.wenjun.astra_persistence.repository.AccountRepository;
import com.wenjun.astra_persistence.repository.UserRepository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class GoogleProviderPluginTest {
    @Mock
    private AccountRepository accountRepository;
    @Mock
    private UserRepository userRepository;

    @Test
    public void getProvider_shouldReturnPasswordProvider() {
        GoogleProviderPlugin plugin = new GoogleProviderPlugin(accountRepository, userRepository);
        Assertions.assertEquals(Provider.GOOGLE, plugin.getProvider());
    }
}
