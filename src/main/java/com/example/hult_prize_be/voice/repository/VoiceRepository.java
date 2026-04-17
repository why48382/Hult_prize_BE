package com.example.hult_prize_be.voice.repository;

import com.example.hult_prize_be.voice.model.entity.Voice;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VoiceRepository extends JpaRepository<Voice, Long> {
    @EntityGraph(attributePaths = "items")
    List<Voice> findByElder_IdInOrderByCreatedAtDesc(List<Long> elderIds);
}
