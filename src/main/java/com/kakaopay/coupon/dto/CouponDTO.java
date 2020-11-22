package com.kakaopay.coupon.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@ToString
public class CouponDTO {
    public enum DiscountType {
        WON, PERCENT
    }

    public enum Status {
        DEFAULT, DELETED
    }

    private Long id;
    private String name;
    private DiscountType discountType;
    private Long discountValue;
    private Status status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime endAt;

    @Builder
    public CouponDTO(Long id, String name, DiscountType discountType, Long discountValue, LocalDateTime updatedAt, LocalDateTime endAt, Status status) {
        this.id = id;
        this.name = name;
        this.discountType = discountType;
        this.discountValue = discountValue;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = updatedAt;
        this.endAt = endAt;
        this.status = status;
    }

    public static boolean hasNullData(CouponDTO couponDTO) {
        return couponDTO.getId() == 0 || couponDTO.getName() == null
                || couponDTO.getCreatedAt() == null;
    }
}
