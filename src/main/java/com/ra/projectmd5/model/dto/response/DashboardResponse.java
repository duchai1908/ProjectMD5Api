package com.ra.projectmd5.model.dto.response;

import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class DashboardResponse {
    private List<MonthlyRevenueResponse> monthlyRevenue;
    private Integer totalProduct;
    private Integer totalCategory;
    private Integer totalOrders;
    private Integer totalUser;
}
