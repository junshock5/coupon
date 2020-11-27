package com.kakaopay.coupon.controller;

import com.kakaopay.coupon.dto.CouponDTO;
import com.kakaopay.coupon.dto.UserDTO;
import com.kakaopay.coupon.exception.coupon.CouponInsertException;
import com.kakaopay.coupon.exception.coupon.CouponMemberNotMatchException;
import com.kakaopay.coupon.exception.coupon.CouponUseException;
import com.kakaopay.coupon.mapper.CouponMapper;
import com.kakaopay.coupon.service.CouponService;
import com.kakaopay.coupon.utils.CouponUtils;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.CompletableFuture;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@RunWith(SpringRunner.class)
class CouponControllerTest {
    @InjectMocks
    private CouponService couponService;

    @InjectMocks
    private CouponController couponController;

    // 새로운 쿠폰 객체 리스트를 생성하여 반환한다.
    public List<CouponDTO> generateCouponList() {
        MockitoAnnotations.initMocks(this); // mock all the field having @Mock annotation
        List<CouponDTO> couponDTOList = new ArrayList<CouponDTO>();
        for (int i = 0; i < 10000; ++i) {
            CouponDTO couponTemp = CouponDTO.builder()
                    .id(Long.valueOf(i))
                    .code(CouponUtils.getUUIDCouponCode())
                    .status(CouponDTO.Status.DEFAULT)
                    .createdAt(LocalDateTime.now())
                    .build();
            couponDTOList.add(couponTemp);
        }
        return couponDTOList;
    }

    @Test
    void registerCoupons() {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("list", generateCouponList());
        try {
            couponService.batchInsert(paramMap);
        } catch (Exception e) {
            fail("Should not have thrown any exception");
        }
    }

    @Test
    void cancelCouponById() {
        registerCoupons();
        long id = 1;
        CouponDTO couponTemp = CouponDTO.builder()
                .userId(id)
                .status(CouponDTO.Status.DEFAULT)
                .createdAt(LocalDateTime.now())
                .build();
        CouponController.CouponRequest couponRequest = null;
        try {
            couponService.updateCoupon(couponTemp);
        } catch (Exception e) {
            fail("Should not have thrown any exception");
        }
    }

    @Test
    void deleteId() {
        registerCoupons();
        long id = 1;
        try {
            couponService.deleteId(id);
        } catch (Exception e) {
            fail("Should not have thrown any exception");
        }
    }

    @Test
    void giveNotUsingCoupon() {
        MockitoAnnotations.initMocks(this); // mock all the field having @Mock annotation

        String fakeCode = CouponUtils.getUUIDCouponCode();
        List<CouponDTO> couponDTOList = new ArrayList<CouponDTO>();

        CouponDTO coupon = CouponDTO.builder()
                .id(1L)
                .userId(1L)
                .code(fakeCode)
                .status(CouponDTO.Status.DEFAULT)
                .createdAt(LocalDateTime.now())
                .build();
        couponDTOList.add(coupon);

        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("list", couponDTOList);
        couponService.batchInsert(paramMap);

        given(couponService.findByCode(fakeCode)).willReturn(coupon);

        try {
            if (coupon.getUserId() != null) {
                if (!coupon.getUserId().equals(1L)) {
                    throw new CouponMemberNotMatchException(fakeCode);
                }
            }
            couponService.updateIsEnabledCouponById(coupon, 1L);

        } catch (Exception e) {
            fail("Should not have thrown any exception");
        }
    }

    @Test
    void getUserCoupons() {
        MockitoAnnotations.initMocks(this); // mock all the field having @Mock annotation
        long memberId = 123L;
        UserDTO fakeMember = UserDTO.builder().id(memberId).build();
        List<CouponDTO> fakeCoupons = Arrays.asList(
                CouponDTO.builder().code(UUID.randomUUID().toString()).build(),
                CouponDTO.builder().code(UUID.randomUUID().toString()).build());
        given(couponService.getUserCoupons(memberId)).willReturn(fakeCoupons);

        List<CouponDTO> result = couponService.getUserCoupons(memberId);
        assertThat(result.equals(fakeCoupons));
    }

    @Test
    void useUserCoupons() {
        MockitoAnnotations.initMocks(this); // mock all the field having @Mock annotation
        String fakeCouponCode = UUID.randomUUID().toString();
        long memberId = 1L;

        CouponDTO couponTemp = CouponDTO.builder()
                .userId(memberId)
                .code(fakeCouponCode)
                .status(CouponDTO.Status.DEFAULT)
                .createdAt(LocalDateTime.now())
                .build();

        assertThat("TRUE".equals(couponService.updateCouponUsedById(couponTemp)));
    }

    @Test
    void getExpiredCoupon() {
        registerCoupons();
        try {
            couponService.findExpiredToday();
        } catch (Exception e) {
            fail("Should not have thrown any exception");
        }
    }
}