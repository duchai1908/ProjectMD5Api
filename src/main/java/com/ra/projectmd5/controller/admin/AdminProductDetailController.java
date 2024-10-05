package com.ra.projectmd5.controller.admin;

import com.ra.projectmd5.exception.DataExistException;
import com.ra.projectmd5.model.dto.request.ProductDetailRequest;
import com.ra.projectmd5.model.dto.response.ResponseDtoSuccess;
import com.ra.projectmd5.model.service.IProductDetailService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin/productDetail")
public class AdminProductDetailController {
    private final IProductDetailService productDetailService;
    @GetMapping("/product/{productId}")
    public ResponseEntity<?> getAllProductDetailByProductId(@PageableDefault(page = 0,size = 2, sort = "id",direction = Sort.Direction.ASC) Pageable pageable, @RequestParam(defaultValue = "" ) String search,@PathVariable Long productId) {
        return new ResponseEntity<>(new ResponseDtoSuccess<>(productDetailService.findAllProductDetail(pageable,search,productId), HttpStatus.OK.value(), HttpStatus.OK),HttpStatus.OK);
    }

    @GetMapping("/{productDetailId}")
    public ResponseEntity<?> getProductDetailById(@PathVariable Long productDetailId) {
        return new ResponseEntity<>(new ResponseDtoSuccess<>(productDetailService.getProductDetailById(productDetailId), HttpStatus.OK.value(), HttpStatus.OK),HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<?> addNewProductDetail(@Valid @ModelAttribute ProductDetailRequest productDetailRequest) throws DataExistException {
        return new ResponseEntity<>(new ResponseDtoSuccess<>(productDetailService.saveProductDetail(productDetailRequest),HttpStatus.CREATED.value(), HttpStatus.CREATED),HttpStatus.CREATED);
    }

    @PutMapping("/{productDetailId}")
    public ResponseEntity<?> updateProductDetailById(@PathVariable Long productDetailId, @Valid @ModelAttribute ProductDetailRequest productDetailRequest) throws DataExistException {
        return new ResponseEntity<>(new ResponseDtoSuccess<>(productDetailService.updateProductDetail(productDetailRequest,productDetailId),HttpStatus.OK.value(), HttpStatus.OK),HttpStatus.OK);
    }

    @DeleteMapping("/{productDetailId}")
    public ResponseEntity<?> deleteProductDetailById(@PathVariable Long productDetailId) {
        productDetailService.deleteProductDetail(productDetailId);
        return new ResponseEntity<>(new ResponseDtoSuccess<>("Delete Product Detail succesfully", HttpStatus.OK.value(), HttpStatus.OK),HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> getAllProductDetails() {
        return new ResponseEntity<>(new ResponseDtoSuccess<>(productDetailService.findAllProductDetailByNothing(), HttpStatus.OK.value(), HttpStatus.OK),HttpStatus.OK);
    }

    @GetMapping("/productId/{productId}")
    public ResponseEntity<?> getAllProductDetailsByProductId(@PathVariable Long productId) {
        return new ResponseEntity<>(new ResponseDtoSuccess<>(productDetailService.findAllProductDetailByProductId(productId), HttpStatus.OK.value(), HttpStatus.OK),HttpStatus.OK);

    @GetMapping("/{colorId}/{sizeId}/{productId}")
    public ResponseEntity<?> getProductDetailBySizeColorId(@PathVariable Long colorId, @PathVariable Long sizeId, @PathVariable Long productId) {
        return new ResponseEntity<>(new ResponseDtoSuccess<>(productDetailService.getProductDetailByColorAndSize(colorId, sizeId, productId), HttpStatus.OK.value(), HttpStatus.OK),HttpStatus.OK);

    }
}
