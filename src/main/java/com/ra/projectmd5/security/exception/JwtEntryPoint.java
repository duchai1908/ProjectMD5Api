package com.ra.projectmd5.security.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ra.projectmd5.model.dto.response.DataError;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Slf4j
public class JwtEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
//        log.error("Un Authentication Failed {}", authException.getMessage());
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        DataError<String> dataError = new DataError<>();
        dataError.setMessage(authException.getMessage());
        dataError.setStatus(HttpStatus.BAD_REQUEST);
        dataError.setStatusCode(401);
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonResponse = objectMapper.writeValueAsString(dataError);
        response.getWriter().write(jsonResponse);
    }
}
