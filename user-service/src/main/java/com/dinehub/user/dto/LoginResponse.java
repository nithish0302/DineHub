package com.dinehub.user.dto;

import com.dinehub.user.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponse {
    private Long userId;
    private String userName;
    private String userEmail;
    private Role userRole;
    private String accessToken;
    private String refreshToken;
}
