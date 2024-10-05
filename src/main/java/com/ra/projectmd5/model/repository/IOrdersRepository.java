package com.ra.projectmd5.model.repository;

import com.ra.projectmd5.model.entity.Orders;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface IOrdersRepository extends JpaRepository<Orders, Long> {
    @Query("SELECT o FROM Orders o WHERE o.user.id = :userId AND o.code LIKE CONCAT('%', :search, '%')")
    Page<Orders> getAllOrderByUser(@Param("userId") Long userId, @Param("search") String search, Pageable pageable);
    Page<Orders> findAllByUserId(Long userId, Pageable pageable);
    Optional<Orders> findByUserIdAndId(Long userId, Long orderId);
}
