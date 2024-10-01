package com.ra.projectmd5.security.jwt;

import com.ra.projectmd5.security.principle.UserDetailCustom;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Slf4j
public class JwtProvider {
    @Value("${jwt.expired-time}")
    private long expired;
    @Value("${jwt.secret-key}")
    private String secretKey;

    public String generateToken(UserDetailCustom userDetailCustom) {
        return Jwts.builder().setSubject(userDetailCustom.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + expired))
                .signWith(SignatureAlgorithm.HS512,secretKey)
                .compact();
    }
    public boolean validateToken(String token) {
        try{
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            return true;
        }catch (ExpiredJwtException e){ // Lỗi JWT hết hạn
            log.error("JWT: message error expired:", e.getMessage());
        }catch (UnsupportedJwtException e){ // Lỗi không hỗ trợ JWT mình mã hoá
            log.error("JWT: message error unsupported:", e.getMessage());
        }catch (MalformedJwtException e){ // Lỗi JWT không hợp lệ, ví dụ copy chuỗi bậy bạ dán vào
            log.error("JWT: message error formated:", e.getMessage());
        }catch (SignatureException e){ // Lỗi chữ ký, ví dụ không đúng secret key hoặc thuật toán mã hoá
            log.error("JWT: message error signature not math:", e.getMessage());
        }catch (IllegalArgumentException e){ // Lỗi đối số truyền vào không hợp lệ
            log.error("JWT: message claims empty or argument invalid:", e.getMessage());
        }
        return false;
    }
    public String getUsernameFromToken(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject(); // giải mã token để lấy username từ tiêu đề
    }
}
