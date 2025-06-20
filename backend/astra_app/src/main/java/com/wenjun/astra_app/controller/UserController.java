package com.wenjun.astra_app.controller;

import com.wenjun.astra_app.model.AstraException;
import com.wenjun.astra_app.model.dto.CreateUserDTO;
import com.wenjun.astra_app.model.dto.UpdateUserDTO;
import com.wenjun.astra_app.service.UserService;
import com.wenjun.astra_persistence.models.UserEntity;

import lombok.AllArgsConstructor;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

@AllArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @PutMapping
    public void updateUser(@Valid @RequestBody UpdateUserDTO request) throws AstraException {
        userService.updateUser(request);
    }

    @Transactional
    @PostMapping("/register")
    public void createUser(@Valid @RequestBody CreateUserDTO request) throws AstraException {
        userService.createUserWithEmailAndPassword(request);
    }

    @DeleteMapping
    public void deleteUser() throws AstraException {
        userService.deleteUser();
    }

    @GetMapping
    public UserEntity getUser() throws AstraException {
        return userService.getUser();
    }
}
