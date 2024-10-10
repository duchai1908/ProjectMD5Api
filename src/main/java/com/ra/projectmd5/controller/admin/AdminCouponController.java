package com.ra.projectmd5.controller.admin;

import com.ra.projectmd5.model.dto.response.ResponseDtoSuccess;
import com.ra.projectmd5.model.entity.Coupon;
import com.ra.projectmd5.model.service.ICouponService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admin/coupon")
@RequiredArgsConstructor
public class AdminCouponController {
    private final ICouponService couponService;
    @GetMapping
    public ResponseEntity<?> getAllCoupons(@PageableDefault(page = 0, size = 4, sort = "id", direction = Sort.Direction.ASC) Pageable pageable,
                                           @RequestParam(defaultValue = "") String search,
                                           @RequestParam(defaultValue = "id") String sortField,
                                           @RequestParam(defaultValue = "ASC") String sortDirection)  {
        Sort.Direction direction = Sort.Direction.fromString(sortDirection.toUpperCase());
        pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), direction, sortField);
        return new ResponseEntity<>(new ResponseDtoSuccess<>(couponService.getCoupons(pageable,search), HttpStatus.OK.value(), HttpStatus.OK),HttpStatus.OK);
    }
    @GetMapping("/{couponId}")
    public ResponseEntity<?> getCouponById(@PathVariable Long couponId) {
        return new ResponseEntity<>(new ResponseDtoSuccess<>(couponService.getCoupon(couponId), HttpStatus.OK.value(), HttpStatus.OK),HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<?> addNewCoupon(@RequestBody Coupon coupon) {
        return new ResponseEntity<>(new ResponseDtoSuccess<>(couponService.addCoupon(coupon), HttpStatus.CREATED.value(), HttpStatus.CREATED),HttpStatus.CREATED);
    }
    @DeleteMapping("/{couponId}")
    public ResponseEntity<?> deleteCouponById(@PathVariable Long couponId) {
        couponService.deleteCoupon(couponId);
        return new ResponseEntity<>(new ResponseDtoSuccess<>("Xoá coupon thành công", HttpStatus.OK.value(), HttpStatus.OK), HttpStatus.OK);
    }
}
