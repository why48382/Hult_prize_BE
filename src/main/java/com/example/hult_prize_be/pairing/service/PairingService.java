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

    private record CodeEntry(String elderId, Instant expiresAt) {
    }

    private final ConcurrentHashMap<String, CodeEntry> codeStore = new ConcurrentHashMap<>();
    // 코드(key) 아이디와 만료시간 (값) 으로 구성됨

    public String codeIssue(MemberDto.AuthUser dto) {
        Members member = memberRepository.findByMemberId(dto.getMemberId())
                .orElseThrow(() -> new RuntimeException("사용자가 존재하지 않습니다"));

        if (member.getRole() != Members.Role.ELDER) {
            throw new RuntimeException("잘못된 역할입니다.");
        }

        // 코드 생성을 메소드로 빼고 불러오는 방식으로 변경하기
        String code;
        do {
            code = String.format("%04d", new Random().nextInt(10000));
        } while (codeStore.containsKey(code)); // 이미 존재하면 다시 생성
        codeStore.put(code, new CodeEntry(member.getMemberId(), Instant.now().plusSeconds(600)));

        return code;
    }

    public void codeVerify(MemberDto.AuthUser member, String inputCode) {
        Members entity = memberRepository.findByMemberId(member.getMemberId()).orElseThrow(() -> new RuntimeException("사용자가 존재하지 않습니다"));
        CodeEntry entry = codeStore.get(inputCode);
        if (entry == null || Instant.now().isAfter(entry.expiresAt())) {
            codeStore.remove(inputCode);
        }
        // 여기까지 오면 검증 완료인것
        // 이제 테이블에 저장하면 됨
        // 그럼 jpa 부터 짜야지
        // TODO 이제 페어링 테이블 저장 로직만 만들면 됨
    }

}
