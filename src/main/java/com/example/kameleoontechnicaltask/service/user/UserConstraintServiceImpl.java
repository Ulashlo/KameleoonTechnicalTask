package com.example.kameleoontechnicaltask.service.user;

import com.example.kameleoontechnicaltask.controller.dto.user.UserInfoForCreateDTO;
import com.example.kameleoontechnicaltask.exceprion.CustomConstraintViolationException;
import com.example.kameleoontechnicaltask.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static java.lang.String.format;

@Service
@RequiredArgsConstructor
public class UserConstraintServiceImpl implements UserConstraintService {
    private final UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public void checkUserAccountCreate(UserInfoForCreateDTO dto) {
        if (userRepository.existsByName(dto.getName())) {
            throw new CustomConstraintViolationException(
                format("User with name = %s is already exist", dto.getName())
            );
        }
        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new CustomConstraintViolationException(
                format("User with email = %s is already exist", dto.getEmail())
            );
        }
    }
}
