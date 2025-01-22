package com.sh.trippy.domain.plan.domain.repository;

import com.sh.trippy.domain.plan.domain.model.Plan;
import com.sh.trippy.domain.trip.domain.model.Trip;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlanRepository extends JpaRepository<Plan, Long> {

    List<Plan> findAllByTripOrderByTripDate(Trip trip);
}
