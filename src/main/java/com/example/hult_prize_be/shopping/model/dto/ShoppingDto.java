package com.example.hult_prize_be.shopping.model.dto;

import com.example.hult_prize_be.shopping.model.entity.ShoppingRecommendation;
import com.example.hult_prize_be.voice.model.entity.VoiceItems;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.function.Function;

public class ShoppingDto {

    @Getter
    @Builder
    public static class QuestionsReq {
        private Long voiceItemId;
        private String originalText;

        public static QuestionsReq from(Long voiceItemId, String originalText) {
            return QuestionsReq.builder()
                    .voiceItemId(voiceItemId)
                    .originalText(originalText)
                    .build();
        }
    }

    @Getter
    public static class QuestionsRes {
        private Long voiceItemId;
        private List<Item> items;
        private List<FollowUpQuestion> followUpQuestions;

        @Getter
        public static class FollowUpQuestion {
            String itemName;
            String question;
            List<String> options;
        }
    }

    @Getter
    public static class RecommendReq {
        private Long voiceItemId;
        private List<Item> items;
        private List<CaregiverAnswers> caregiverAnswers;

        @Getter
        public static class CaregiverAnswers {
            String itemName;
            String answer;
        }
    }

    @Getter
    @Builder
    public static class RecommendRes {
        private Long voiceItemId;
        private List<Recommendation> recommendations;

        @Getter
        public static class Recommendation {
            String itemName;
            Integer rank;
            String productTitle;
            Integer price;
            String mall;
            String productUrl;
            String image;
            String reason;
        }

        public ShoppingRecommendation toEntity(VoiceItems voiceItem) {
            return ShoppingRecommendation.builder()
                    .voiceItem(voiceItem)
                    .productTitle1(get(0, ShoppingDto.RecommendRes.Recommendation::getProductTitle))
                    .price1(get(0, ShoppingDto.RecommendRes.Recommendation::getPrice))
                    .mall1(get(0, ShoppingDto.RecommendRes.Recommendation::getMall))
                    .productUrl1(get(0, ShoppingDto.RecommendRes.Recommendation::getProductUrl))
                    .image1(get(0, ShoppingDto.RecommendRes.Recommendation::getImage))
                    .reason1(get(0, ShoppingDto.RecommendRes.Recommendation::getReason))
                    .productTitle2(get(1, ShoppingDto.RecommendRes.Recommendation::getProductTitle))
                    .price2(get(1, ShoppingDto.RecommendRes.Recommendation::getPrice))
                    .mall2(get(1, ShoppingDto.RecommendRes.Recommendation::getMall))
                    .productUrl2(get(1, ShoppingDto.RecommendRes.Recommendation::getProductUrl))
                    .image2(get(1, ShoppingDto.RecommendRes.Recommendation::getImage))
                    .reason2(get(1, ShoppingDto.RecommendRes.Recommendation::getReason))
                    .productTitle3(get(2, ShoppingDto.RecommendRes.Recommendation::getProductTitle))
                    .price3(get(2, ShoppingDto.RecommendRes.Recommendation::getPrice))
                    .mall3(get(2, ShoppingDto.RecommendRes.Recommendation::getMall))
                    .productUrl3(get(2, ShoppingDto.RecommendRes.Recommendation::getProductUrl))
                    .image3(get(2, ShoppingDto.RecommendRes.Recommendation::getImage))
                    .reason3(get(2, ShoppingDto.RecommendRes.Recommendation::getReason))
                    .createdAt(LocalDateTime.now())
                    .build();
        }

        private <T> T get(int index, Function<Recommendation, T> mapper) {
            if (recommendations == null || recommendations.size() <= index) return null;
            return mapper.apply(recommendations.get(index));
        }

    }

    @Getter
    public static class Item {
        String itemName;
        Integer itemQuantity;
    }

}
