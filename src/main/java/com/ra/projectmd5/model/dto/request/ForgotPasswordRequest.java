package com.ra.projectmd5.model.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ForgotPasswordRequest {
    @NotBlank(message = "username không được để trống")
    private String username;
    @NotBlank(message = "email không được để trống")
    private String email;
}
