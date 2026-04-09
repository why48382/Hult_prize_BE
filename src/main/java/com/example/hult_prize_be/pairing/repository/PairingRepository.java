package com.example.hult_prize_be.pairing.repository;

import com.example.hult_prize_be.pairing.model.entity.Pairing;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PairingRepository extends JpaRepository<Pairing, Long> {
}
