package com.ra.projectmd5.model.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ra.projectmd5.model.entity.OrderDetail;
import com.ra.projectmd5.model.entity.Orders;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class OrdersResponse {
    private Orders orders;
    @JsonIgnoreProperties({"orders"})
    private List<OrderDetail> orderDetail;
}
