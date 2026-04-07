package com.example.hult_prize_be.member.repository;

import com.example.hult_prize_be.member.model.entity.Members;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Members, Integer> {

    Optional<Members> findByMember_id(String kakaoId);
}
