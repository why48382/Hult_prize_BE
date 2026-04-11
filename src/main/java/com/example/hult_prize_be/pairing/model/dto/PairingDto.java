package com.example.hult_prize_be.pairing.model.dto;

import com.example.hult_prize_be.member.model.entity.Members;
import com.example.hult_prize_be.pairing.model.entity.Pairing;

public class PairingDto {
    public static class CreatReq {
        public Pairing toEntity(Members elder, Members caregiver) {
            return Pairing.builder()
                    .elder(elder)
                    .caregiver(caregiver)
                    .build();
        }
    }
}
