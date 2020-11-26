package com.kakaopay.coupon.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@ToString
public class UserDTO {
    private long id;
    private String userId;
    private String password;
    private LocalDateTime createdAt;

    @Builder
    public UserDTO(long id, String userId, String password) {
        this.id = id;
        this.userId = userId;
        this.password = password;
        this.createdAt = LocalDateTime.now();
    }

    public static boolean hasNullDataBeforeSignup(UserDTO userDTO) {
        return userDTO.getUserId() == null || userDTO.getPassword() == null;
    }
}
