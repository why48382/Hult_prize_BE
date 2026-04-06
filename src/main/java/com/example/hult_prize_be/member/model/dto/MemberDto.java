package com.example.hult_prize_be.member.model.dto;

import com.example.hult_prize_be.member.model.entity.Members;
import lombok.Getter;

public class MemberDto {

    @Getter
    public static class MemberCreateDto {
        private String member_id;
        private String name;

        public Members toMembers() {
            return Members.builder()
                    .member_id(member_id)
                    .name(name)
                    .build();
        }
    }



}