package com.kakaopay.coupon.utils;

import com.kakaopay.coupon.exception.coupon.InvalidPayloadException;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.regex.Pattern;

public class CouponUtils {
    private static List<Integer> percentages = Arrays.asList(10,20,30,40,50);
    public CouponUtils() throws Exception {
        throw new Exception("you do not need to construct CouponUtils class!");
    }

    public static String getUUIDCouponCode() {
        // format : uuid
        return UUID.randomUUID().toString();
    }

    public static LocalDateTime getRandomExpiredAt(LocalDateTime fromDate) {
        // add random expired days from now date (1 day ~ 7 days)
        return fromDate.plusDays((long)(Math.random() * 7) + 1);
    }

    public static Integer getRandomDiscountRate() {
        return percentages.get((int) (Math.random() * (percentages.size())));
    }

    public static void validateCouponCode(String code) throws InvalidPayloadException {
        if(!Pattern.matches(ValidationRegex.COUPONCODE, code)) {
            throw new InvalidPayloadException("Invalid format of coupon code.");
        }
    }
}
