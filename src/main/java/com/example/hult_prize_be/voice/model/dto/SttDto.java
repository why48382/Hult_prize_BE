package com.example.hult_prize_be.voice.model.dto;

import com.example.hult_prize_be.voice.model.entity.Voice;
import com.example.hult_prize_be.voice.model.entity.VoiceItems;
import lombok.Getter;

import java.util.List;

public class SttDto {

    @Getter
    public static class Request {
        private final String audioUrl;
        private final Long voiceId;

        public Request(String audioUrl, Long voiceId) {
            this.audioUrl = audioUrl;
            this.voiceId = voiceId;
        }
    }

    @Getter
    public static class Response {
        private Long voiceId;
        private String originalText;
        private List<Item> items;

        @Getter
        public static class Item {
            private String itemName;
            private Integer itemQuantity;

            public VoiceItems toEntity(Voice voice) {
                return VoiceItems.builder()
                        .itemName(itemName)
                        .itemQuantity(itemQuantity)
                        .voice(voice)
                        .build();
            }
        }
    }
}