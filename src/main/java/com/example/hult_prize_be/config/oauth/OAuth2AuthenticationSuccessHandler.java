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
    private final JwtUtil jwtUtil;

    // TODO 회원가입시 약관정보 저장하게 만들기
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        log.info("LoginFilter 성공 로직.");
        MemberDto.AuthUser authUser = (MemberDto.AuthUser) authentication.getPrincipal();

        String jwt = jwtUtil.generateToken(authUser.getMemberId(), authUser.getId(), authUser.getName(), authUser.getRole());

        if (jwt != null) {
            String cookieValue = String.format(
                    "onsoom_access_token=%s; Path=/; HttpOnly; Secure; SameSite=None; Max-Age=%d",
                    jwt, 60 * 60 * 24
            );
            response.addHeader("Set-Cookie", cookieValue);
            response.sendRedirect("https://onsum.store/oauth2/success");
        }
    }
}
