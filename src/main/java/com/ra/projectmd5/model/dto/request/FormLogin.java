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
public class FormLogin {
    @NotBlank(message = "Username is blank!")
    private String username;
    @NotBlank(message = "Password is blank")
    private String password;
}
