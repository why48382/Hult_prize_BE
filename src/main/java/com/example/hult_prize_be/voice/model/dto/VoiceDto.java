package com.example.hult_prize_be.voice.model.dto;

import com.example.hult_prize_be.file.model.FileDto;
import com.example.hult_prize_be.member.model.dto.MemberDto;
import com.example.hult_prize_be.member.model.entity.Members;
import com.example.hult_prize_be.voice.model.entity.Voice;
import lombok.Builder;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

public class VoiceDto {

    @Getter
    public static class UploadReq {
        private MultipartFile file;
        private String directory;
        private Integer audioDuration;
        private Voice.UrgencyLevel urgencyLevel;

        public Voice toEntity(FileDto.UploadResponse uploadResponse, MemberDto.AuthUser member) {
            return Voice.builder()
                    .audioUrl(uploadResponse.fileUrl())
                    .audioDuration(audioDuration)
                    .urgencyLevel(urgencyLevel != null ? urgencyLevel : Voice.UrgencyLevel.NORMAL)
                    .status(Voice.Status.PENDING)
                    .createdAt(LocalDateTime.now())
                    .elder(Members.builder()
                            .id(member.getId())
                            .memberId(member.getMemberId())
                            .name(member.getName())
                            .role(member.getRole())
                            .status(member.getStatus())
                            .build())
                    .build();
        }
    }

    @Getter
    @Builder
    public static class UploadRes {
        private Long voiceId;
        private String audioUrl;
        private Voice.Status status;

        public static UploadRes from(Voice voice) {
            return UploadRes.builder()
                    .voiceId(voice.getVoiceId())
                    .audioUrl(voice.getAudioUrl())
                    .status(voice.getStatus())
                    .build();
        }
    }
}
