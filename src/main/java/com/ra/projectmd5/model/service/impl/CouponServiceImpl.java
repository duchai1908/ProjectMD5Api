package com.ra.projectmd5.model.service.impl;

import com.ra.projectmd5.model.entity.Coupon;
import com.ra.projectmd5.model.repository.ICouponRepository;
import com.ra.projectmd5.model.service.ICouponService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.NoSuchElementException;


@Service
@RequiredArgsConstructor
public class CouponServiceImpl implements ICouponService {
    private final UUIDService uuidService;
    private final ICouponRepository couponRepository;
    /**
     * @Param pageable Pageable
     * @Param search String
     * @apiNote lấy ra danh sách toàn bộ coupon
     * @Auth Duc Hai (04/10/2024)
     * */
    @Override
    public Page<Coupon> getCoupons(Pageable pageable, String search) {
        Page<Coupon> coupons;
        if(search != null && !search.isEmpty()) {
            coupons = couponRepository.findAll(pageable);
        }else{
            coupons = couponRepository.findCouponBySkuContainsIgnoreCase(search,pageable);
        }
        return coupons;
    }

    /**
     * @param coupon Coupon
     * @apiNote Thêm mới coupon
     * @Auth Duc Hai (04/10/2024)
     * */
    @Override
    public Coupon addCoupon(Coupon coupon) {
        Coupon couponAdd = Coupon.builder()
                .sku(uuidService.couponUUID())
                .quantity(coupon.getQuantity())
                .percent(coupon.getPercent())
                .start_at(coupon.getStart_at())
                .end_at(coupon.getEnd_at())
                .created_at(new Date())
                .build();
        return couponRepository.save(couponAdd);
    }

    /**
     * @Param couponId Long
     * @apiNote Lấy chi tiết coupon theo id
     * @throws NoSuchElementException Không tìm thấy coupon
     * @Auth Duc Hai (04/10/2024)
     * */
    @Override
    public Coupon getCoupon(Long couponId) {
        return couponRepository.findById(couponId).orElseThrow(()-> new NoSuchElementException("Không tìm thấy coupon"));
    }

    /**
     * @Param couponId Long
     * @apiNote Xoá coupon
     * @Auth Duc Hai (04/10/2024)
     * */
    @Override
    public void deleteCoupon(Long couponId) {
        Coupon coupon = getCoupon(couponId);
        couponRepository.delete(coupon);
    }

    @Override
    public Coupon getCouponByCode(String code) {
        return couponRepository.getCouponBySku(code);
    }
}
