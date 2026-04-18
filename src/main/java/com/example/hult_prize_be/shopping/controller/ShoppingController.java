package com.example.hult_prize_be.shopping.controller;

import com.example.hult_prize_be.shopping.model.dto.ShoppingDto;
import com.example.hult_prize_be.shopping.service.ShoppingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/shopping")
@RequiredArgsConstructor
public class ShoppingController {
    private final ShoppingService shoppingService;

    // Todo
    @GetMapping("/question/{voiceItemId}")
    public ResponseEntity<ShoppingDto.QuestionsRes> getQuestions(
            @PathVariable Long voiceItemId) {
        return ResponseEntity.ok(shoppingService.getQuestions(voiceItemId));
    }

    // Todo
    @PostMapping("/recommend/{voiceItemId}")
    public ResponseEntity<ShoppingDto.RecommendRes> recommend(
            @PathVariable Long voiceItemId,
            @RequestBody ShoppingDto.RecommendReq req) {
        return ResponseEntity.ok(shoppingService.recommend(req, voiceItemId));
    }
}