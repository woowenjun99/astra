package com.wenjun.astra_app.service.impl;

import com.wenjun.astra_app.model.AstraException;
import com.wenjun.astra_app.model.dto.UpdateUserDTO;
import com.wenjun.astra_app.plugins.accounts.ProviderPlugins;
import com.wenjun.astra_app.service.UserService;
import com.wenjun.astra_app.util.ThreadLocalUser;
import com.wenjun.astra_persistence.models.UserEntity;
import com.wenjun.astra_persistence.repository.UserRepository;
import com.wenjun.astra_third_party_services.firebase.model.AuthenticatedUser;
import com.wenjun.astra_third_party_services.firebase.service.FirebaseClient;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private FirebaseClient firebaseClient;
    @Mock
    private ProviderPlugins providerPlugins;

    @Test
    public void getUser_userNotFound_shouldThrow() {
        try (MockedStatic<ThreadLocalUser> mocked = Mockito.mockStatic(ThreadLocalUser.class)) {
            AuthenticatedUser authenticatedUser = new AuthenticatedUser("userId");
            mocked.when(() -> ThreadLocalUser.getAuthenticatedUser()).thenReturn(authenticatedUser);
            Mockito.when(userRepository.getUserByUid("userId")).thenReturn(null);
            UserService userService = new UserServiceImpl(userRepository, firebaseClient, providerPlugins);
            Throwable exception = Assertions.assertThrows(AstraException.class, () -> userService.getUser());
            Assertions.assertEquals("User cannot be found", exception.getMessage());
        }
    }

    @Test
    public void deleteUser_validUser_shouldCallCorrectly() {
        try (MockedStatic<ThreadLocalUser> mocked = Mockito.mockStatic(ThreadLocalUser.class)) {
            AuthenticatedUser authenticatedUser = new AuthenticatedUser("userId");
            mocked.when(() -> ThreadLocalUser.getAuthenticatedUser()).thenReturn(authenticatedUser);
            UserEntity user = new UserEntity();
            user.setUid("userId");
            Mockito.when(userRepository.getUserByUid("userId")).thenReturn(user);
            UserService userService = new UserServiceImpl(userRepository, firebaseClient, providerPlugins);
            Assertions.assertDoesNotThrow(() -> userService.deleteUser());
            Mockito.verify(userRepository, Mockito.times(1)).deleteByUid("userId");
        }
    }

    @Test
    public void updateUser_ifUserWantsToUpdateEmailThatIsInUse_shouldThrow() {
        try (MockedStatic<ThreadLocalUser> mocked = Mockito.mockStatic(ThreadLocalUser.class)) {
            // Arrange
            AuthenticatedUser authenticatedUser = new AuthenticatedUser("userId");
            mocked.when(() -> ThreadLocalUser.getAuthenticatedUser()).thenReturn(authenticatedUser);
            UserEntity user = new UserEntity();
            user.setEmail("woowenjun98@gmail.com");
            Mockito.when(userRepository.getUserByUid("userId")).thenReturn(user);
            UpdateUserDTO request = new UpdateUserDTO(null, "woowenjun99@gmail.com", null, null, null);
            Mockito.when(userRepository.isEmailInUse("woowenjun99@gmail.com")).thenReturn(true);

            // Act
            UserService userService = new UserServiceImpl(userRepository, firebaseClient, providerPlugins);
            Throwable exception = Assertions.assertThrows(AstraException.class, () -> userService.updateUser(request));

            // Assertions
            Assertions.assertEquals("Existing Email found", exception.getMessage());
        }
    }

    @Test
    public void updateUser_ifUserWantsToUpdateEmailThatIsNotInUse_shouldSave() {
        try (MockedStatic<ThreadLocalUser> mocked = Mockito.mockStatic(ThreadLocalUser.class)) {
            // Arrange
            AuthenticatedUser authenticatedUser = new AuthenticatedUser("userId");
            mocked.when(() -> ThreadLocalUser.getAuthenticatedUser()).thenReturn(authenticatedUser);
            UserEntity user = new UserEntity();
            user.setEmail("woowenjun98@gmail.com");
            Mockito.when(userRepository.getUserByUid("userId")).thenReturn(user);
            UpdateUserDTO request = new UpdateUserDTO(null, "woowenjun99@gmail.com", null, null, null);
            Mockito.when(userRepository.isEmailInUse("woowenjun99@gmail.com")).thenReturn(false);

            // Act
            UserService userService = new UserServiceImpl(userRepository, firebaseClient, providerPlugins);
            Assertions.assertDoesNotThrow(() -> userService.updateUser(request));

            // Assertions
            Mockito.verify(userRepository, Mockito.times(1)).updateByPrimaryKey(Mockito.any(UserEntity.class));
            Mockito.verify(firebaseClient, Mockito.times(1)).updateUser("userId", "woowenjun99@gmail.com");
        }
    }

    @Test
    public void updateUser_ifUserDoesNotNeedToUpdateEmail_shouldNotCallAuthService() {
        try (MockedStatic<ThreadLocalUser> mocked = Mockito.mockStatic(ThreadLocalUser.class)) {
            // Arrange
            AuthenticatedUser authenticatedUser = new AuthenticatedUser("userId");
            mocked.when(() -> ThreadLocalUser.getAuthenticatedUser()).thenReturn(authenticatedUser);
            UserEntity user = new UserEntity();
            user.setEmail("woowenjun98@gmail.com");
            Mockito.when(userRepository.getUserByUid("userId")).thenReturn(user);
            UpdateUserDTO request = new UpdateUserDTO(null, "woowenjun98@gmail.com", null, null, null);

            // Act
            UserService userService = new UserServiceImpl(userRepository, firebaseClient, providerPlugins);
            Assertions.assertDoesNotThrow(() -> userService.updateUser(request));

            // Assertions
            Mockito.verify(userRepository, Mockito.times(1)).updateByPrimaryKey(Mockito.any(UserEntity.class));
            Mockito.verify(firebaseClient, Mockito.never()).updateUser("userId", "woowenjun99@gmail.com");
        }
    }
}
