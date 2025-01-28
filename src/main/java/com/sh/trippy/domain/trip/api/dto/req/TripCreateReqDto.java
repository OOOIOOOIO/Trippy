package com.sh.trippy.domain.trip.api.dto.req;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TripCreateReqDto {
    private String city;
    private String countryEmogi;
    private boolean abroadFlag;
    private LocalDate departureDate;
    private LocalDate arrivalDate;
    private Double corpLat; // 위도
    private Double corpLon; // 경도



}
