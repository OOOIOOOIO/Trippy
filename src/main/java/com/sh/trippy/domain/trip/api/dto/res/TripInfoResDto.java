package com.sh.trippy.domain.trip.api.dto.res;

import com.sh.trippy.domain.trip.domain.model.Trip;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TripInfoResDto {
    String country;
    String countryEmogi;
    LocalDate departureDate;
    LocalDate arrivalDate;
    boolean abroadFlag;
    boolean beenFlag;
    int companionCnt; // 동반인 n명과 함께~
    List<TripCompanionUserInfoResDto> tripCompanionUserInfoList; // 동반인 n명의 유저 정보
    int dDay; // dDay -> 계산

    public TripInfoResDto(Trip trip) {
        this.country = trip.getCountry();
        this.countryEmogi = trip.getCountryEmogi();
        this.departureDate = trip.getDepartureDate();
        this.arrivalDate = trip.getArrivalDate();
        this.abroadFlag = trip.isAbroadFlag();
        this.beenFlag = trip.isBeenFlag();
        this.companionCnt = 0;
        this.tripCompanionUserInfoList = null;
        this.dDay = 0;

    }

    public void setCompanionCnt(int companionCnt){
        this.companionCnt = companionCnt;
    }

    public void setTripCompanionUserInfoList(List<TripCompanionUserInfoResDto> tripCompanionUserInfoList){
        this.tripCompanionUserInfoList = tripCompanionUserInfoList;
    }

    public void setDDay(int dDay){
        this.dDay = dDay;
    }
}
