package com.wenjun.astra_app.plugins.accounts;

import com.wenjun.astra_app.model.dto.CreateUserDTO;
import com.wenjun.astra_app.model.enums.accounts.Provider;
import com.wenjun.astra_persistence.models.AccountEntity;
import com.wenjun.astra_persistence.models.UserEntity;
import com.wenjun.astra_persistence.repository.AccountRepository;
import com.wenjun.astra_persistence.repository.UserRepository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
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

    @Test
    public void getProvider_userAlreadyUsedProviderToCreateAccount_doNothing() {
        Mockito.when(accountRepository.doesAccountExist("userId", Provider.GOOGLE.getProviderId())).thenReturn(true);
        GoogleProviderPlugin plugin = new GoogleProviderPlugin(accountRepository, userRepository);
        CreateUserDTO request = new CreateUserDTO(null, null, "google.com", "userId");

        Assertions.assertDoesNotThrow(() -> plugin.createUser(request));

        Mockito.verify(accountRepository, Mockito.never()).insertSelective(Mockito.any(AccountEntity.class));
    }

    @Test
    public void getProvider_userNotUsedProviderToCreateAccountYet_savesCorrectly() {
        Mockito.when(accountRepository.doesAccountExist("userId", Provider.GOOGLE.getProviderId())).thenReturn(false);
        GoogleProviderPlugin plugin = new GoogleProviderPlugin(accountRepository, userRepository);
        CreateUserDTO request = new CreateUserDTO(null, null, "google.com", "userId");

        Assertions.assertDoesNotThrow(() -> plugin.createUser(request));

        Mockito.verify(accountRepository, Mockito.times(1)).insertSelective(Mockito.any(AccountEntity.class));
        Mockito.verify(userRepository, Mockito.times(1)).insertSelective(Mockito.any(UserEntity.class));
    }
}
