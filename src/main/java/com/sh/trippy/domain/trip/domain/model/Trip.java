package com.sh.trippy.domain.trip.domain.model;

import com.sh.trippy.domain.common.BaseTimeEntity;
import com.sh.trippy.domain.plan.domain.model.Plan;
import com.sh.trippy.domain.reply.domain.model.Reply;
import com.sh.trippy.domain.sharechecklist.domain.model.ShareChecklist;
import com.sh.trippy.domain.tripcompanion.domain.model.TripCompanion;
import com.sh.trippy.domain.tripinvitaion.domain.model.TripInvitation;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Trip extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tripId;
    private String country;
    private String countryEmogi;
    private boolean abroadFlag;
    private LocalDate departureDate;
    private LocalDate abroadDate;
    private boolean beenFlag;

    @OneToMany(mappedBy = "trip", cascade = {CascadeType.REMOVE, CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    private List<Plan> planList = new ArrayList<>();

    @OneToMany(mappedBy = "trip", cascade = {CascadeType.REMOVE, CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    private List<Reply> replyList = new ArrayList<>();

    @OneToMany(mappedBy = "trip", cascade = {CascadeType.REMOVE, CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    private List<TripCompanion> tripCompanionList = new ArrayList<>();

    @OneToMany(mappedBy = "trip", cascade = {CascadeType.REMOVE, CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    private List<TripInvitation> tripInvitationList = new ArrayList<>();

    @OneToOne(mappedBy = "trip", cascade = {CascadeType.REMOVE, CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    private ShareChecklist shareChecklist;



    @Builder
    private Trip(String country, String countryEmogi, boolean abroadFlag, LocalDate departureDate, LocalDate abroadDate, boolean beenFlag) {
        this.country = country;
        this.countryEmogi = countryEmogi;
        this.abroadFlag = abroadFlag;
        this.departureDate = departureDate;
        this.abroadDate = abroadDate;
        this.beenFlag = beenFlag;
    }

    public static Trip createTrip(){

        return null;
    }

}











