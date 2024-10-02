package com.ra.projectmd5.model.repository;

import com.ra.projectmd5.model.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IProductRepository extends JpaRepository<Product, Long> {
    Page<Product> findProductByNameContainsIgnoreCase(String name, Pageable pageable);
    boolean existsByName(String name);
}
