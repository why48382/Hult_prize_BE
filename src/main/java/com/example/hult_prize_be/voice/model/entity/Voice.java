package com.example.hult_prize_be.voice.model.entity;

import com.example.hult_prize_be.member.model.entity.Members;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Voice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long voiceId;

    private String originalText;

    // 음성파일 보관 URL
    private String audioUrl;

    // 녹음 길이
    private Integer audioDuration;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UrgencyLevel urgencyLevel;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;

    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "elder_id")
    private Members elder;

    @OneToMany(mappedBy = "voice", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<VoiceItems> items = new ArrayList<>();

    public enum UrgencyLevel {
        NORMAL,
        URGENCY
    }

    public enum Status {
        PENDING,
        CONFIRMED,
        CANCELED,
        DONE
        // 대기 중, 확인됨, 취소됨, 완료됨
    }

    public void updateFromSTT(String originalText) {
        this.originalText = originalText;
    }

    public void changeStatus(Status targetStatus) {
        validateStatusChange(targetStatus);
        this.status = targetStatus;
    }

    public void validateStatusChange(Status targetStatus) {
        if (targetStatus == null) {
            throw new RuntimeException("Target status is required.");
        }
        if (this.status == targetStatus) {
            return;
        }
        if ((this.status == Status.DONE || this.status == Status.CANCELED)
                && (targetStatus == Status.PENDING || targetStatus == Status.CONFIRMED)) {
            throw new RuntimeException("Cannot change DONE or CANCELED back to PENDING or CONFIRMED.");
        }
        if ((targetStatus == Status.DONE || targetStatus == Status.CANCELED) && this.status == Status.PENDING) {
            throw new RuntimeException("Cannot change PENDING directly to DONE or CANCELED.");
        }
    }

}
