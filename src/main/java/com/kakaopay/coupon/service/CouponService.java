package com.kakaopay.coupon.service;

import com.kakaopay.coupon.dto.CouponDTO;
import com.kakaopay.coupon.exception.coupon.CouponDeleteException;
import com.kakaopay.coupon.exception.coupon.CouponInsertException;
import com.kakaopay.coupon.exception.coupon.CouponUpdateException;
import com.kakaopay.coupon.exception.DuplicateIdException;
import com.kakaopay.coupon.mapper.CouponMapper;
import com.sun.istack.internal.NotNull;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class CouponService {

    @Autowired
    private final CouponMapper couponMapper;

    public CouponService(CouponMapper couponMapper) {
        this.couponMapper = couponMapper;
    }

    public void register(CouponDTO CouponDTO) {
        boolean duplIdResult = isDuplicatedId(CouponDTO.getId());
        if (duplIdResult) {
            throw new DuplicateIdException("중복된 아이디입니다.");
        }
        int insertCount = couponMapper.register(CouponDTO);

        if (insertCount != 1) {
            log.error("insertCoupon ERROR! {}", CouponDTO);
            throw new CouponInsertException("insertCoupon ERROR! 쿠폰 추가 메서드를 확인해주세요\n" + "Params : " + CouponDTO);
        }
    }

    public void updateCoupon(@NotNull CouponDTO CouponDTO) {
        try {
            couponMapper.updateCoupon(CouponDTO);
        } catch (CouponUpdateException e) {
            log.error("updateCoupon ERROR! {}", CouponDTO);
            throw new CouponUpdateException("updateCoupon ERROR! 쿠폰 변경 메서드를 확인해주세요\n" + "Params : " + CouponDTO);
        }
    }

    public void deleteId(long id) {
        if (id != 0) {
            couponMapper.deleteCoupon(id);
        } else {
            log.error("deleteId ERROR! {}", id);
            throw new CouponDeleteException("deleteId ERROR! 쿠폰 삭제 메서드를 확인해주세요\n" + "Params : " + id);
        }
    }

    public boolean isDuplicatedId(long id) {
        return couponMapper.idCheck(id) == 1;
    }

    public long totalCount() {
        return couponMapper.totalCount();
    }

}
