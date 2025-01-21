package com.sh.trippy.domain.trip.domain.repository;

import com.sh.trippy.domain.trip.domain.model.Trip;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TripRepository extends JpaRepository<Trip, Long> {
}
