package com.example.hult_prize_be.voice.client;

import com.example.hult_prize_be.voice.model.dto.SttDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class SttClientImpl implements SttClient {

    private final RestTemplate restTemplate;

    @Value("${stt.server.url}")
    private String sttServerUrl;

    @Override
    public SttDto.Response transcribe(SttDto.Request request) {
        return restTemplate.postForObject(
                sttServerUrl + "/api/stt/transcribe",
                request,
                SttDto.Response.class
        );
    }
}