package com.ra.projectmd5.model.dto.response;

import com.ra.projectmd5.model.entity.ProductDetail;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ProductDetailResponse {
    private ProductDetail productDetail;
    private List<String> images;
}
