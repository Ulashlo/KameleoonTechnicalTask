package com.example.kameleoontechnicaltask.service.user;

import com.example.kameleoontechnicaltask.controller.dto.user.UserInfoForCreateDTO;
import com.example.kameleoontechnicaltask.exceprion.CustomConstraintViolationException;
import com.example.kameleoontechnicaltask.model.UserEntity;

/**
 * Service provides functionality for checking constraints for {@linkplain UserEntity} operations
 */
public interface UserConstraintService {
    /**
     * Check constraints for {@linkplain UserEntity} creation.
     *
     * @param dto user info for create
     * @throws CustomConstraintViolationException if it is impossible to create user with given params
     */
    void checkUserAccountCreate(UserInfoForCreateDTO dto);
}
