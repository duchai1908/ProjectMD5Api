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
//this is dto for update product
public class ProductUpdateRequest {
    @NotBlank(message = "Tên sản phẩm không được để trống")
    private String name;
    private MultipartFile image;
    @NotNull(message = "Status không được để trống")
    private boolean status;
    @NotNull(message = "Danh mục không được để trống")
    private Long categoryId;
    @NotNull(message = "Mục giảm giá không được để trống")
    private Integer sale;
}
