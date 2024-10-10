package com.ra.projectmd5.model.service;

import com.ra.projectmd5.model.entity.Coupon;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ICouponService {
    Page<Coupon> getCoupons(Pageable pageable,String search);
    Coupon addCoupon(Coupon coupon);
    Coupon getCoupon(Long couponId);
    void deleteCoupon(Long couponId);
    Coupon getCouponByCode(String code);
}
