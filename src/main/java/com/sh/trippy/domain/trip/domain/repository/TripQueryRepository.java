package com.sh.trippy.domain.trip.domain.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sh.trippy.domain.trip.domain.model.QTrip;
import com.sh.trippy.domain.trip.domain.model.Trip;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.sh.trippy.domain.trip.domain.model.QTrip.trip;


@Repository
@RequiredArgsConstructor
public class TripQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;


    public List<Trip> getTripListByUserId(Long userId){

        return jpaQueryFactory
                .select(trip)
                .from(trip)
                .where(trip.tripId.eq(userId))
                .fetch();
    }



}
