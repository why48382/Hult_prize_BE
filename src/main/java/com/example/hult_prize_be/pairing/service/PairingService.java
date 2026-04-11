package com.example.hult_prize_be.pairing.service;

import com.example.hult_prize_be.member.model.dto.MemberDto;
import com.example.hult_prize_be.member.model.entity.Members;
import com.example.hult_prize_be.member.repository.MemberRepository;
import com.example.hult_prize_be.pairing.model.dto.PairingDto;
import com.example.hult_prize_be.pairing.model.entity.Pairing;
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

    private record CodeEntry(Members elderId, Instant expiresAt) {
    }

    private final ConcurrentHashMap<String, CodeEntry> codeStore = new ConcurrentHashMap<>();
    // 코드(key) 아이디와 만료시간 (값) 으로 구성됨

    public String codeIssue(MemberDto.AuthUser dto) {
        Members member = memberRepository.findByMemberId(dto.getMemberId())
                .orElseThrow(() -> new RuntimeException("사용자가 존재하지 않습니다"));

        if (member.getRole() != Members.Role.ELDER) {
            throw new RuntimeException("잘못된 역할입니다.");
        }

        // TODO 코드 생성을 메소드로 빼고 불러오는 방식으로 변경하기
        String code;
        do {
            code = String.format("%04d", new Random().nextInt(10000));
        } while (codeStore.containsKey(code)); // 이미 존재하면 다시 생성
        codeStore.put(code, new CodeEntry(member, Instant.now().plusSeconds(600)));

        return code;
    }

    public void codeVerify(MemberDto.AuthUser member, String inputCode) {
        Members caregiver = memberRepository.findByMemberId(member.getMemberId()).orElseThrow(() -> new RuntimeException("사용자가 존재하지 않습니다"));
        CodeEntry entry = codeStore.get(inputCode);
        if (entry == null || Instant.now().isAfter(entry.expiresAt())) {
            codeStore.remove(inputCode);
            throw new RuntimeException("만료되거나 잘못된 코드입니다.");
        }
        PairingDto.CreatReq creatReq = new PairingDto.CreatReq();
        Pairing pairing = creatReq.toEntity(entry.elderId, caregiver);
        pairingRepository.save(pairing);
    }

}
