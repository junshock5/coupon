package com.kakaopay.coupon.exception.user;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class UserNotFoundException extends RuntimeException {
    private long memberId;

    public UserNotFoundException(long memberId) {
        super("Member not found : " +memberId);
    }
}
