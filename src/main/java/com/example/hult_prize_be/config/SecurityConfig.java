package com.example.hult_prize_be.config;

import com.example.hult_prize_be.config.filter.JwtAuthFilter;
import com.example.hult_prize_be.config.filter.LoginFilter;
import com.example.hult_prize_be.config.oauth.OAuth2AuthenticationSuccessHandler;
import com.example.hult_prize_be.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
@EnableJpaAuditing
public class SecurityConfig {

    private final JwtUtil jwtUtil;
    private final AuthenticationConfiguration authConfiguration;
    private final OAuth2UserService oAuth2UserService;
    private final OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager() throws Exception {
        return authConfiguration.getAuthenticationManager();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowCredentials(true);
        configuration.setAllowedOrigins(
                List.of("http://localhost:5173", "http://192.0.2.108:80")
        );
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("*"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration); // 모든 경로에 대해 CORS 적용
        return source;
    }

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {

        LoginFilter loginFilter = new LoginFilter(jwtUtil, authConfiguration.getAuthenticationManager());
        loginFilter.setFilterProcessesUrl("/api/login");

        http.oauth2Login(config -> {
            config.userInfoEndpoint(
                    endpoint -> endpoint.userService(oAuth2UserService)
            );
            config.successHandler(oAuth2AuthenticationSuccessHandler);
        });

        http.authorizeHttpRequests(auth -> auth
                .requestMatchers(
                        "/swagger-ui/**",
                        "/swagger-ui.html",
                        "/v3/api-docs/**",
                        "/swagger-resources/**",
                        "/webjars/**"
                ).permitAll()
                .requestMatchers(
                        "/login", "/auth/**", "/user/signup",
                        "/oauth2/**", "/login/oauth2/**", "/user/verify"
                ).permitAll()
                .requestMatchers("/project/read", "/project/search", "/file/read/**").permitAll()
                .requestMatchers("/test/*").hasRole("USER")
                .anyRequest().authenticated()  // permitAll → authenticated로 변경
        );

        http.cors(cors -> cors.configurationSource(corsConfigurationSource()));
        http.csrf(AbstractHttpConfigurer::disable);
        http.httpBasic(AbstractHttpConfigurer::disable);
        http.formLogin(AbstractHttpConfigurer::disable);

        // jwtUtil 넘겨주기
        http.addFilterBefore(new JwtAuthFilter(jwtUtil), UsernamePasswordAuthenticationFilter.class);
        http.addFilterAt(loginFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}