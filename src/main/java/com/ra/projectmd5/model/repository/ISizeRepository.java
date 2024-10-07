package com.ra.projectmd5.model.repository;

import com.ra.projectmd5.model.entity.Color;
import com.ra.projectmd5.model.entity.Size;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ISizeRepository extends JpaRepository<Size, Long> {
    boolean existsBySize(String size);

    Page<Size> findAllBySizeContainsIgnoreCase(String size, Pageable pageable);
}
