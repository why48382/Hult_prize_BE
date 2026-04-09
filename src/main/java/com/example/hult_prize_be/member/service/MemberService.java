package com.example.hult_prize_be.member.service;

import com.example.hult_prize_be.member.model.dto.MemberDto;
import com.example.hult_prize_be.member.model.entity.Members;
import com.example.hult_prize_be.member.repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    @Transactional
    public void decideRole(String memberId, String name, Members.Role role) {
        Members member = memberRepository.findByMemberIdAndName(memberId, name)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 회원입니다."));
        if (member.getRole() != null) {
            throw new RuntimeException("역할을 먼저 제거해야 합니다.");
        }
        member.decideRole(role);
    }
}
