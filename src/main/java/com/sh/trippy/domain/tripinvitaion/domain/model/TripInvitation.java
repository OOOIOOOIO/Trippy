package com.sh.trippy.domain.tripinvitaion.domain.model;

import com.sh.trippy.domain.trip.domain.model.Trip;
import jakarta.persistence.*;
import lombok.AccessLevel;
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


}
