package com.dinehub.user.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;


import java.time.LocalDateTime;

@Entity
@Table(name="users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(nullable = false)
    @NotBlank(message = "Name is required")
    @Size(min=3,max=40 ,message = "Name size should between 3 to 40")
    private String userName;

    @Column(unique = true,nullable = false)
    @NotBlank(message = "Email is required")
    @Email(message = "Email is not in valid format")
    private String userEmail;

    @NotBlank(message = "Password is required")
    @Size(min=6,message = "Password must contain at least 6 character")
    private String password;

    @Pattern(regexp = "^[0-9]{10}$",message = "Phone number must contain exactly 10 digits")
    private String userPhoneNumber;


    @Enumerated(EnumType.STRING)
    @NotNull(message = "Role is required")
    private Role userRole;

    private String userAddress;

    @CreationTimestamp
    @Column(nullable = false,updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime updatedAt;

}
