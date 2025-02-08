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

    private Long sendUserId; // host
    private Long receivedUserId; // guest
    private InvitationStatus invitationStatus;

    @Builder
    private TripInvitation(Trip trip, Long sendUserId, Long receivedUserId) {
        this.trip = trip;
        this.sendUserId = sendUserId;
        this.receivedUserId = receivedUserId;
        this.invitationStatus = InvitationStatus.WAIT; // 초기는 WAIT

    }

    public static TripInvitation createTripInvitaion(Trip trip, Long sendUserId, Long receivedUserId){

        return TripInvitation.builder()
                .trip(trip)
                .sendUserId(sendUserId)
                .receivedUserId(receivedUserId)
                .build();
    }
}
