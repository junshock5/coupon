package com.kakaopay.coupon.exception.coupon;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class CouponNotFoundException extends RuntimeException {
    private String couponCode;

    public CouponNotFoundException(String couponCode) {
        super("Coupon not found : " +couponCode);
    }
}
