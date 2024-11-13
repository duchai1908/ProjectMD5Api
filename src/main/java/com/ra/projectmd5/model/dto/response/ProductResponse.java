package com.ra.projectmd5.model.dto.response;

import com.ra.projectmd5.model.entity.Color;
import com.ra.projectmd5.model.entity.Product;
import com.ra.projectmd5.model.entity.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ProductResponse {
    private Product product;
    private Set<Color> colors;
    private Set<Size> sizes;
    private List<String> images;
}
