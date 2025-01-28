package com.sh.trippy.domain.trip.domain.model;

import com.sh.trippy.domain.common.BaseTimeEntity;
import com.sh.trippy.domain.plan.domain.model.Plan;
import com.sh.trippy.domain.reply.domain.model.Reply;
import com.sh.trippy.domain.sharechecklist.domain.model.ShareChecklist;
import com.sh.trippy.domain.trip.api.dto.req.TripCreateReqDto;
import com.sh.trippy.domain.trip.api.dto.req.TripUpdateReqDto;
import com.sh.trippy.domain.tripcompanion.domain.model.TripCompanion;
import com.sh.trippy.domain.tripinvitaion.domain.model.TripInvitation;
import com.sh.trippy.domain.user.domain.Users;
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
    private String city;
    private String countryEmogi;
    private boolean abroadFlag;
    private LocalDate departureDate;
    private LocalDate arrivalDate;
    private boolean beenFlag;
    private Double corpLat; // 위도
    private Double corpLon; // 경도

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private Users users;
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
    private Trip(String city, String countryEmogi, boolean abroadFlag, LocalDate departureDate, LocalDate arrivalDate, boolean beenFlag, Double corpLat, Double corpLon, Users user) {
        this.city = city;
        this.countryEmogi = countryEmogi;
        this.abroadFlag = abroadFlag;
        this.departureDate = departureDate;
        this.arrivalDate = arrivalDate;
        this.beenFlag = beenFlag;
        this.corpLat = corpLat;
        this.corpLon = corpLon;
        this.users = user;
    }

    public static Trip createTrip(TripCreateReqDto tripCreateReqDto, Users user){
        return Trip.builder()
                .city(tripCreateReqDto.getCity())
                .countryEmogi(tripCreateReqDto.getCountryEmogi())
                .abroadFlag(tripCreateReqDto.isAbroadFlag())
                .departureDate(tripCreateReqDto.getDepartureDate())
                .arrivalDate(tripCreateReqDto.getArrivalDate())
                .beenFlag(false)
                .corpLat(tripCreateReqDto.getCorpLat())
                .corpLon(tripCreateReqDto.getCorpLon())
                .user(user)
                .build();
    }

    public void updateTrip(TripUpdateReqDto tripUpdateReqDto){
        this.city = tripUpdateReqDto.getCity();
        this.countryEmogi = tripUpdateReqDto.getCountryEmogi();
        this.abroadFlag = tripUpdateReqDto.isAbroadFlag();
        this.departureDate = tripUpdateReqDto.getDepartureDate();
        this.arrivalDate = tripUpdateReqDto.getArrivalDate();
        this.beenFlag = tripUpdateReqDto.isBeenFlag();
    }

    public void updateBeenFlag(boolean beenFlag){
        this.beenFlag = !beenFlag;
    }

}











