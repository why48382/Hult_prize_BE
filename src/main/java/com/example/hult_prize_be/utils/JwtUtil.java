package com.example.hult_prize_be.utils;

import com.example.hult_prize_be.member.model.entity.Members;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {
    @Value("${jwt.secret}")
    private String secret;
    private Key KEY;
    private static final Long EXP = 1000 * 60 * 60 * 24 * 7L;

    @PostConstruct
    public void init() {
        this.KEY = Keys.hmacShaKeyFor(secret.getBytes());
    }

    public String generateToken(String member_Id, Long id, String name, Members.Role role) {

        // Map로 제거하고 바로 적용시키는 방식으로 변경
        // 만약 데이터가 많다면 Map 방식이 좋을것 같다
        return Jwts.builder()
                .claim("id", id)
                .claim("member_Id", member_Id)
                .claim("name", name)
                .claim("role", role != null ? role.name() : null)
                .setExpiration(new Date(System.currentTimeMillis() + EXP))
                .signWith(KEY, SignatureAlgorithm.HS256)
                .compact();
    }

    public String generateNewMemberToken(String kakaoId, String name) {
        return Jwts.builder()
                .claim("member_Id", kakaoId)
                .claim("name", name)
                .claim("isNew", true)
                .setExpiration(new Date(System.currentTimeMillis() + EXP))
                .signWith(KEY, SignatureAlgorithm.HS256)
                .compact();
    }

    public static void deleteToken(HttpServletResponse response) {

        Cookie cookie = new Cookie("onsoom_access_token", null);
        cookie.setHttpOnly(true);
        cookie.setSecure(true); // 로그인 때 Secure 줬다면 동일하게
        cookie.setPath("/");
//        cookie.setDomain("gomorebi.kro.kr"); // 로그인 때와 동일하게
        cookie.setMaxAge(0); // 즉시 만료
        response.addCookie(cookie);

    }


    public static String getValue(Claims claims, String key) {
        String value = (String) claims.get(key);

        return value;
    }

    public Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(KEY)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}

