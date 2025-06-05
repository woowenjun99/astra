package com.wenjun.astra_app.service.impl;

import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;
import com.wenjun.astra_app.model.AstraException;
import com.wenjun.astra_app.model.dto.CreateUserDTO;
import com.wenjun.astra_app.model.dto.UpdateUserDTO;
import com.wenjun.astra_app.model.enums.AstraExceptionEnum;
import com.wenjun.astra_app.service.AuthService;
import com.wenjun.astra_app.service.UserService;
import com.wenjun.astra_persistence.models.UserEntity;
import com.wenjun.astra_persistence.repository.UserRepository;

import com.google.firebase.auth.FirebaseToken;

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
    private AuthService authService;
    @Mock
    private UserRepository userRepository;

    @Test
    public void getUser_userNotLoggedIn_shouldThrow() {
        try (MockedStatic<ThreadLocalUser> mocked = Mockito.mockStatic(ThreadLocalUser.class)) {
            mocked.when(() -> ThreadLocalUser.get()).thenReturn(null);
            UserService userService = new UserServiceImpl(authService, userRepository);
            Throwable exception = Assertions.assertThrows(AstraException.class, () -> userService.getUser());
            Assertions.assertEquals(AstraExceptionEnum.UNAUTHORIZED.getErrorMessage(), exception.getMessage());
        }
    }

    @Test
    public void getUser_userNotFound_shouldThrow() {
        try (MockedStatic<ThreadLocalUser> mocked = Mockito.mockStatic(ThreadLocalUser.class)) {
            FirebaseToken firebaseToken = Mockito.mock(FirebaseToken.class);
            Mockito.when(firebaseToken.getUid()).thenReturn("userId");
            mocked.when(() -> ThreadLocalUser.get()).thenReturn(firebaseToken);
            Mockito.when(userRepository.getUserByUid("userId")).thenReturn(null);
            UserService userService = new UserServiceImpl(authService, userRepository);
            Throwable exception = Assertions.assertThrows(AstraException.class, () -> userService.getUser());
            Assertions.assertEquals("User cannot be found", exception.getMessage());
        }
    }

    @Test
    public void deleteUser_validUser_shouldCallCorrectly() {
        try (MockedStatic<ThreadLocalUser> mocked = Mockito.mockStatic(ThreadLocalUser.class)) {
            FirebaseToken firebaseToken = Mockito.mock(FirebaseToken.class);
            Mockito.when(firebaseToken.getUid()).thenReturn("userId");
            mocked.when(() -> ThreadLocalUser.get()).thenReturn(firebaseToken);
            UserEntity user = new UserEntity();
            user.setUid("userId");
            Mockito.when(userRepository.getUserByUid("userId")).thenReturn(user);
            UserService userService = new UserServiceImpl(authService, userRepository);
            Assertions.assertDoesNotThrow(() -> userService.deleteUser());
            Mockito.verify(userRepository, Mockito.times(1)).deleteByUid("userId");
        }
    }

    @Test
    public void createUser_ifEmailInUse_throwError() {
        Mockito.when(userRepository.isEmailInUse("woowenjun99@gmail.com")).thenReturn(true);
        UserService userService = new UserServiceImpl(null, userRepository);
        CreateUserDTO request = new CreateUserDTO("woowenjun99@gmail.com", "123456");
        Throwable exception = Assertions.assertThrows(AstraException.class, () -> userService.createUser(request));
        Assertions.assertEquals("Existing Email found", exception.getMessage());
    }

    @Test
    public void createUser_ifEmailNotInUse_shouldSave() throws FirebaseAuthException {
        // Arrange
        UserRecord userRecord = Mockito.mock(UserRecord.class);
        Mockito.when(userRepository.isEmailInUse("woowenjun99@gmail.com")).thenReturn(false);
        Mockito.when(authService.createUser("woowenjun99@gmail.com", "123456")).thenReturn(userRecord);
        UserService userService = new UserServiceImpl(authService, userRepository);
        CreateUserDTO request = new CreateUserDTO("woowenjun99@gmail.com", "123456");

        // Act
        Assertions.assertDoesNotThrow(() -> userService.createUser(request));

        // Assert
        Mockito.verify(authService, Mockito.times(1)).createUser("woowenjun99@gmail.com", "123456");
        Mockito.verify(userRepository, Mockito.times(1)).insertSelective(Mockito.any(UserEntity.class));
    }

    @Test
    public void updateUser_ifUserWantsToUpdateEmailThatIsInUse_shouldThrow() {
        try (MockedStatic<ThreadLocalUser> mocked = Mockito.mockStatic(ThreadLocalUser.class)) {
            // Arrange
            FirebaseToken token = Mockito.mock(FirebaseToken.class);
            UserEntity user = new UserEntity();
            user.setEmail("woowenjun98@gmail.com");
            Mockito.when(token.getUid()).thenReturn("userId");
            mocked.when(() -> ThreadLocalUser.get()).thenReturn(token);
            Mockito.when(userRepository.getUserByUid("userId")).thenReturn(user);
            UpdateUserDTO request = new UpdateUserDTO(null, "woowenjun99@gmail.com", null, null, null);
            Mockito.when(userRepository.isEmailInUse("woowenjun99@gmail.com")).thenReturn(true);

            // Act
            UserService userService = new UserServiceImpl(authService, userRepository);
            Throwable exception = Assertions.assertThrows(AstraException.class, () -> userService.updateUser(request));

            // Assertions
            Assertions.assertEquals("Existing Email found", exception.getMessage());
        }
    }

    @Test
    public void updateUser_ifUserWantsToUpdateEmailThatIsNotInUse_shouldSave() throws FirebaseAuthException {
        try (MockedStatic<ThreadLocalUser> mocked = Mockito.mockStatic(ThreadLocalUser.class)) {
            // Arrange
            FirebaseToken token = Mockito.mock(FirebaseToken.class);
            UserEntity user = new UserEntity();
            user.setEmail("woowenjun98@gmail.com");
            Mockito.when(token.getUid()).thenReturn("userId");
            mocked.when(() -> ThreadLocalUser.get()).thenReturn(token);
            Mockito.when(userRepository.getUserByUid("userId")).thenReturn(user);
            UpdateUserDTO request = new UpdateUserDTO(null, "woowenjun99@gmail.com", null, null, null);
            Mockito.when(userRepository.isEmailInUse("woowenjun99@gmail.com")).thenReturn(false);

            // Act
            UserService userService = new UserServiceImpl(authService, userRepository);
            Assertions.assertDoesNotThrow(() -> userService.updateUser(request));

            // Assertions
            Mockito.verify(userRepository, Mockito.times(1)).updateByPrimaryKey(Mockito.any(UserEntity.class));
            Mockito.verify(authService, Mockito.times(1)).updateUser("userId", "woowenjun99@gmail.com");
        }
    }

    @Test
    public void updateUser_ifUserDoesNotNeedToUpdateEmail_shouldNotCallAuthService() throws FirebaseAuthException {
        try (MockedStatic<ThreadLocalUser> mocked = Mockito.mockStatic(ThreadLocalUser.class)) {
            // Arrange
            FirebaseToken token = Mockito.mock(FirebaseToken.class);
            UserEntity user = new UserEntity();
            user.setEmail("woowenjun98@gmail.com");
            Mockito.when(token.getUid()).thenReturn("userId");
            mocked.when(() -> ThreadLocalUser.get()).thenReturn(token);
            Mockito.when(userRepository.getUserByUid("userId")).thenReturn(user);
            UpdateUserDTO request = new UpdateUserDTO(null, "woowenjun98@gmail.com", null, null, null);

            // Act
            UserService userService = new UserServiceImpl(authService, userRepository);
            Assertions.assertDoesNotThrow(() -> userService.updateUser(request));

            // Assertions
            Mockito.verify(userRepository, Mockito.times(1)).updateByPrimaryKey(Mockito.any(UserEntity.class));
            Mockito.verify(authService, Mockito.never()).updateUser("userId", "woowenjun99@gmail.com");
        }
    }
}
