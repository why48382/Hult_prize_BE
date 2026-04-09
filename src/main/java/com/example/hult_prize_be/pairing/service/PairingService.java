package com.example.hult_prize_be.pairing.service;

import com.example.hult_prize_be.pairing.repository.PairingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PairingService {
    private final PairingRepository pairingRepository;
}
