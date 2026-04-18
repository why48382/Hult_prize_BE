package com.example.hult_prize_be.shopping.client;

import com.example.hult_prize_be.shopping.model.dto.ShoppingDto;
import com.example.hult_prize_be.voice.model.dto.SttDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class ShoppingClientImpl implements ShoppingClient {

    private final RestTemplate restTemplate;

    @Value("${SHOPPING_SERVER_URL}")
    private String sttServerUrl;

    @Override
    public ShoppingDto.QuestionsRes question(ShoppingDto.QuestionsReq request) {
        return restTemplate.postForObject(
                sttServerUrl + "/api/shopping/questions",
                request,
                ShoppingDto.QuestionsRes.class
        );
    }

    @Override
    public ShoppingDto.RecommendRes recommend(ShoppingDto.RecommendReq request) {
        return restTemplate.postForObject(
                sttServerUrl + "/api/shopping/recommend",
                request,
                ShoppingDto.RecommendRes.class
        );
    }
}