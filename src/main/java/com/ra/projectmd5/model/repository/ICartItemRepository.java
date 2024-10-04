package com.ra.projectmd5.model.repository;

import com.ra.projectmd5.model.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICartItemRepository extends JpaRepository<CartItem, Long> {
}
