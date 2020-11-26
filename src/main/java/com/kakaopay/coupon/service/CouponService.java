package com.kakaopay.coupon.service;

import com.kakaopay.coupon.dto.CouponDTO;
import com.kakaopay.coupon.exception.coupon.CouponDeleteException;
import com.kakaopay.coupon.exception.coupon.CouponInsertException;
import com.kakaopay.coupon.exception.coupon.CouponUpdateException;
import com.kakaopay.coupon.mapper.CouponMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        int insertCount = couponMapper.batchInsert(map);

        if (insertCount != 1) {
            throw new CouponInsertException("insertCoupon ERROR! 쿠폰 추가 메서드를 확인해주세요\n" + "Params : " + map);
        }
    }

    public void updateCoupon(CouponDTO CouponDTO) {
        try {
            couponMapper.updateCoupon(CouponDTO);
        } catch (CouponUpdateException e) {
            throw new CouponUpdateException("updateCoupon ERROR! 쿠폰 변경 메서드를 확인해주세요\n" + "Params : " + CouponDTO);
        }
    }

    public void deleteId(long id) {
        if (id != 0) {
            couponMapper.deleteCoupon(id);
        } else {
            throw new CouponDeleteException("deleteId ERROR! 쿠폰 삭제 메서드를 확인해주세요\n" + "Params : " + id);
        }
    }
}
