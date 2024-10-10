package com.ra.projectmd5.controller.user;

import com.ra.projectmd5.model.dto.response.ResponseDtoSuccess;
import com.ra.projectmd5.model.service.ICouponService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user/coupon")
@RequiredArgsConstructor
public class UserCouponController {
    private final ICouponService couponService;
    @GetMapping("/{sku}")
    public ResponseEntity<?> getUserCoupon(@PathVariable String sku) {
        return new ResponseEntity<>(new ResponseDtoSuccess<>(couponService.getCouponByCode(sku), HttpStatus.OK.value(), HttpStatus.OK),HttpStatus.OK);
    }
}
