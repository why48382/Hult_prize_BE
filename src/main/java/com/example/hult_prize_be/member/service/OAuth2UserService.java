package com.example.hult_prize_be.member.service;


import com.example.hult_prize_be.member.model.dto.MemberDto;
import com.example.hult_prize_be.member.model.entity.Members;
import com.example.hult_prize_be.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OAuth2UserService extends DefaultOAuth2UserService {
    private final MemberRepository memberRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        Map<String, Object> attributes = oAuth2User.getAttributes();

        Map properties = ((Map) attributes.get("properties"));
        String name = (String) properties.get("nickname");
        String kakaoId = attributes.get("id").toString();

        Optional<Members> socialUserResult =
                memberRepository.findByMemberId(kakaoId);
        Members member;
        if (!socialUserResult.isPresent()) {
            member = memberRepository.save(
                    Members.builder().name(name).memberId(kakaoId).password("KAKAO_USER")
                            .build()
            );
        } else {
            member = socialUserResult.get();
        }
        return MemberDto.AuthUser.fromOAuth(member);
    }
}
