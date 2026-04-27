package com.example.hult_prize_be.member.model.dto;

import com.example.hult_prize_be.member.model.entity.Members;
import jakarta.persistence.Column;
import lombok.Builder;
import lombok.Getter;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class MemberDto {

    @Getter
    public static class MemberCreateDto {
        private String memberId;
        private String name;

        public Members toMembers() {
            return Members.builder()
                    .memberId(memberId)
                    .name(name)
                    .build();
        }
    }

    @Getter
    @Builder
    public static class AuthUser implements UserDetails, OAuth2User {
        private Long id;
        private String memberId;
        private String password;
        private String name;
        private Members.Role role;
        private Members.Status status;
        private boolean newMember;

        @Override
        public String getName() {
            return (name != null && !name.isBlank()) ? name : memberId;
        }

        public boolean isEnabled() {
            return status == Members.Status.ACTIVE;
        }

        @Override
        public Map<String, Object> getAttributes() {
            return Map.of();
        }

        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
            return List.of(new SimpleGrantedAuthority("ROLE_USER"));
        }

        @Override
        public String getPassword() {
            return password;
        }

        @Override
        public String getUsername() {
            return name;
        }

        public static AuthUser fromOAuth(Members entity) {
            return AuthUser.builder()
                    .id(entity.getId())
                    .memberId(entity.getMemberId())
                    .name(entity.getName())
                    .role(entity.getRole())
                    .status(entity.getStatus())
                    .password(null)
                    .newMember(false)
                    .build();
        }
    }

    @Getter
    @Builder
    public static class Login {
        private String memberId;
        private String password;
    }

    @Getter
    @Builder
    public static class LoginRes {
        private Long id;
        private String memberId;
        private String name;

        public static LoginRes from(AuthUser authUser) {
            LoginRes dto = LoginRes.builder()
                    .id(authUser.getId())
                    .memberId(authUser.getMemberId())
                    .name(authUser.getName())
                    .build();

            return dto;
        }
    }

    @Getter
    @Builder
    public static class RoleReq {
        private Members.Role role;
    }

    @Getter
    @Builder
    public static class SignupReq {
        private boolean termsAgreed;
        private boolean voiceAgreed;
        private boolean privacyAgreed;
        private boolean notificationAgreed;

        public Members toEntity(String kakaoId, String name) {
            return Members.builder()
                    .memberId(kakaoId)
                    .name(name)
                    .password("KAKAO_USER")
                    .termsAgreed(this.termsAgreed)
                    .voiceAgreed(this.voiceAgreed)
                    .privacyAgreed(this.privacyAgreed)
                    .notificationAgreed(this.notificationAgreed)
                    .build();
        }
    }

    @Getter
    @Builder
    public static class MeRes {
        private Members.Role role;
        private boolean paired;

        public static MeRes of(Members.Role role, boolean paired) {
            return MeRes.builder()
                    .role(role)
                    .paired(paired)
                    .build();
        }
    }
}