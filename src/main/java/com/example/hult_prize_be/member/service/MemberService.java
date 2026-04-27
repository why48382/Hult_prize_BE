package com.example.hult_prize_be.member.service;

import com.example.hult_prize_be.member.model.dto.MemberDto;
import com.example.hult_prize_be.member.model.entity.Members;
import com.example.hult_prize_be.member.repository.MemberRepository;
import com.example.hult_prize_be.pairing.repository.PairingRepository;
import com.example.hult_prize_be.utils.JwtUtil;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final JwtUtil jwtUtil;
    private final MemberRepository memberRepository;
    private final PairingRepository pairingRepository;

    @Transactional
    public void decideRole(String memberId, Members.Role role) {
        Members member = memberRepository.findByMemberId(memberId)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 회원입니다."));
//        if (member.getRole() != null && member.getRole() != role) {
//            throw new RuntimeException("역할을 먼저 제거해야 합니다.");
//        }
        member.decideRole(role);
    }

    public MemberDto.MeRes getMe(MemberDto.AuthUser authUser) {
        Members.Role role = authUser.getRole();
        boolean paired = false;

        if (role == Members.Role.ELDER) {
            paired = pairingRepository.existsByElder_Id(authUser.getId());
        } else if (role == Members.Role.CAREGIVER) {
            paired = pairingRepository.existsByCaregiver_Id(authUser.getId());
        }

        return MemberDto.MeRes.of(role, paired);
    }

    @Transactional
    public void signup(MemberDto.AuthUser authUser, MemberDto.SignupReq req, HttpServletResponse response) {
        if (!authUser.isNewMember()) {
            throw new RuntimeException("이미 가입된 회원입니다.");
        }
        Members member = memberRepository.save(req.toEntity(authUser.getMemberId(), authUser.getName()));

        String jwt = jwtUtil.generateToken(member.getMemberId(), member.getId(), member.getName(), member.getRole());
        String cookieValue = String.format(
                "onsoom_access_token=%s; Path=/; HttpOnly; Secure; SameSite=None; Max-Age=%d",
                jwt, 60 * 60 * 24 * 7
        );
        response.addHeader("Set-Cookie", cookieValue);
    }
}
