package com.example.kameleoontechnicaltask.service;

import com.example.kameleoontechnicaltask.controller.dto.UserInfoForCreateDTO;
import com.example.kameleoontechnicaltask.model.User;

import java.util.List;

public interface UserService {
    List<User> getAllUsers();

    void createUser(UserInfoForCreateDTO infoForCreate);
}
