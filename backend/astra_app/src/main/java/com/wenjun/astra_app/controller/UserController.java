package com.wenjun.astra_app.controller;

import com.wenjun.astra_app.model.AstraException;
import com.wenjun.astra_app.model.dto.CreateUserDTO;
import com.wenjun.astra_app.model.dto.UpdateUserDTO;
import com.wenjun.astra_app.service.UserService;

import com.google.firebase.auth.FirebaseAuthException;

import lombok.AllArgsConstructor;

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
    private void updateUser(@Valid @RequestBody UpdateUserDTO request) throws AstraException, FirebaseAuthException {
        userService.updateUser(request);
    }

    @PostMapping("/register")
    private void createUser(@Valid @RequestBody CreateUserDTO request) throws AstraException, FirebaseAuthException {
        userService.createUser(request);
    }
}
