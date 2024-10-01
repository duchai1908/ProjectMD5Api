package com.ra.projectmd5.model.repository;

import com.ra.projectmd5.model.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICategoryRepository extends JpaRepository<Category, Long> {
    boolean existsByName(String name);
    Page<Category> findCategoryByNameContainsIgnoreCase(String cateName, Pageable pageable);
    Category findCategoryById(Long id);
}
