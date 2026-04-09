package com.example.hult_prize_be.pairing.service;

import com.example.hult_prize_be.member.model.dto.MemberDto;
import com.example.hult_prize_be.member.model.entity.Members;
import com.example.hult_prize_be.member.repository.MemberRepository;
import com.example.hult_prize_be.pairing.repository.PairingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
public class PairingService {
    private final PairingRepository pairingRepository;
    private final MemberRepository memberRepository;

    private record CodeEntry(String code, Instant expiresAt) { }
    private final ConcurrentHashMap<String, CodeEntry> codeStore = new ConcurrentHashMap<>();

    public String codeIssued(MemberDto.AuthUser dto) {
        Members member = memberRepository.findByMemberIdAndName(dto.getMemberId(), dto.getName())
                .orElseThrow(() -> new RuntimeException("사용자가 존재하지 않습니다"));

        if (member.getRole() != Members.Role.ELDER) {
            throw new RuntimeException("잘못된 역할입니다.");
        }

        String code = String.format("%04d", new Random().nextInt(10000));
        codeStore.put(member.getMemberId(), new CodeEntry(code, Instant.now().plusSeconds(600)));

        return code;
    }

}
