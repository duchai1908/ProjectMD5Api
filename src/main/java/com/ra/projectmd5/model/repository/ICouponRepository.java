package com.ra.projectmd5.model.repository;

import com.ra.projectmd5.model.entity.Coupon;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICouponRepository extends JpaRepository<Coupon, Long> {
    Page<Coupon> findCouponBySkuContainsIgnoreCase(String sku, Pageable pageable);
}
