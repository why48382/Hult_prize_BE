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

    @Column(nullable = false)
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

}
