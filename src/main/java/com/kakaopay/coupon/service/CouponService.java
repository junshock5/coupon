package com.kakaopay.coupon.service;

import com.kakaopay.coupon.dto.CouponDTO;
import com.kakaopay.coupon.exception.coupon.CouponDeleteException;
import com.kakaopay.coupon.exception.coupon.CouponInsertException;
import com.kakaopay.coupon.exception.coupon.CouponNotFoundException;
import com.kakaopay.coupon.exception.coupon.CouponUpdateException;
import com.kakaopay.coupon.mapper.CouponMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
@Log4j2
public class CouponService {

    @Autowired
    private final CouponMapper couponMapper;

    public CouponService(CouponMapper couponMapper) {
        this.couponMapper = couponMapper;
    }

    public void batchInsert(Map<String, Object> map) {
        try {
            couponMapper.batchInsert(map);
        } catch (CouponUpdateException e) {
            throw new CouponInsertException("insertCoupon ERROR! 쿠폰 추가 메서드를 확인해주세요\n" + "Params : " + map);
        }
    }

    public void updateCoupon(CouponDTO couponDTO) {
        try {
            couponDTO.setUpdatedAt(LocalDateTime.now());
            couponDTO.setStatus(CouponDTO.Status.DEFAULT);
            couponDTO.setUserId(null);
            couponMapper.updateCoupon(couponDTO);
        } catch (CouponUpdateException e) {
            throw new CouponUpdateException("updateCoupon ERROR! 쿠폰 변경 메서드를 확인해주세요\n" + "Params : " + couponDTO);
        }
    }

    public void deleteId(long id) {
        if (id != 0) {
            couponMapper.deleteCoupon(id);
        } else {
            throw new CouponDeleteException("deleteId ERROR! 쿠폰 삭제 메서드를 확인해주세요\n" + "Params : " + id);
        }
    }

    public CouponDTO findByCode(String couponCode) {
        return couponMapper.findByCode(couponCode);
    }

    public void updateIsEnabledCouponById(CouponDTO couponDTO, long userId) throws CouponNotFoundException {
        couponDTO.setUserId(userId);
        couponDTO.setAssignedAt(LocalDateTime.now());
        couponMapper.setAvailable(couponDTO);
    }

    public void updateCouponUsedById(CouponDTO couponDTO, long userId) throws CouponNotFoundException {
        couponDTO.setUpdatedAt(LocalDateTime.now());
        couponDTO.setStatus(CouponDTO.Status.USED);
        couponMapper.setIsUsed(couponDTO);
    }

    public List<CouponDTO> getUserCoupons(long id) {
        return couponMapper.getUserCoupons(id);
    }
}
