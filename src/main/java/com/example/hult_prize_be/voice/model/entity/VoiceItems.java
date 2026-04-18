package com.example.hult_prize_be.voice.model.entity;

import com.example.hult_prize_be.shopping.model.entity.ShoppingRecommendation;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VoiceItems {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long voiceItemId;

    @Column(nullable = false)
    private String itemName;

    @Column(nullable = false)
    private Integer itemQuantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "voice_id", nullable = false)
    private Voice voice;

    @OneToOne(mappedBy = "voiceItem", cascade = CascadeType.ALL, orphanRemoval = true)
    private ShoppingRecommendation recommendation;
}
