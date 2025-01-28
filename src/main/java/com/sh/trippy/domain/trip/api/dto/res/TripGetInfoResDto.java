package com.sh.trippy.domain.trip.api.dto.res;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TripGetInfoResDto {

    //여행 정보
    private TripInfoResDto tripInfoResDto;
    //여행 계획(리스트)
    private List<LocalDate> tripDateList;

    public TripGetInfoResDto(TripInfoResDto tripInfoResDto, List<LocalDate> tripDateList) {
        this.tripInfoResDto = tripInfoResDto;
        this.tripDateList = tripDateList;
    }
}
