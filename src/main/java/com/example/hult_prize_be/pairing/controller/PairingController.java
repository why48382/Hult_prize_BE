package com.example.hult_prize_be.pairing.controller;

import com.example.hult_prize_be.member.model.dto.MemberDto;
import com.example.hult_prize_be.pairing.service.PairingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/pairing")
@RequiredArgsConstructor
public class PairingController {
    private final PairingService pairingService;

    // 유저를 조회하고 그 다음 역할을 확인 하는 방식으로 진행해야 함

    // 부모님의 페어링 코드 발급 요청 (트리거를 만들고 나중에 프론트에서 버튼을 통해 가능학 ㅔ만들자 DB에 Pairing KEY 저장은 필요 없다고 생각하고 있기 때문에
    @GetMapping("/1")
    public ResponseEntity<String> codeIssued(@AuthenticationPrincipal MemberDto.AuthUser member) {
        return ResponseEntity.ok(pairingService.codeIssued(member));
    }
    // 보호자의 페어링 코드 검증 요청
    // 대상의 id를 어떻게 휙득하지? 반대로 이렇게 해야 할듯 코드 입력 -> 아이디를 알아내기
    @GetMapping("/2")
    public ResponseEntity codeVerification(@AuthenticationPrincipal MemberDto.AuthUser member, String code) {
        pairingService.codeVerify(member, code);
        return ResponseEntity.ok().build();
    }
}
