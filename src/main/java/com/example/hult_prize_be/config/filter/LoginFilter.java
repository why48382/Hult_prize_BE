package com.example.hult_prize_be.config.filter;

import com.example.hult_prize_be.member.model.dto.MemberDto;
import com.example.hult_prize_be.utils.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class LoginFilter extends UsernamePasswordAuthenticationFilter {
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    // 원래는 form-data 형식으로 사용자 정보를 입력받았는데
    // 우리는 JSON 형태로 입력을 받기 위해서 재정의
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        UsernamePasswordAuthenticationToken authToken;
        try {
            log.debug("LoginFilter 실행됐다.");

            MemberDto.Login dto = new ObjectMapper().readValue(request.getInputStream(), MemberDto.Login.class);
            authToken = new UsernamePasswordAuthenticationToken(
                    dto.getMemberId(), dto.getPassword()
            );

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // 그림에서 3번 로직
        return authenticationManager.authenticate(authToken);
    }


    // 그림에서 9번 로직
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        log.info("LoginFilter 성공 로직.");
        MemberDto.AuthUser authUser = (MemberDto.AuthUser) authResult.getPrincipal();

        String jwt = jwtUtil.generateToken(authUser.getName(), authUser.getId(), authUser.getName(), authUser.getRole());

        if(jwt != null) {
            Cookie cookie = new Cookie("onsoom_access_token", jwt);
            cookie.setHttpOnly(true);
            cookie.setSecure(true);
            cookie.setPath("/");
//            cookie.setDomain("gomorebi.kro.kr");

//            String cookieString = String.format(
//                    "%s=%s; Path=/; Domain=gomorebi.kro.kr; HttpOnly; Secure; SameSite=None",
//                    cookie.getName(), cookie.getValue()
//            );
//            response.addHeader("Set-Cookie", cookieString);

            response.getWriter().write(new ObjectMapper().writeValueAsString(MemberDto.LoginRes.from(authUser)));

        }


    }
}

