package com.example.hult_prize_be.voice.model.dto;

import com.example.hult_prize_be.file.model.FileDto;
import com.example.hult_prize_be.member.model.dto.MemberDto;
import com.example.hult_prize_be.member.model.entity.Members;
import com.example.hult_prize_be.voice.model.entity.Voice;
import com.example.hult_prize_be.voice.model.entity.VoiceItems;
import lombok.Builder;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

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
    public static class RequestRes {
        private Long voiceId;
        private String originalText;
        private String audioUrl;
        private Voice.UrgencyLevel urgencyLevel;
        private Voice.Status status;
        private LocalDateTime createdAt;
        private List<ItemRes> items;

        public static RequestRes from(Voice voice) {
            return RequestRes.builder()
                    .voiceId(voice.getVoiceId())
                    .originalText(voice.getOriginalText())
                    .audioUrl(voice.getAudioUrl())
                    .urgencyLevel(voice.getUrgencyLevel())
                    .status(voice.getStatus())
                    .createdAt(voice.getCreatedAt())
                    .items(voice.getItems().stream()
                            .map(ItemRes::from)
                            .toList())
                    .build();
        }
    }

    @Getter
    @Builder
    public static class ItemRes {
        private Long voiceItemId;
        private String itemName;
        private Integer itemQuantity;

        public static ItemRes from(VoiceItems item) {
            return ItemRes.builder()
                    .voiceItemId(item.getVoiceItemId())
                    .itemName(item.getItemName())
                    .itemQuantity(item.getItemQuantity())
                    .build();
        }
    }
}
