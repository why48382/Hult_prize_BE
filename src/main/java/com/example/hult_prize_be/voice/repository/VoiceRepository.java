package com.example.hult_prize_be.voice.repository;

import com.example.hult_prize_be.voice.model.entity.Voice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VoiceRepository extends JpaRepository<Voice, Long> {
}
