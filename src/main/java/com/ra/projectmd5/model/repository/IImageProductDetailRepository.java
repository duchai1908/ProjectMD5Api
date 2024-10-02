package com.ra.projectmd5.model.repository;

import com.ra.projectmd5.model.entity.ImageProductDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IImageProductDetailRepository extends JpaRepository<ImageProductDetail, Long> {
    void deleteImageProductDetailByProductDetailId(long productId);
}
