package com.ra.projectmd5.model.service;

import com.ra.projectmd5.model.dto.request.OrderRequest;
import com.ra.projectmd5.model.dto.response.MonthlyRevenueResponse;
import com.ra.projectmd5.model.dto.response.OrdersResponse;
import com.ra.projectmd5.model.entity.Orders;
import org.apache.coyote.BadRequestException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IOrdersService {
    Page<Orders> findAllOrdersByUser(Pageable pageable,String search, Long userId);
    OrdersResponse getOrdersById(Long id, Long userId);
    Orders addToOrders(OrderRequest orderRequest,Long userId);
    Orders changeStatus(Long ordersId,String status);
    Orders cancelOrder(Long ordersId,Long userId) throws BadRequestException;
    List<MonthlyRevenueResponse> getAllRevenue();
    List<Orders> findAll();
    Orders deniedOrder(Long ordersId) throws BadRequestException;
}
