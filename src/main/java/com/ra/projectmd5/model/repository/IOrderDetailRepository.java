package com.ra.projectmd5.model.repository;

import com.ra.projectmd5.model.entity.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IOrderDetailRepository extends JpaRepository<OrderDetail, Long> {
    List<OrderDetail> findOrderDetailsByOrdersId(Long orderId);
    List<OrderDetail> findAllByOrdersId(Long orderId);
}
