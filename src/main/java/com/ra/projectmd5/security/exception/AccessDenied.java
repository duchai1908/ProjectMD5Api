package com.ra.projectmd5.security.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ra.projectmd5.model.dto.response.DataError;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
@Component
@Slf4j
public class AccessDenied implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
//        log.error("Un Permission Failed {}", accessDeniedException.getMessage());
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        DataError<String> dataError = new DataError<>();
        dataError.setMessage(accessDeniedException.getMessage());
        dataError.setStatus(HttpStatus.BAD_REQUEST);
        dataError.setStatusCode(403);
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonResponse = objectMapper.writeValueAsString(dataError);
        response.getWriter().write(jsonResponse);
    }
}
