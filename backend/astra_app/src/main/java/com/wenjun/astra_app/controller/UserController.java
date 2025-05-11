package com.wenjun.astra_app.controller;

import com.wenjun.astra_app.model.dto.CreateUserDTO;
import com.wenjun.astra_app.service.UserService;
import com.wenjun.astra_persistence.models.UserEntity;

import com.google.firebase.auth.FirebaseAuthException;

import lombok.AllArgsConstructor;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

@AllArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @ResponseBody
    @PostMapping("/register")
    public UserEntity createUser(@Valid @RequestBody CreateUserDTO request) throws FirebaseAuthException {
        return userService.createUser(request);
    }
}
