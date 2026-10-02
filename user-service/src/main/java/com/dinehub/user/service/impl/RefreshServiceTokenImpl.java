package com.dinehub.user.service.impl;

import com.dinehub.user.entity.RefreshToken;
import com.dinehub.user.entity.User;
import com.dinehub.user.exception.InvalidRefreshTokenException;
import com.dinehub.user.exception.UserNotFoundException;
import com.dinehub.user.repository.RefreshTokenRepository;
import com.dinehub.user.repository.UserRepository;
import com.dinehub.user.security.JwtService;
import com.dinehub.user.service.RefreshTokenService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshServiceTokenImpl implements RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;
    private final UserRepository userRepository;
    private final JwtService jwtService;

    @Value("${refresh-token.expiration}")
    private long refreshTokenExpiration;

    @Override
    public RefreshToken createRefreshToken(Long userId) {

        RefreshToken refreshToken = new RefreshToken();

        refreshToken.setUserId(userId);
        refreshToken.setToken(UUID.randomUUID().toString());
        refreshToken.setExpiryDate(
                LocalDateTime.now()
                        .plusSeconds(refreshTokenExpiration / 1000)
        );

        return refreshTokenRepository.save(refreshToken);
    }

    @Override
    public String refreshAccessToken(String token) {

        RefreshToken refreshToken =
                refreshTokenRepository.findByToken(token)
                        .orElseThrow(() ->
                                new InvalidRefreshTokenException(
                                        "Invalid refresh token"
                                )
                        );

        if (refreshToken.getExpiryDate()
                .isBefore(LocalDateTime.now())) {

            refreshTokenRepository.deleteByToken(token);

            throw new InvalidRefreshTokenException(
                    "Refresh token expired"
            );
        }

        User user =
                userRepository.findById(refreshToken.getUserId())
                        .orElseThrow(() ->
                                new UserNotFoundException(
                                        "User Not Found"
                                )
                        );

        return jwtService.generateToken(
                user.getUserEmail()
        );
    }

    @Override
    @Transactional
    public void deleteRefreshToken(String token) {

        refreshTokenRepository.deleteByToken(token);
    }
}