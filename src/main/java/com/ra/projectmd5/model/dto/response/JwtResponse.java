package com.ra.projectmd5.model.dto.response;

import lombok.*;

import java.util.Date;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class JwtResponse {
    private String accessToken;
    private final String type = "Bearer";
    private String username;
    private String fullName;
    private String email;
    private String phone;
    private String address;
    private Boolean status;
    private Date dob;
    private String image;
    private Set<String> roles;
}
