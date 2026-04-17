package com.example.hult_prize_be.voice.repository;

import com.example.hult_prize_be.voice.model.entity.Voice;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface VoiceRepository extends JpaRepository<Voice, Long> {
    @EntityGraph(attributePaths = "items")
    List<Voice> findByElder_IdInOrderByCreatedAtDesc(List<Long> elderIds);

    @EntityGraph(attributePaths = "items")
    Optional<Voice> findByVoiceIdAndElder_IdIn(Long voiceId, List<Long> elderIds);
}
