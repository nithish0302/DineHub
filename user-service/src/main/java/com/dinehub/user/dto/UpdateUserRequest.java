package com.dinehub.user.dto;

import com.dinehub.user.entity.Role;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UpdateUserRequest {

    @NotBlank(message = "Name is required")
    @Size(min = 3, max = 40, message = "Name size should between 3 and 40")
    private String userName;

    @Pattern(
            regexp = "^[0-9]{10}$",
            message = "Phone number must contain exactly 10 digits"
    )
    private String userPhoneNumber;

    private String userAddress;

    private Role userRole;
}