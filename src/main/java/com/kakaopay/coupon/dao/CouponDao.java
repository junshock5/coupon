package com.kakaopay.coupon.dao;

import com.kakaopay.coupon.dto.CouponDTO;
import com.kakaopay.coupon.mapper.CouponMapper;
import com.kakaopay.coupon.service.CouponService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Repository
@Log4j2
public class CouponDao {

    private final CouponMapper couponMapper;
    private final CouponService couponService;
    private static final String FILE_DIRECTORY_NAME = System.getProperty("user.dir") + File.separator;

    @Autowired
    public CouponDao(CouponMapper couponMapper, CouponService couponService) {
        this.couponMapper = couponMapper;
        this.couponService = couponService;
    }

    @PostConstruct
    public void init() throws IOException {
        loadInitialData();
    }

    private void loadInitialData() throws IOException {
        List<CouponDTO> list = new ArrayList();

        try (BufferedReader in = new BufferedReader(new FileReader(FILE_DIRECTORY_NAME + "InitialData.csv"))) {
            Map<Long, Integer> userMap = new HashMap<Long, Integer>();
            String line;
            while ((line = in.readLine()) != null) {
                line = line.replaceAll("\"","");
                String[] pair = line.split(";");
                long userId = Long.parseLong(pair[0]);
                String code = pair[1];
                CouponDTO.DiscountType discountType = CouponDTO.DiscountType.valueOf(pair[2]);
                long discountValue = Long.parseLong(pair[3]);
                CouponDTO.Status status = CouponDTO.Status.valueOf(pair[4]);
                LocalDateTime createdAt = LocalDateTime.parse(pair[5], DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                LocalDateTime endAt = LocalDateTime.parse(pair[7], DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

                CouponDTO couponTemp = CouponDTO.builder()
                        .userId(userId)
                        .code(code)
                        .discountType(discountType)
                        .discountValue(discountValue)
                        .status(status)
                        .createdAt(createdAt)
                        .endAt(endAt)
                        .build();
                list.add(couponTemp);
            }
            Map<String, Object> paramMap = new HashMap<String, Object>();
            paramMap.put("list", list);
            couponService.batchInsert(paramMap);
        }
    }
}