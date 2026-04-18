package com.example.hult_prize_be.shopping.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/shopping")
@RequiredArgsConstructor
public class ShoppingController {

    // 두가지 방식의 요청이 가능해야 하지 않을까? 그냥 바로 키워드 받아서 상품 추천 받기
    // 어시스턴트에게 도움을 받은뒤 결과를 받는 방법
//    @GetMapping("/")

}
