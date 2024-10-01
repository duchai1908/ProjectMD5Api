package com.ra.projectmd5.model.dto.response;

import com.ra.projectmd5.constants.EHttpStatus;
import lombok.*;
import org.springframework.http.HttpStatusCode;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ResponseWrapper <T> {
    EHttpStatus ehttpStatus;
    int statusCode;
    T data;
}
