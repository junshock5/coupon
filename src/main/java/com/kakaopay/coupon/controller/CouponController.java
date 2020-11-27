package com.kakaopay.coupon.controller;

import com.kakaopay.coupon.dto.CouponDTO;
import com.kakaopay.coupon.exception.coupon.CouponMemberNotMatchException;
import com.kakaopay.coupon.exception.coupon.CouponUseException;
import com.kakaopay.coupon.exception.coupon.InvalidPayloadException;
import com.kakaopay.coupon.exception.user.UserNotFoundException;
import com.kakaopay.coupon.service.CouponService;
import com.kakaopay.coupon.service.UserService;
import com.kakaopay.coupon.utils.CouponUtils;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;


@Api(tags = {"coupons"})
@RestController
@RequestMapping("/coupons")
@Log4j2
public class CouponController {

    private final CouponService couponService;
    private final UserService userService;
    private static final ResponseEntity<CouponResponse> FAIL_RESPONSE = new ResponseEntity<CouponResponse>(HttpStatus.BAD_REQUEST);
    private static final ResponseEntity<CouponResponse> SUCCESS_RESPONSE = new ResponseEntity<CouponResponse>(HttpStatus.OK);

    @Autowired
    public CouponController(CouponService couponService, UserService userService) {
        this.couponService = couponService;
        this.userService = userService;
    }

    /**
     * 쿠폰 추가 메서드.
     */
    @PostMapping("/{num}")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<CouponResponse> registerCoupons(long num, @RequestBody CouponDTO couponDTO) {
        ResponseEntity<CouponResponse> responseEntity = SUCCESS_RESPONSE;
        if (num > 100000) {
            responseEntity = FAIL_RESPONSE;
        }
        if (CouponDTO.hasNullData(couponDTO)) {
            throw new NullPointerException("쿠폰 추가시 필수 데이터를 모두 입력 해야 합니다.");
        }

        List<CouponDTO> list = new ArrayList();

        for (int i = 0; i < num; ++i) {
            CouponDTO couponTemp = CouponDTO.builder()
                    .id(couponDTO.getId())
                    .code(CouponUtils.getUUIDCouponCode())
                    .discountType(couponDTO.getDiscountType())
                    .discountValue(couponDTO.getDiscountValue())
                    .status(CouponDTO.Status.DEFAULT)
                    .endAt(couponDTO.getEndAt())
                    .createdAt(LocalDateTime.now())
                    .build();
            list.add(couponTemp);
        }

        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("list", list);
        couponService.batchInsert(paramMap);
        return responseEntity;
    }


    /**
     * 쿠폰 취소 메서드.
     */
    @PutMapping("/{id}")
    public ResponseEntity<CouponResponse> cancelCouponById(@RequestBody CouponRequest couponRequest) {
        ResponseEntity<CouponResponse> responseEntity = SUCCESS_RESPONSE;
        CouponDTO CouponDTO = couponRequest.getCouponDTO();
        try {
            couponService.updateCoupon(CouponDTO);
        } catch (NullPointerException e) {
            log.error("updateUser 실패", e);
            responseEntity = FAIL_RESPONSE;
        }
        return responseEntity;
    }

    /**
     * 쿠폰 ID 삭제 메서드.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<CouponResponse> deleteId(@RequestBody long id) {
        ResponseEntity<CouponResponse> responseEntity = SUCCESS_RESPONSE;
        try {
            couponService.deleteId(id);
        } catch (RuntimeException e) {
            log.info("deleteID 실패", e);
            responseEntity = FAIL_RESPONSE;
        }
        return responseEntity;
    }

    /**
     * 쿠폰 사용자 지급 메서드.
     */
    @PatchMapping("/{coupon_code}")
    public ResponseEntity<CouponResponse> giveNotUsingCoupon(@RequestBody CouponUseRequest couponUseRequest) throws CouponMemberNotMatchException {
        ResponseEntity<CouponResponse> responseEntity = SUCCESS_RESPONSE;
        CouponUtils.validateCouponCode(couponUseRequest.getCouponCode());

        CouponDTO coupon = couponService.findByCode(couponUseRequest.getCouponCode());
        if (coupon.getUserId() != null) {
            if (!coupon.getUserId().equals(couponUseRequest.getUserId())) {
                throw new CouponMemberNotMatchException(couponUseRequest.getCouponCode());
            }
        }
        couponService.updateIsEnabledCouponById(coupon, couponUseRequest.getUserId());

        return responseEntity;
    }

    /**
     * 지급된 쿠폰 조회 메서드.
     */
    @GetMapping("/{userId}")
    public UserCouponsResponse getUserCoupons(long userId) throws UserNotFoundException {
        List<CouponDTO> couponDTOList = couponService.getUserCoupons(userId);
        return new UserCouponsResponse(couponDTOList);
    }

    /**
     * 지급된 쿠폰 사용 메서드.
     */
    @PutMapping("/{coupon_code}")
    public ResponseEntity<CouponResponse> useUserCoupons(@RequestBody CouponUseRequest couponUseRequest) throws CouponUseException {
        ResponseEntity<CouponResponse> responseEntity = SUCCESS_RESPONSE;
        CouponUtils.validateCouponCode(couponUseRequest.getCouponCode());

        CouponDTO coupon = couponService.findByCode(couponUseRequest.getCouponCode());
        if (coupon != null) {
            if (coupon.getUserId().equals(couponUseRequest.getUserId()) && coupon.getStatus() != CouponDTO.Status.USED) {
                if (!couponService.updateCouponUsedById(coupon))
                    throw new CouponUseException(couponUseRequest.getCouponCode());
            } else
                responseEntity = FAIL_RESPONSE;
        }
        return responseEntity;
    }

    /**
     * 만료된 쿠폰 조회 메서드.
     */
    @GetMapping("/expired")
    public UserCouponsResponse getExpiredCoupon() {
        List<CouponDTO> couponDTOList = couponService.findExpiredToday();
        return new UserCouponsResponse(couponDTOList);
    }

    @Getter
    public static class CouponRequest {
        @NonNull
        private CouponDTO couponDTO;
    }

    @Getter
    private static class CouponUseRequest {
        private String couponCode;
        private Long userId;
    }

    @Getter
    @AllArgsConstructor
    @RequiredArgsConstructor
    static class CouponResponse {
        enum CouponStatus {
            SUCCESS, FAIL, DELETED
        }

        @NonNull
        private CouponStatus result;
        private CouponDTO couponDTO;

        // success의 경우 memberInfo의 값을 set해줘야 하기 때문에 new 하도록 해준다.
        private static final CouponResponse FAIL = new CouponResponse(CouponStatus.FAIL);

        private static CouponResponse success(CouponDTO couponDTO) {
            return new CouponResponse(CouponStatus.SUCCESS, couponDTO);
        }

    }

    @Getter
    @AllArgsConstructor
    public static class UserCouponsResponse {
        private List<CouponDTO> couponDTOList;
    }
}
