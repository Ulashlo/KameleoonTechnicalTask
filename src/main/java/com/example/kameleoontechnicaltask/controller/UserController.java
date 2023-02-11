package com.example.kameleoontechnicaltask.controller;

import com.example.kameleoontechnicaltask.controller.dto.AccountInfoDTO;
import com.example.kameleoontechnicaltask.controller.dto.UserInfoForCreateDTO;
import com.example.kameleoontechnicaltask.service.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/register")
    public AccountInfoDTO createAccount(@RequestBody
                                        @Valid UserInfoForCreateDTO infoForCreate) {
        return userService.createAccount(infoForCreate);
    }

    @GetMapping("/login")
    public AccountInfoDTO login(@RequestParam
                                @NotBlank String name,
                                @RequestParam
                                @NotBlank String password) {
        return userService.login(name, password);
    }
}
