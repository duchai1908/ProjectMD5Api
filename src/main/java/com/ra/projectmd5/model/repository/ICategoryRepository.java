package com.ra.projectmd5.model.repository;

import com.ra.projectmd5.model.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ICategoryRepository extends JpaRepository<Category, Long> {
    boolean existsByName(String name);
    Page<Category> findCategoryByNameContainsIgnoreCase(String cateName, Pageable pageable);
    Category findCategoryById(Long id);
    @Query(value = "SELECT c FROM Category c ORDER BY c.id DESC")
    List<Category> findNewestCategory(Pageable pageable);
}
