package com.sh.trippy.domain.plan.domain.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sh.trippy.domain.plan.domain.model.Plan;
import com.sh.trippy.domain.plan.domain.model.QPlan;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static com.sh.trippy.domain.plan.domain.model.QPlan.plan;

@Repository
@RequiredArgsConstructor
public class PlanQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public Optional<List<Plan>> getTripPlanInfoWithDate(Long tripId, LocalDate tripDate){
        return Optional.ofNullable(
                jpaQueryFactory.select(plan)
                        .from(plan)
                        .where(plan.trip.tripId.eq(tripId),
                                plan.tripDate.eq(tripDate))
                        .fetch()
        );
    }


}
