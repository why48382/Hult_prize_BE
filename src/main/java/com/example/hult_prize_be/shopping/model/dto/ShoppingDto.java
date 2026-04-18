package com.example.hult_prize_be.shopping.model.dto;

import lombok.Getter;

import java.util.List;

public class ShoppingDto {

    @Getter
    public static class QuestionsReq {
        private Long voiceId;
        private String originalText;
    }

    @Getter
    public static class QuestionsRes {
        private Long voiceId;
        private List<Item> items;
        private List<FollowUpQuestion> followUpQuestions;

        @Getter
        private static class FollowUpQuestion {
            String itemName;
            String question;
            List<String> options;
        }
    }

    @Getter
    public static class RecommendReq {
        private Long voiceId;
        private List<Item> items;
        private List<CaregiverAnswers> caregiverAnswers;

        @Getter
        private static class CaregiverAnswers {
            String itemName;
            String answer;
        }
    }

    @Getter
    public static class Item {
        String itemName;
        Integer itemQuantity;
    }

    @Getter
    public static class RecommendRes {
        private Long voiceId;
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
    }

}
