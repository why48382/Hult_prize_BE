package com.example.hult_prize_be.config.oauth;


import com.example.hult_prize_be.member.model.dto.MemberDto;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.example.hult_prize_be.utils.JwtUtil;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        log.info("LoginFilter 성공 로직.");
        MemberDto.AuthUser authUser = (MemberDto.AuthUser) authentication.getPrincipal();

        String jwt = JwtUtil.generateToken(authUser.getMember_id(), authUser.getId(), authUser.getName());

        if (jwt != null) {
            String cookieValue = String.format(
                    "SJB_AT=%s; Path=/; Domain=gomorebi.kro.kr; HttpOnly; Secure; SameSite=None; Max-Age=%d",
                    jwt, 60 * 60 * 24
            );
            response.addHeader("Set-Cookie", cookieValue);
            // 소셜 로그인 성공 후 프론트의 특정 경로로 이동
            response.sendRedirect("https://www.gomorebi.kro.kr/oauth2/success");
        }
    }
}
