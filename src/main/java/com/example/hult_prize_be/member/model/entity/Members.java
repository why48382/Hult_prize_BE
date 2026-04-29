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

    @Column(nullable = false, columnDefinition = "BOOLEAN DEFAULT FALSE")
    private boolean termsAgreed;       // [필수] 서비스 이용약관

    @Column(nullable = false, columnDefinition = "BOOLEAN DEFAULT FALSE")
    private boolean voiceAgreed;       // [필수] 음성 정보 수집/이용 동의

    @Column(nullable = false, columnDefinition = "BOOLEAN DEFAULT FALSE")
    private boolean privacyAgreed;     // [필수] 개인정보 수집/이용 동의

    @Column(nullable = false, columnDefinition = "BOOLEAN DEFAULT FALSE")
    private boolean notificationAgreed; // [선택] 알림 정보 수신 동의

    public enum Role {
        ELDER,
        CAREGIVER,
        NONE
    }

    public enum Status {
        ACTIVE,
        BLOCKED
    }

    public void decideRole(Role role) {
        if (role != null) this.role = role;
    }

    public void unpair () {
        this.role = Role.NONE;
    }
}
