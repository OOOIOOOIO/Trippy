package com.sh.trippy.domain.tripinvitaion.domain.repository;

import com.sh.trippy.domain.tripinvitaion.domain.model.TripInvitation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TripInvitationRepository extends JpaRepository<TripInvitation, Long> {

    List<TripInvitation> findByUserFrom(Long userId);

    List<TripInvitation> findByUserTo(Long userId);
}
