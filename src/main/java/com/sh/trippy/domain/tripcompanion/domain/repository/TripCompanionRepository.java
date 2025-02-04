package com.sh.trippy.domain.tripcompanion.domain.repository;

import com.sh.trippy.domain.tripcompanion.domain.model.TripCompanion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TripCompanionRepository extends JpaRepository<TripCompanion, Long> {

    List<TripCompanion> findByTrip_TripId(Long tripId);
}
