package com.example.hult_prize_be.member.controller;

import com.example.hult_prize_be.member.model.dto.MemberDto;
import com.example.hult_prize_be.member.model.entity.Members;
import com.example.hult_prize_be.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/member")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @GetMapping("/role")
    public ResponseEntity decideRole(@AuthenticationPrincipal MemberDto.AuthUser member,
                                     @RequestParam Members.Role role) {
        memberService.decideRole(member.getMemberId(), role);
        return ResponseEntity.ok().build();
    }
}
