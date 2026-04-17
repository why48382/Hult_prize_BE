package com.example.hult_prize_be.voice.service;

import com.example.hult_prize_be.file.model.FileDto;
import com.example.hult_prize_be.file.service.FileService;
import com.example.hult_prize_be.member.model.dto.MemberDto;
import com.example.hult_prize_be.member.model.entity.Members;
import com.example.hult_prize_be.member.repository.MemberRepository;
import com.example.hult_prize_be.pairing.repository.PairingRepository;
import com.example.hult_prize_be.voice.client.SttClient;
import com.example.hult_prize_be.voice.model.dto.SttDto;
import com.example.hult_prize_be.voice.model.dto.VoiceDto;
import com.example.hult_prize_be.voice.model.entity.Voice;
import com.example.hult_prize_be.voice.model.entity.VoiceItems;
import com.example.hult_prize_be.voice.repository.VoiceItemRepository;
import com.example.hult_prize_be.voice.repository.VoiceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
@Slf4j
@RequiredArgsConstructor
public class VoiceService {
    private final FileService fileService;
    private final MemberRepository memberRepository;
    private final PairingRepository pairingRepository;
    private final VoiceRepository voiceRepository;
    private final VoiceItemRepository voiceItemRepository;
    private final SttClient sttClient;

    public void upload(VoiceDto.UploadReq uploadReq, MemberDto.AuthUser member) {
        Members members = memberRepository.findByMemberId(member.getMemberId())
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));
        if (members.getRole() == Members.Role.ELDER) {
            throw new RuntimeException("요청할 수 있는 사용자가 아닙니다.");
        }

        FileDto.UploadResponse uploadedFile = fileService.upload(uploadReq.getFile(), uploadReq.getDirectory());
        Voice savedVoice = voiceRepository.save(uploadReq.toEntity(uploadedFile, member));

        CompletableFuture.runAsync(() -> processStt(savedVoice.getVoiceId(), savedVoice.getAudioUrl()));
    }

    public List<VoiceDto.RequestRes> request(MemberDto.AuthUser member) {
        List<Long> elderIds = resolveElderIds(member);
        if (elderIds.isEmpty()) {
            return List.of();
        }

        return voiceRepository.findByElder_IdInOrderByCreatedAtDesc(elderIds).stream()
                .map(VoiceDto.RequestRes::from)
                .toList();
    }

    public VoiceDto.RequestRes updateStatus(Long voiceId, Voice.Status status, MemberDto.AuthUser member) {
        List<Long> elderIds = resolveElderIds(member);

        Voice voice = voiceRepository.findByVoiceIdAndElder_IdIn(voiceId, elderIds)
                .orElseThrow(() -> new RuntimeException("Voice not found or access denied."));

        voice.changeStatus(status);
        return VoiceDto.RequestRes.from(voiceRepository.save(voice));
    }

    private List<Long> resolveElderIds(MemberDto.AuthUser member) {
        if (member.getRole() == Members.Role.ELDER) {
            return List.of(member.getId());
        }
        if (member.getRole() == Members.Role.CAREGIVER) {
            return pairingRepository.findElderIdsByCaregiverId(member.getId());
        }
        return List.of();
    }

    private void processStt(Long voiceId, String audioUrl) {
        int maxAttempts = 3;

        for (int attempt = 1; attempt <= maxAttempts; attempt++) {
            try {
                SttDto.Response response = sttClient.transcribe(new SttDto.Request(audioUrl, voiceId));
                if (response == null) {
                    log.warn("STT response is null. voiceId={}, attempt={}/{}", voiceId, attempt, maxAttempts);
                    return;
                }

                Voice voice = voiceRepository.findById(voiceId)
                        .orElseThrow(() -> new RuntimeException("Voice not found. voiceId=" + voiceId));

                voice.updateFromSTT(response.getOriginalText());
                voiceRepository.save(voice);

                List<VoiceItems> items = response.getItems() == null
                        ? List.of()
                        : response.getItems().stream()
                          .map(item -> item.toEntity(voice))
                          .toList();

                if (!items.isEmpty()) {
                    voiceItemRepository.saveAll(items);
                }
                return;
            } catch (Exception e) {
                if (attempt == maxAttempts) {
                    log.error("Failed to process STT after {} attempts. voiceId={}", maxAttempts, voiceId, e);
                    return;
                }
                log.warn("Failed to process STT. Retrying... voiceId={}, attempt={}/{}", voiceId, attempt, maxAttempts, e);
            }
        }
    }
}
