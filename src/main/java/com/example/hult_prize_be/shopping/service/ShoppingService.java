package com.example.hult_prize_be.shopping.service;

import com.example.hult_prize_be.shopping.client.ShoppingClient;
import com.example.hult_prize_be.shopping.model.dto.ShoppingDto;
import com.example.hult_prize_be.shopping.model.entity.ShoppingRecommendation;
import com.example.hult_prize_be.shopping.repository.ShoppingRepository;
import com.example.hult_prize_be.voice.model.entity.VoiceItems;
import com.example.hult_prize_be.voice.repository.VoiceItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ShoppingService {
    private final ShoppingRepository shoppingRepository;
    private final ShoppingClient shoppingClient;
    private final VoiceItemRepository voiceItemRepository;

    public ShoppingDto.QuestionsRes getQuestions(Long voiceItemId) {
        VoiceItems voiceItem = voiceItemRepository.findById(voiceItemId)
                .orElseThrow(() -> new RuntimeException("VoiceItem not found"));
        String originalText = voiceItem.getVoice().getOriginalText();

        return shoppingClient.question(ShoppingDto.QuestionsReq.from(voiceItemId, originalText));
    }

    public ShoppingDto.RecommendRes recommend(ShoppingDto.RecommendReq req, Long voiceItemId) {
        VoiceItems voiceItem = voiceItemRepository.findById(voiceItemId)
                .orElseThrow(() -> new RuntimeException("VoiceItem not found"));

        ShoppingDto.RecommendRes res = shoppingClient.recommend(req);
        shoppingRepository.save(res.toEntity(voiceItem));
        return res;
    }

}
