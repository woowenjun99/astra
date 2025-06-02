package com.wenjun.astra_app.service.impl;

import com.wenjun.astra_app.model.AstraException;
import com.wenjun.astra_app.model.enums.AstraExceptionEnum;
import com.wenjun.astra_app.service.AuthService;
import com.wenjun.astra_app.service.UserService;
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
    public void updateUser_userNotLoggedIn_shouldThrow() {
        try (MockedStatic<ThreadLocalUser> mocked = Mockito.mockStatic(ThreadLocalUser.class)) {
            mocked.when(() -> ThreadLocalUser.get()).thenReturn(null);
            UserService userService = new UserServiceImpl(authService, userRepository);
            Throwable exception = Assertions.assertThrows(AstraException.class, () -> userService.updateUser(null));
            Assertions.assertEquals(AstraExceptionEnum.UNAUTHORIZED.getErrorMessage(), exception.getMessage());
        }
    }

    @Test
    public void updateUser_userNotFound_shouldThrow() {
        try (MockedStatic<ThreadLocalUser> mocked = Mockito.mockStatic(ThreadLocalUser.class)) {
            FirebaseToken firebaseToken = Mockito.mock(FirebaseToken.class);
            Mockito.when(firebaseToken.getUid()).thenReturn("userId");
            mocked.when(() -> ThreadLocalUser.get()).thenReturn(firebaseToken);
            Mockito.when(userRepository.getUserByUid("userId")).thenReturn(null);
            UserService userService = new UserServiceImpl(authService, userRepository);
            Throwable exception = Assertions.assertThrows(AstraException.class, () -> userService.updateUser(null));
            Assertions.assertEquals("User cannot be found", exception.getMessage());
        }
    }
}
