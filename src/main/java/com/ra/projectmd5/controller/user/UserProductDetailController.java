package com.ra.projectmd5.controller.user;

import com.ra.projectmd5.model.dto.response.ResponseDtoSuccess;
import com.ra.projectmd5.model.service.IProductDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user/productDetail")
@RequiredArgsConstructor
public class UserProductDetailController {
    private final IProductDetailService productDetailService;
    @GetMapping("/{productDetailId}")
    public ResponseEntity<?> getProductDetailById(@PathVariable Long productDetailId) {
        return new ResponseEntity<>(new ResponseDtoSuccess<>(productDetailService.getProductDetailAndImage(productDetailId), HttpStatus.OK.value(), HttpStatus.OK), HttpStatus.OK);
    }

}
