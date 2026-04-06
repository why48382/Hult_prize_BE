package com.example.hult_prize_be.member.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Members {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String member_id;

    @Column(nullable = false)
    private String name;

    private Role role;

    private Status status;

    private LocalDateTime createdAt;

    public enum Role {
        ELDER,
        CAREGIVER
    }

    public enum Status {
        ACTIVE,
        BLOCKED
    }
}
