package com.example.hult_prize_be.shopping.repository;

import com.example.hult_prize_be.shopping.model.entity.ShoppingRecommendation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShoppingRepository extends JpaRepository<ShoppingRecommendation, Long> {
    ShoppingRecommendation findByVoiceItem_VoiceItemId(Long voiceItemId);
}
