package com.ra.projectmd5.model.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ProductRequest {
    @NotBlank(message = "Tên sản phẩm không được để trống")
    private String name;
    @NotNull(message = "Ảnh sản phẩm không được để trống")
    private MultipartFile image;
    @NotBlank(message = "Mô tả không được để trống")
    private String description;
    @NotNull(message = "Status không được để trống")
    private boolean status;
    @NotNull(message = "Danh mục không được để trống")
    private Long categoryId;
    @NotNull(message = "Mục giảm giá không được để trống")
    private Integer sale;
}
