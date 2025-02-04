package com.sh.trippy.domain.tripinvitaion.domain.model;

import com.sh.trippy.domain.trip.domain.model.Trip;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TripInvitation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tripInvitationId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "trip")
    private Trip trip;

    private Long userFrom; // host
    private Long userTo; // guest
    private InvitationStatus invitationStatus;

    @Builder
    private TripInvitation(Trip trip, Long userFrom, Long userTo) {
        this.trip = trip;
        this.userFrom = userFrom;
        this.userTo = userTo;
        this.invitationStatus = InvitationStatus.WAIT; // 초기는 WAIT

    }

    public static TripInvitation createTripInvitaion(Trip trip, Long userFrom, Long userTo){

        return TripInvitation.builder()
                .trip(trip)
                .userFrom(userFrom)
                .userTo(userTo)
                .build();
    }
}
