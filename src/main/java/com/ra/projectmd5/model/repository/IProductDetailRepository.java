package com.ra.projectmd5.model.repository;

import com.ra.projectmd5.model.entity.ProductDetail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IProductDetailRepository extends JpaRepository<ProductDetail, Long> {
    Page<ProductDetail> findAllByProductId(Long productId, Pageable pageable);
    Page<ProductDetail> findAllByProductIdContainsIgnoreCase(Long productId,String search, Pageable pageable);
    boolean existsByName(String name);
    List<ProductDetail> findProductDetailsByProduct_Id(Long productId);
}
