package com.example.hult_prize_be.voice.controller;

import com.example.hult_prize_be.member.model.dto.MemberDto;
import com.example.hult_prize_be.voice.model.dto.VoiceDto;
import com.example.hult_prize_be.voice.model.entity.Voice;
import com.example.hult_prize_be.voice.service.VoiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/voice")
@RequiredArgsConstructor
public class VoiceController {
    private final VoiceService voiceService;

    @PostMapping("/upload")
    public ResponseEntity<VoiceDto.RequestRes> upload(
            @RequestPart("file") MultipartFile file,
            @AuthenticationPrincipal MemberDto.AuthUser member) {
        return ResponseEntity.ok(voiceService.upload(file, "voice", member));
    }

    @GetMapping("/requests")
    public ResponseEntity<List<VoiceDto.RequestRes>> requests(
            @AuthenticationPrincipal MemberDto.AuthUser member) {
        return ResponseEntity.ok(voiceService.request(member));
    }

    @PatchMapping("/{voiceId}/status")
    public ResponseEntity<VoiceDto.RequestRes> updateStatus(
            @PathVariable Long voiceId,
            @RequestParam Voice.Status status,
            @AuthenticationPrincipal MemberDto.AuthUser member) {
        return ResponseEntity.ok(voiceService.updateStatus(voiceId, status, member));
    }

}
