package com.example.hult_prize_be.member.controller;

import com.example.hult_prize_be.member.model.dto.MemberDto;
import com.example.hult_prize_be.member.model.entity.Members;
import com.example.hult_prize_be.member.service.MemberService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/member")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @GetMapping("/role")
    public ResponseEntity<String> decideRole(@AuthenticationPrincipal MemberDto.AuthUser member,
                                             @RequestParam Members.Role role) {
        memberService.decideRole(member.getMemberId(), role);
        return ResponseEntity.ok("역할 변경 완료");
    }

    @GetMapping("/me")
    public ResponseEntity<MemberDto.MeRes> getMe(@AuthenticationPrincipal MemberDto.AuthUser member) {
        return ResponseEntity.ok(memberService.getMe(member));
    }

    @PostMapping("/signup")
    public ResponseEntity<Void> signup(@AuthenticationPrincipal MemberDto.AuthUser authUser,
                                       @RequestBody MemberDto.SignupReq req,
                                       HttpServletResponse response) {
        memberService.signup(authUser, req, response);
        return ResponseEntity.ok().build();
    }
}
