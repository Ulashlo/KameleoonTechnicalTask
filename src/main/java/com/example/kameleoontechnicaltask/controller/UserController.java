package com.example.kameleoontechnicaltask.controller;

import com.example.kameleoontechnicaltask.controller.dto.UserInfoForCreateDTO;
import com.example.kameleoontechnicaltask.model.User;
import com.example.kameleoontechnicaltask.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/user")
    public void createUser(@RequestBody
                           @Valid UserInfoForCreateDTO infoForCreate) {
        userService.createUser(infoForCreate);
    }

    @GetMapping("/users")
    public List<User> getAllUser() {
        return userService.getAllUsers();
    }
}
