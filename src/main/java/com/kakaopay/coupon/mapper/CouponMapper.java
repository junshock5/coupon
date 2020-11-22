package com.kakaopay.coupon.mapper;

import com.kakaopay.coupon.dto.CouponDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface CouponMapper {
    public CouponDTO getCoupon(@Param("id") long id);

    int deleteCoupon(@Param("id") long id);

    public int register(CouponDTO couponDTO);

    int idCheck(long id);

    public int updateCoupon(CouponDTO couponDTO);

    long totalCount();

}
