package com.ra.projectmd5.model.service;

import com.ra.projectmd5.model.dto.request.ProductDetailRequest;
import com.ra.projectmd5.model.entity.ProductDetail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IProductDetailService {
    Page<ProductDetail> findAllProductDetail(Pageable pageable,String search, Long id);
    ProductDetail getProductDetailById(Long id);
    ProductDetail saveProductDetail(ProductDetailRequest productDetailRequest);
    ProductDetail updateProductDetail(ProductDetailRequest productDetailRequest, Long id);
    void deleteProductDetail(Long id);

}
