package com.ra.projectmd5.model.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RatingRequest {
    @NotNull(message = "Bạn chưa đánh giá sản phẩm")
    private int rating;
    @NotBlank(message = "Bạn chưa nhập bình luận")
    private String comment;
    @NotNull(message = "Không tìm thấy id sản phẩm")
    private Long productId;
}
