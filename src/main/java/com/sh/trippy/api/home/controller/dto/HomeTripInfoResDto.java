package com.sh.trippy.api.home.controller.dto;

import com.sh.trippy.domain.trip.api.dto.res.TripCompanionUserInfoResDto;
import com.sh.trippy.domain.trip.domain.model.Trip;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class HomeTripInfoResDto {

    private Long tripId;
    private String city;
    private String countryEmogi;
    private LocalDate departureDate;
    private LocalDate arrivalDate;
    private boolean abroadFlag;
    private boolean beenFlag;
    private int companionCnt; // 동반인 n명과 함께~
    private int dDay; // dDay -> 계산
    private Double corpLat; // 위도
    private Double corpLon; // 경도

    private HomeTripStatusResDto homeTripStatusResDto;

    public HomeTripInfoResDto(Trip trip) {
        this.tripId = trip.getTripId();
        this.city = trip.getCity();
        this.countryEmogi = trip.getCountryEmogi();
        this.departureDate = trip.getDepartureDate();
        this.arrivalDate = trip.getArrivalDate();
        this.abroadFlag = trip.isAbroadFlag();
        this.beenFlag = trip.isBeenFlag();
        this.companionCnt = 0;
        this.dDay = 0;
        this.corpLat = trip.getCorpLat();
        this.corpLon = trip.getCorpLon();

    }

    public void setCompanionCnt(int companionCnt){
        this.companionCnt = companionCnt;
    }

    public void setDDay(int dDay){
        this.dDay = dDay;
    }

    public void setHomeTripStatusResDto(HomeTripStatusResDto homeTripStatusResDto){
        this.homeTripStatusResDto = homeTripStatusResDto;
    }
}
