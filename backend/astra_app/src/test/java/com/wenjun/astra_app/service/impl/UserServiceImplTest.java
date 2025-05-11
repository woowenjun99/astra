package com.wenjun.astra_app.service.impl;

import com.google.firebase.auth.UserRecord;
import com.wenjun.astra_app.model.dto.CreateUserDTO;
import com.wenjun.astra_app.service.AuthService;
import com.wenjun.astra_app.service.UserService;
import com.wenjun.astra_persistence.repository.UserRepository;

import com.google.firebase.auth.FirebaseAuthException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private AuthService authService;

    @Test
    public void createUser_emailExists_shouldThrow () {
        // Arrange
        CreateUserDTO request = new CreateUserDTO();
        request.setEmail("abc@gmail.com");
        Mockito.when(userRepository.doesUserWithEmailExists(request.getEmail())).thenReturn(true);

        // Act
        UserService userService = new UserServiceImpl(null, userRepository);
        Throwable exception = Assertions.assertThrows(RuntimeException.class, () -> userService.createUser(request));

        // Assert
        Assertions.assertEquals("Email already in use", exception.getMessage());
    }

    @Test
    public void createUser_emailNotExist_shouldSave () throws FirebaseAuthException {
        // Arrange
        CreateUserDTO request = new CreateUserDTO();
        request.setEmail("abc@gmail.com");
        UserRecord userRecord = Mockito.mock(UserRecord.class);
        Mockito.when(userRecord.getUid()).thenReturn("1");
        Mockito.when(userRepository.doesUserWithEmailExists(request.getEmail())).thenReturn(false);
        Mockito.when(authService.createUser(request.getEmail(), request.getPassword())).thenReturn(userRecord);

        // Act
        UserService userService = new UserServiceImpl(authService, userRepository);
        Assertions.assertDoesNotThrow(() -> userService.createUser(request));

        // Assert
        Mockito.verify(userRepository, Mockito.times(1)).createUser(Mockito.any());
    }
}
