package com.ra.projectmd5.controller.guest;

import com.ra.projectmd5.model.dto.response.ResponseDtoSuccess;
import com.ra.projectmd5.model.service.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/product")
@RequiredArgsConstructor
public class GuestProductController {
    private final IProductService productService;
    @GetMapping("/{productId}")
    public ResponseEntity<?> getProductById(@PathVariable Long productId) {
        return new ResponseEntity<>(new ResponseDtoSuccess<>(productService.getProductResponseByProductId(productId), HttpStatus.OK.value(), HttpStatus.OK), HttpStatus.OK);
    }
}
