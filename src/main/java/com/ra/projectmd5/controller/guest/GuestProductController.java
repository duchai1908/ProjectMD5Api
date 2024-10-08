package com.ra.projectmd5.controller.guest;

import com.ra.projectmd5.model.dto.response.ResponseDtoSuccess;
import com.ra.projectmd5.model.service.IProductService;
import com.ra.projectmd5.model.service.IRatingService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
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
    private final IRatingService ratingService;
    @GetMapping("/{productId}")
    public ResponseEntity<?> getProductById(@PathVariable Long productId) {
        return new ResponseEntity<>(new ResponseDtoSuccess<>(productService.getProductResponseByProductId(productId), HttpStatus.OK.value(), HttpStatus.OK), HttpStatus.OK);
    }

    @GetMapping("/relatedProduct/{categoryId}")
    public ResponseEntity<?> getRelatedProductById(@PathVariable Long categoryId) {
        return new ResponseEntity<>(new ResponseDtoSuccess<>(productService.getAllByCategoryId(categoryId), HttpStatus.OK.value(), HttpStatus.OK), HttpStatus.OK);
    }

    @GetMapping("/rating/{productId}")
    public ResponseEntity<?> getProductRating(@PageableDefault(page = 0, size = 4, sort = "id", direction = Sort.Direction.DESC) Pageable pageable, @PathVariable Long productId) {
        return new ResponseEntity<>(new ResponseDtoSuccess<>(ratingService.findAllRatingsByProductId(productId,pageable),HttpStatus.OK.value(), HttpStatus.OK),HttpStatus.OK);
    }


}
