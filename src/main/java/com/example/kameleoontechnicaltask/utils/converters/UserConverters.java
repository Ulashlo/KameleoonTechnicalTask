package com.example.kameleoontechnicaltask.utils.converters;

import com.example.kameleoontechnicaltask.controller.dto.user.UserLinkDTO;
import com.example.kameleoontechnicaltask.model.UserEntity;

public class UserConverters {
    public static UserLinkDTO toUserLinkDTO(UserEntity user) {
        return new UserLinkDTO(
            user.getId(),
            user.getName(),
            user.getEmail()
        );
    }
}
