package com.example.hult_prize_be.voice.controller;

import com.example.hult_prize_be.member.model.dto.MemberDto;
import com.example.hult_prize_be.voice.model.dto.VoiceDto;
import com.example.hult_prize_be.voice.service.VoiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/voice")
@RequiredArgsConstructor
public class VoiceController {
    private final VoiceService voiceService;

    @PostMapping("/upload")
    public ResponseEntity<String> upload(
            @ModelAttribute VoiceDto.UploadReq uploadReq,
            @AuthenticationPrincipal MemberDto.AuthUser member) {
        voiceService.upload(uploadReq, member);
        return ResponseEntity.ok("not yet implemented");
    }
}
