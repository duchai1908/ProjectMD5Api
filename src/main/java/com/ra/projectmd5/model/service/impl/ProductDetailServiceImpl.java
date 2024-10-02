package com.ra.projectmd5.model.service.impl;

import com.ra.projectmd5.model.dto.request.ProductDetailRequest;
import com.ra.projectmd5.model.entity.ProductDetail;
import com.ra.projectmd5.model.repository.IProductDetailRepository;
import com.ra.projectmd5.model.service.IProductDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductDetailServiceImpl implements IProductDetailService {
    private final IProductDetailRepository productDetailRepository;
    @Override
    public Page<ProductDetail> findAllProductDetail(Pageable pageable, String search, Long id) {
        return null;
    }

    @Override
    public ProductDetail getProductDetailById(Long id) {
        return null;
    }

    @Override
    public ProductDetail saveProductDetail(ProductDetailRequest productDetailRequest) {
        return null;
    }

    @Override
    public ProductDetail updateProductDetail(ProductDetailRequest productDetailRequest, Long id) {
        return null;
    }

    @Override
    public void deleteProductDetail(Long id) {

    }
}
