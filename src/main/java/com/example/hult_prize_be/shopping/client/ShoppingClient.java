package com.example.hult_prize_be.shopping.client;

import com.example.hult_prize_be.shopping.model.dto.ShoppingDto;

public interface ShoppingClient {
    ShoppingDto.QuestionsRes question(ShoppingDto.QuestionsReq request);
    ShoppingDto.RecommendRes recommend(ShoppingDto.RecommendReq request);
}
