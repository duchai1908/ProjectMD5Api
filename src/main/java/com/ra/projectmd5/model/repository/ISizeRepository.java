package com.ra.projectmd5.model.repository;

import com.ra.projectmd5.model.entity.Size;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ISizeRepository extends JpaRepository<Size, Long> {
    boolean existsBySize(String size);
}
