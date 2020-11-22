package com.kakaopay.coupon.controller;

import com.kakaopay.coupon.dto.CouponDTO;
import com.kakaopay.coupon.exception.coupon.InvalidPayloadException;
import com.kakaopay.coupon.service.CouponService;
import com.kakaopay.coupon.service.UserService;
import com.kakaopay.coupon.utils.CouponUtils;
import io.swagger.annotations.Api;
import lombok.Getter;
import lombok.NonNull;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


@Api(tags = {"coupons"})
@RestController
@RequestMapping("/coupons")
@Log4j2
public class CouponController {

    private final CouponService couponService;
    private final UserService userService;

    @Autowired
    public CouponController(CouponService couponService, UserService userService) {
        this.couponService = couponService;
        this.userService = userService;
    }

    /**
     * 쿠폰 한개 추가 메서드.
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void register(@RequestBody CouponDTO couponDTO) {
        if (CouponDTO.hasNullData(couponDTO)) {
            throw new NullPointerException("쿠폰 추가시 필수 데이터를 모두 입력 해야 합니다.");
        }
        couponService.register(couponDTO);
    }

    /**
     * 쿠폰 다수 추가 메서드.
     */
    @PostMapping("/{coupon_count}")
    @ResponseStatus(HttpStatus.CREATED)
    public void registerCoupons(long num, @RequestBody CouponDTO couponDTO) {
        if (num > 100000) {
            throw new InvalidPayloadException("The number of coupon should be less than 1000000.");
        }
        if (CouponDTO.hasNullData(couponDTO)) {
            throw new NullPointerException("쿠폰 추가시 필수 데이터를 모두 입력 해야 합니다.");
        }
        for (int i = 0; i < num; ++i) {
            CouponDTO couponTemp = CouponDTO.builder()
                    .code(CouponUtils.getUUIDCouponCode())
                    .discountType(couponDTO.getDiscountType())
                    .discountValue(couponDTO.getDiscountValue())
                    .status(CouponDTO.Status.DEFAULT)
                    .endAt(couponDTO.getEndAt())
                    .build();

            couponService.register(couponTemp);
        }
    }


    /**
     * 쿠폰 수정 메서드.
     */
    @PatchMapping("{userId}")
    public HttpStatus updateCoupon(@RequestBody CouponRequest couponRequest) {
        CouponDTO CouponDTO = couponRequest.getCouponDTO();

        try {
            couponService.updateCoupon(CouponDTO);
        } catch (NullPointerException e) {
            log.error("updateUser 실패", e);
            return HttpStatus.BAD_REQUEST;
        }
        return HttpStatus.OK;
    }

    /**
     * 쿠폰 ID 삭제 메서드.
     */
    @DeleteMapping("{userId}")
    public HttpStatus deleteId(@RequestBody long id) {
        try {
            couponService.deleteId(id);
        } catch (RuntimeException e) {
            log.info("deleteID 실패", e);
        }
        return HttpStatus.OK;
    }

    /**
     * 쿠폰 전체 수 반환 메서드.
     */
    @GetMapping("totalCount")
    public long totalCount() {
        try {
            return couponService.totalCount();
        } catch (RuntimeException e) {
            log.info("totalCount 실패", e);
        }
        return 0;
    }

    @Getter
    private static class CouponRequest {
        @NonNull
        private CouponDTO CouponDTO;
    }

}
