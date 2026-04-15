package com.example.hult_prize_be.voice.client;

import com.example.hult_prize_be.voice.model.dto.SttDto;

public interface SttClient {
    SttDto.Response transcribe(SttDto.Request request);
}
