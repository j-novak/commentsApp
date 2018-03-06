package com.dummy.commentsApp.controller;

import com.dummy.commentsApp.dto.UserDTO;
import com.dummy.commentsApp.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/register")
public class RegistrationController {

    private final UserService userService;

    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public void signUp(@RequestBody UserDTO user) {
        userService.registerNewUserAccount(user);
    }
}