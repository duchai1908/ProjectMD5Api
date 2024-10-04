package com.ra.projectmd5.model.service.impl;

import com.ra.projectmd5.model.entity.Orders;
import com.ra.projectmd5.model.service.IOrdersService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrdersServiceImpl implements IOrdersService {
    @Override
    public Orders addToOrders(Long userId) {
        return null;
    }
}
