package com.sh.trippy.domain.tripcompanion.domain.model;

import com.sh.trippy.domain.common.BaseTimeEntity;
import com.sh.trippy.domain.sharechecklist.domain.model.ShareChecklist;
import com.sh.trippy.domain.trip.domain.model.Trip;
import com.sh.trippy.domain.user.domain.Users;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TripCompanion extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long companionId;
    @Enumerated(EnumType.STRING)
    private TripRole tripRole;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "trip")
    private Trip trip;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "users")
    private Users users;

    @Builder
    private TripCompanion(TripRole tripRole, Trip trip, Users users) {
        this.tripRole = tripRole;
        this.trip = trip;
        this.users = users;
    }

    public static TripCompanion createTripCompanion(TripRole tripRole, Trip trip, Users users){
        return TripCompanion.builder()
                .tripRole(tripRole)
                .trip(trip)
                .users(users)
                .build();

    }


}
