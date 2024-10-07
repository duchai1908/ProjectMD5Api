package com.ra.projectmd5.model.service;

import com.ra.projectmd5.exception.DataExistException;
import com.ra.projectmd5.model.dto.request.ProductDetailRequest;
import com.ra.projectmd5.model.dto.response.ProductDetailResponse;
import com.ra.projectmd5.model.entity.ProductDetail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IProductDetailService {
    Page<ProductDetail> findAllProductDetail(Pageable pageable,String search, Long id);
    ProductDetail getProductDetailById(Long id);
    ProductDetail saveProductDetail(ProductDetailRequest productDetailRequest) throws DataExistException;
    ProductDetail updateProductDetail(ProductDetailRequest productDetailRequest, Long id) throws DataExistException;
    ProductDetail getProductDetailByColorAndSize(Long colorId, Long sizeId, Long productId);
    void deleteProductDetail(Long id);
    boolean existsByName(String name);
    List<ProductDetail> findAllProductDetailByNothing();
    List<ProductDetail> findAllProductDetailByProductId(Long id);
    ProductDetailResponse getProductDetailAndImage(Long productDetailId);
}
