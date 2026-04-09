package com.example.hult_prize_be.pairing.controller;

import com.example.hult_prize_be.pairing.service.PairingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/pairing")
@RequiredArgsConstructor
public class PairingController {
    private final PairingService pairingService;

    // 부모님의 페어링 코드 발급 요청

    // 보호자의 페어링 코드 검증 요청
}
