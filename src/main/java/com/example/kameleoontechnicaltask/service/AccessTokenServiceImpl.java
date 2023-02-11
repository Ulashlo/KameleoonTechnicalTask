package com.example.kameleoontechnicaltask.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import static java.lang.String.format;

@Service
@RequiredArgsConstructor
public class AccessTokenServiceImpl implements AccessTokenService {
    @Override
    public String generateAccessToken(String name, String password) {
        final var encodedPart = Base64.getEncoder().encodeToString(
            format("%s:%s", name, password).getBytes(StandardCharsets.UTF_8)
        );
        return format("Basic %s", encodedPart);
    }
}
