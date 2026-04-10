package com.example.hult_prize_be.pairing.model.entity;

import com.example.hult_prize_be.member.model.entity.Members;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Pairing {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pairingId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "elder_member_id")
    private Members elder;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "caregiver_member_id")
    private Members caregiver;

    private LocalDateTime createdAt;
}
