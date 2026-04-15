package com.example.hult_prize_be.voice.repository;

import com.example.hult_prize_be.voice.model.entity.VoiceItems;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VoiceItemRepository extends JpaRepository<VoiceItems, Long> {
}
