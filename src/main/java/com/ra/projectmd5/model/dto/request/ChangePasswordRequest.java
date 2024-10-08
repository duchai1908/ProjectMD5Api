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
public class ChangePasswordRequest {
    @NotBlank(message = "Mật khẩu cũ không được để trống ")
    private String oldPassword;
    @NotBlank(message = "Mật khẩu mới không được để trống")
    private String newPassword;
}
