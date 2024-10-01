package com.ra.projectmd5.model.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class FormRegister {
    @NotBlank(message = "Username is blank!")
    private String username;
    @NotBlank(message = "Password is blank!")
    private String password;
    @NotBlank(message = "Email is blank!")
    private String email;
    private Set<String> roles;
}
