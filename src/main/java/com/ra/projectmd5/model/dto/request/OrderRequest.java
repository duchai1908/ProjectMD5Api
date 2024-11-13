package com.ra.projectmd5.model.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class OrderRequest {
    private String note;
    @NotNull(message = "Địa chỉ không được để trống")
    private Long addressId;
    private Long couponId;
}
