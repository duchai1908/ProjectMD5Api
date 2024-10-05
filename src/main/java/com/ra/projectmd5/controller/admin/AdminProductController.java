package com.ra.projectmd5.controller.admin;

import com.ra.projectmd5.exception.DataExistException;
import com.ra.projectmd5.model.dto.request.ProductRequest;
import com.ra.projectmd5.model.dto.request.ProductUpdateRequest;
import com.ra.projectmd5.model.dto.response.ResponseDtoSuccess;
import com.ra.projectmd5.model.entity.Product;
import com.ra.projectmd5.model.service.IProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admin/products")
@RequiredArgsConstructor
public class AdminProductController {
    private final IProductService productService;
//    @GetMapping
//    public ResponseEntity<?> getAllProducts(@PageableDefault(page = 0,size = 2, sort = "id",direction = Sort.Direction.ASC) Pageable pageable, @RequestParam(defaultValue = "" ) String search, @RequestParam(defaultValue = "") String filter ) {
//        return new ResponseEntity<>(new ResponseDtoSuccess<>(productService.findAll(pageable,search),HttpStatus.OK.value(),HttpStatus.OK),HttpStatus.OK);
//    }

    @GetMapping("/filter")
    public ResponseEntity<?> getAllProducts(@PageableDefault(page = 0, size = 2, sort = "id", direction = Sort.Direction.ASC) Pageable pageable,
                                            @RequestParam(defaultValue = "") String search,
                                            @RequestParam(required = false) Double minPrice,
                                            @RequestParam(required = false) Double maxPrice,
                                            @RequestParam(required = false) Long colorId,
                                            @RequestParam(defaultValue = "none") String sortOption) {
        Page<Product> products = productService.findAllWithFilter(pageable, search, minPrice, maxPrice, colorId, sortOption);

        return new ResponseEntity<>(new ResponseDtoSuccess<>(products, HttpStatus.OK.value(), HttpStatus.OK), HttpStatus.OK);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<?> getProductById(@PathVariable Long productId) {
        return new ResponseEntity<>(new ResponseDtoSuccess<>(productService.getProductById(productId),HttpStatus.OK.value(),HttpStatus.OK),HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> addProduct(@ModelAttribute ProductRequest productRequest) throws DataExistException {
        return new ResponseEntity<>(new ResponseDtoSuccess<>(productService.saveProduct(productRequest), HttpStatus.CREATED.value(),HttpStatus.CREATED),HttpStatus.CREATED);
    }

    @PutMapping("/{productId}")
    public ResponseEntity<?> updateProduct(@PathVariable Long productId, @ModelAttribute ProductUpdateRequest productRequest) throws DataExistException {
        return new ResponseEntity<>(new ResponseDtoSuccess<>(productService.updateProduct(productId,productRequest),HttpStatus.OK.value(),HttpStatus.OK),HttpStatus.OK);
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long productId) {
        productService.deleteProduct(productId);
        return new ResponseEntity<>(new ResponseDtoSuccess<>("Xoá thành công",HttpStatus.OK.value(), HttpStatus.OK),HttpStatus.OK);
    }

    @GetMapping("/search/{productName}")
    public ResponseEntity<?> getProductsByName(@PageableDefault(page = 0, size = 2, sort = "id", direction = Sort.Direction.ASC) Pageable pageable,
                                               @PathVariable String productName) {
        return new ResponseEntity<>(new ResponseDtoSuccess<>(productService.findAllByProductNameContainsIgnoreCase(productName,pageable),HttpStatus.OK.value(),HttpStatus.OK),HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> getProductsByNameAndSort(
            @PageableDefault(page = 0, size = 2, sort = "id", direction = Sort.Direction.ASC) Pageable pageable,
            @RequestParam(defaultValue = "") String productName,
            @RequestParam(defaultValue = "none") String sortOption) {
        System.out.println(productName);
        Page<Product> products = productService.findAllProductsByNameAndSort(productName, pageable, sortOption);

        return new ResponseEntity<>(new ResponseDtoSuccess<>(products, HttpStatus.OK.value(), HttpStatus.OK), HttpStatus.OK);
    }

}
