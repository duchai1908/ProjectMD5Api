package com.ra.projectmd5.model.repository;

import com.ra.projectmd5.model.entity.ProductDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IProductDetailRepository extends JpaRepository<ProductDetail, Long> {
}
