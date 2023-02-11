package com.example.kameleoontechnicaltask.service;

import com.example.kameleoontechnicaltask.controller.dto.UserInfoForCreateDTO;
import com.example.kameleoontechnicaltask.model.User;
import com.example.kameleoontechnicaltask.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public void createUser(UserInfoForCreateDTO infoForCreate) {
        userRepository.saveAndFlush(
            new User(
                infoForCreate.getName(),
                infoForCreate.getEmail(),
                infoForCreate.getPassword(),
                LocalDateTime.now()
            )
        );
    }
}
