package com.example.hult_prize_be.voice.controller;

import com.example.hult_prize_be.member.model.dto.MemberDto;
import com.example.hult_prize_be.voice.model.dto.VoiceDto;
import com.example.hult_prize_be.voice.model.entity.Voice;
import com.example.hult_prize_be.voice.service.VoiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/voice")
@RequiredArgsConstructor
public class VoiceController {
    private final VoiceService voiceService;

    //TODO 테스트 x
    @PostMapping("/upload")
    public ResponseEntity<String> upload(
            @ModelAttribute VoiceDto.UploadReq uploadReq,
            @AuthenticationPrincipal MemberDto.AuthUser member) {
        voiceService.upload(uploadReq, member);
        return ResponseEntity.ok("업로드 완료");
    }

    //TODO 테스트 x
    @GetMapping("/requests")
    public ResponseEntity<List<VoiceDto.RequestRes>> requests(
            @AuthenticationPrincipal MemberDto.AuthUser member) {
        return ResponseEntity.ok(voiceService.request(member));
    }

    //TODO 테스트 x
    @PatchMapping("/{voiceId}/status")
    public ResponseEntity<VoiceDto.RequestRes> updateStatus(
            @PathVariable Long voiceId,
            @RequestParam Voice.Status status,
            @AuthenticationPrincipal MemberDto.AuthUser member) {
        return ResponseEntity.ok(voiceService.updateStatus(voiceId, status, member));
    }

}
