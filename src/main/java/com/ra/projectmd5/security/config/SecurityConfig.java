package com.ra.projectmd5.security.config;

import com.ra.projectmd5.constants.RoleName;
import com.ra.projectmd5.security.exception.AccessDenied;
import com.ra.projectmd5.security.exception.JwtEntryPoint;
import com.ra.projectmd5.security.jwt.JwtTokenFilter;
import com.ra.projectmd5.security.principle.UserDetailCustomService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity (prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig {
    private final UserDetailCustomService userDetailCustomService;
    private final AccessDenied accessDenied;
    private final JwtEntryPoint jwtEntryPoint;
    private final JwtTokenFilter jwtTokenFilter;
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Trả về đối tượng authentication để quản lý
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(url -> url.requestMatchers("/api/v1/admin/**").hasAuthority(RoleName.ROLE_ADMIN.name())
                        .requestMatchers("/api/v1/moderator/**").hasAuthority(RoleName.ROLE_MODERATOR.name())
                        .requestMatchers("/user/**").hasAuthority(RoleName.ROLE_USER.name())
                        .anyRequest().permitAll())
                .authenticationProvider(authenticationProvider())
                .exceptionHandling(exception -> exception.authenticationEntryPoint(jwtEntryPoint).accessDeniedHandler(accessDenied))
                .addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean

    public AuthenticationProvider authenticationProvider() {
        // Khai báo authProvider từ lớp DaoAuthenticationProvider. DaoAuthenticationProvider kế thừa interface AuthenticationProvider
        // sử dụng csdl để xác thực người dùng
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        // So sánh password đã bị mã hoá
        authProvider.setPasswordEncoder(passwordEncoder());
        // Tìm kiếm csdl dựa trên userDetailCustomService để biết phải tìm ở đối tượng nào
        authProvider.setUserDetailsService(userDetailCustomService);
        return authProvider;
    }
}
