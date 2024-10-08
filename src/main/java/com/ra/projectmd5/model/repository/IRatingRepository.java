package com.ra.projectmd5.model.repository;

import com.ra.projectmd5.model.entity.Rating;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IRatingRepository extends JpaRepository<Rating, Long> {
    Page<Rating> findAllByUserUsername(String username, Pageable pageable);
    Page<Rating> findAllByProductsId(Long productId, Pageable pageable);
    @Query("SELECT AVG(r.rating) FROM Rating r WHERE r.products.id = :productId")
    Object[] findAverageRatingAndCountByProductId(@Param("productId") Long productId);

    @Query("SELECT r.rating, COUNT(r) FROM Rating r WHERE r.products.id = :productId GROUP BY r.rating")
    List<Object[]> findCountByRatingGroupedByProductId(@Param("productId") Long productId);
}
