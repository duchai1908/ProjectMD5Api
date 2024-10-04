package com.ra.projectmd5.model.repository;

import com.ra.projectmd5.model.entity.CartItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ICartItemRepository extends JpaRepository<CartItem, Long> {
    CartItem findCartItemByProductDetailIdAndUserId(Long productDetailId, Long userId);
    List<CartItem> findAllByUserId(Long userId);
    Page<CartItem> findAllByUserId(Long userId, Pageable pageable);
    void deleteAllByUserId(Long userId);
}
