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
public class CategoryRequest {
    @NotBlank(message = "Category name must not be null")
    private String name;
    private String description;
    @NotNull(message = "status must not be null")
    private Boolean status;
    @NotNull(message = "Image must not be null")
    private MultipartFile image;
}
