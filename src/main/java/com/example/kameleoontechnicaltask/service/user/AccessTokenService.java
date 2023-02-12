package com.example.kameleoontechnicaltask.service.user;

/**
 * Service provides functionality for working with an access token
 */
public interface AccessTokenService {
    /**
     * Generate an access token by user's credentials.
     *
     * @param name user's name
     * @param password user's password
     * @return access token
     */
    String generateAccessToken(String name, String password);
}
