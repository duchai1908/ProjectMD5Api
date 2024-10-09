package com.ra.projectmd5.model.service.impl;

import com.ra.projectmd5.model.dto.response.DashboardResponse;
import com.ra.projectmd5.model.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DashboardServiceImpl implements IDashboardService {
    private final IOrdersService ordersService;
    private final IProductService productService;
    private final ICategoryService categoryService;
    private final IUserService userService;

    @Override
    public DashboardResponse totalDashboard() {
        return DashboardResponse.builder()
                .monthlyRevenue(ordersService.getAllRevenue())
                .totalProduct(productService.findAll().size())
                .totalCategory(categoryService.findAll().size())
                .totalOrders(ordersService.findAll().size())
                .totalUser(userService.findAllUsers().size())
                .build();
    }
}
