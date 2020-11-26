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
    private Long userId;
    private String code;
    private DiscountType discountType;
    private Long discountValue;
    private Status status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime endAt;
    private LocalDateTime assignedAt;

    @Builder
    public CouponDTO(Long id, Long userId, String code, DiscountType discountType, Long discountValue, Status status, LocalDateTime createdAt, LocalDateTime updatedAt, LocalDateTime endAt, LocalDateTime assignedAt) {
        this.id = id;
        this.userId = userId;
        this.code = code;
        this.discountType = discountType;
        this.discountValue = discountValue;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.endAt = endAt;
        this.assignedAt = assignedAt;
    }

    public static boolean hasNullData(CouponDTO couponDTO) {
        return couponDTO.getCode() == null
                || couponDTO.getDiscountType() == null
                || couponDTO.getDiscountValue() == null
                || couponDTO.getEndAt() == null;
    }
}
