package com.ra.projectmd5.model.repository;

import com.ra.projectmd5.model.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IOrdersRepository extends JpaRepository<Orders, Long> {
}
