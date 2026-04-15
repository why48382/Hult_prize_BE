package com.example.hult_prize_be.voice.service;

import com.example.hult_prize_be.file.model.FileDto;
import com.example.hult_prize_be.file.service.FileService;
import com.example.hult_prize_be.member.model.dto.MemberDto;
import com.example.hult_prize_be.member.model.entity.Members;
import com.example.hult_prize_be.member.repository.MemberRepository;
import com.example.hult_prize_be.voice.model.dto.VoiceDto;
import com.example.hult_prize_be.voice.repository.VoiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VoiceService {
    private final FileService fileService;
    private final MemberRepository memberRepository;
    private final VoiceRepository voiceRepository;

    public void upload(VoiceDto.UploadReq uploadReq, MemberDto.AuthUser member) {
        Members members = memberRepository.findByMemberId(member.getMemberId()).orElseThrow(() -> new RuntimeException("존재하지 않는 사용자 입니다."));
        if (members.getRole() == Members.Role.ELDER) {
            throw new RuntimeException("요청자 역할이 일치하지 않습니다.");
        }
        FileDto.UploadResponse voice = fileService.upload(uploadReq.getFile(), uploadReq.getDirectory());
        voiceRepository.save(uploadReq.toEntity(voice, member));
    }
}
