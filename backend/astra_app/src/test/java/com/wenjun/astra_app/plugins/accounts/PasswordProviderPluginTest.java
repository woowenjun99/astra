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

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
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

    @Test
    public void createUser_emailAlreadyInUseInAnotherPasswordProvider_shouldThrow() {
        Mockito.when(userRepository.isEmailInUse("abc@gmail.com")).thenReturn(true);
        PasswordProviderPlugin plugin = new PasswordProviderPlugin(firebaseClient, accountRepository, userRepository);
        CreateUserDTO request = new CreateUserDTO("abc@gmail.com", "", "password", null);

        Throwable exception = Assertions.assertThrows(AstraException.class, () -> plugin.createUser(request));

        Assertions.assertEquals("Existing email found", exception.getMessage());
    }

    @Test
    public void createUser_emailNotInUseInAnotherPasswordProvider_shouldNotThrow() {
        AuthenticatedUser authenticatedUser = new AuthenticatedUser("userId");
        Mockito.when(userRepository.isEmailInUse("abc@gmail.com")).thenReturn(false);
        Mockito.when(firebaseClient.createUser("abc@gmail.com", "")).thenReturn(authenticatedUser);
        PasswordProviderPlugin plugin = new PasswordProviderPlugin(firebaseClient, accountRepository, userRepository);
        CreateUserDTO request = new CreateUserDTO("abc@gmail.com", "", "password", null);

        Assertions.assertDoesNotThrow(() -> plugin.createUser(request));

        Mockito.verify(accountRepository, Mockito.times(1)).insertSelective(Mockito.any(AccountEntity.class));
        Mockito.verify(userRepository, Mockito.times(1)).insertSelective(Mockito.any(UserEntity.class));
    }
}
