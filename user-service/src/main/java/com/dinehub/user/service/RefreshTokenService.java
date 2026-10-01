package com.dinehub.user.service;

import com.dinehub.user.entity.RefreshToken;

public interface RefreshTokenService {

    public RefreshToken createRefreshToken(Long userId);

    String refreshAccessToken(String token);

    void deleteRefreshToken(String token);
}
