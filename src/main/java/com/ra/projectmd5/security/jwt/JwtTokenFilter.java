package com.ra.projectmd5.security.jwt;

import com.ra.projectmd5.security.principle.UserDetailCustomService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
@Component
@RequiredArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter {
    private final JwtProvider jwtProvider;
    private final UserDetailCustomService userDetailCustomService;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // Lấy token từ request
        String token = getTokenFromRequest(request);
        // Nếu token khác null và validateToken hợp lệ
        if(token != null && jwtProvider.validateToken(token)) {
            // Dùng phương thức getUsernameFromToken của jwtProvider để lấy username
            String username = jwtProvider.getUsernameFromToken(token);
            // Đối tượng userDetails sẽ chứa thông tin trả về từ phương thức loadUserByUsername thông qua username
            UserDetails userDetails = userDetailCustomService.loadUserByUsername(username);
            // Tạo đối tượng authentication để chứa các thông tin từ userDetails bao gồm thông tin và quyền
            Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            // Đưa đi tượng authentication vào SecurityContext để chương trình biết rằng người dùng hiện tại đã được xác thực và có quyền gì
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        // cho request chạy tiếp
        filterChain.doFilter(request, response);
    }
    private String getTokenFromRequest(HttpServletRequest request) {
        //Lấy ra chuỗi Authorization từ header của request
        String bearerToken = request.getHeader("Authorization");
        // Kiểm tra nếu bearToken khác null và bắt đầu bằng chuỗi Bearer
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            // Trả về chuỗi đằng sau ký tự có index = 7
            return bearerToken.substring(7);
        }
        return null;
    }
}
