package com.example.kameleoontechnicaltask.repository;

import com.example.kameleoontechnicaltask.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
