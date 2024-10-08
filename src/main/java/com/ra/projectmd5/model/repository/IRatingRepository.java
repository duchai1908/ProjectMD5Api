package com.ra.projectmd5.model.repository;

import com.ra.projectmd5.model.entity.Rating;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IRatingRepository extends JpaRepository<Rating, Long> {
    Page<Rating> findAllByUserUsername(String username, Pageable pageable);
    Page<Rating> findAllByProductsId(Long productId, Pageable pageable);
}
