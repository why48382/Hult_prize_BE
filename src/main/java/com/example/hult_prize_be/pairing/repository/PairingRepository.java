package com.example.hult_prize_be.pairing.repository;

import com.example.hult_prize_be.pairing.model.entity.Pairing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PairingRepository extends JpaRepository<Pairing, Long> {
    @Query("select p.elder.id from Pairing p where p.caregiver.id = :caregiverId")
    List<Long> findElderIdsByCaregiverId(@Param("caregiverId") Long caregiverId);

    boolean existsByElder_Id(Long elderId);
    boolean existsByCaregiver_Id(Long caregiverId);
}
