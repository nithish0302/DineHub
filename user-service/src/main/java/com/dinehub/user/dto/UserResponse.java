package com.dinehub.user.dto;

import com.dinehub.user.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {

    private Long userId;
    private String userName;
    private String userEmail;
    private String userPhoneNumber;
    private String userAddress;
    private Role userRole;
}
