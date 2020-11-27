package com.kakaopay.coupon.utils;

public class ValidationRegex {
    // 숫자, 문자, 특수문자 각각 1개 이상 포함하여 최소 8자리 이상
    public static final String PASSWORD = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[$@$!%*#?&])[A-Za-z\\d$@$!%*#?&]{8,}$";
    // UUID format
    public static final String COUPONCODE = "^[0-9a-f]{8}-[0-9a-f]{4}-4[0-9a-f]{3}-[89ab][0-9a-f]{3}-[0-9a-f]{12}$";

    public ValidationRegex() throws Exception {
        throw new Exception("you should not construct this class! use static properties instead.");
    }
}
