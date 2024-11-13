package com.ra.projectmd5.model.repository;

import com.ra.projectmd5.model.entity.Category;
import com.ra.projectmd5.model.entity.Color;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IColorRepository extends JpaRepository<Color, Long> {
    boolean existsByColor(String color);

    Page<Color> findAllByColorContainsIgnoreCase(String color, Pageable pageable);
}
