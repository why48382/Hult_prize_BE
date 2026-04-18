package com.example.hult_prize_be.shopping.model.entity;

import com.example.hult_prize_be.voice.model.entity.VoiceItems;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
// 아이템의 상품 추천 정보를 저장하는 테이블 API 호출 횟수를 줄이기 위함
public class ShoppingRecommendation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long recommendationId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "voice_item_id", nullable = false)
    private VoiceItems voiceItem;

    // 1순위
    private String productTitle1;
    private Integer price1;
    private String mall1;
    private String productUrl1;
    private String image1;
    private String reason1;

    // 2순위
    private String productTitle2;
    private Integer price2;
    private String mall2;
    private String productUrl2;
    private String image2;
    private String reason2;

    // 3순위
    private String productTitle3;
    private Integer price3;
    private String mall3;
    private String productUrl3;
    private String image3;
    private String reason3;

    private LocalDateTime createdAt;
}
