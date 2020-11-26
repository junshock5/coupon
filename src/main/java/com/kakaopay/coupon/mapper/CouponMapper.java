package com.kakaopay.coupon.mapper;

import com.kakaopay.coupon.dto.CouponDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Map;


@Mapper
public interface CouponMapper {
    CouponDTO getCoupon(@Param("id") long id);

    int deleteCoupon(@Param("id") long id);

    int batchInsert(Map<String, Object> map);

    int idCheck(long id);

    int updateCoupon(CouponDTO couponDTO);
}
