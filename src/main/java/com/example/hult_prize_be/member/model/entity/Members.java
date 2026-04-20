package com.example.hult_prize_be.member.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

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

    @Column(nullable = false, unique = true, name = "member_id")
    private String memberId;

    @Column(length = 100)
    private String password;

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "VARCHAR(10) DEFAULT 'ACTIVE'")
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

    public void decideRole(Role role) {
        if (role != null) this.role = role;
    }
}
