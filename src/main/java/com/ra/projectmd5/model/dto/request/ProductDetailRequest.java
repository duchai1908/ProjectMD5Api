package com.ra.projectmd5.model.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ProductDetailRequest {
    @NotBlank(message = "Tên chi tiết sản phẩm không được để trống")
    private String name;
    private String description;
    @NotNull(message = "Số lượng kho không được để trống")
    private Integer stock;
    @NotNull(message = "Giá tiền không được để trống")
    private Double price;
    @NotNull(message = "Kích thước không được để trống")
    private Long sizeId;
    @NotNull(message = "Màu sắc không được để trống")
    private Long colorId;
    @NotNull(message = "Sản phẩm chính không được để trống")
    private Long productId;
    private List<MultipartFile> images;
}
