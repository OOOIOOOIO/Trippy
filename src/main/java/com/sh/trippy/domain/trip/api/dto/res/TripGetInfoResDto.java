package com.sh.trippy.domain.trip.api.dto.res;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TripGetInfoResDto {

    //여행 정보
    private TripInfoResDto tripInfoResDto;
    //여행 계획(리스트)
    private List<TripPlanInfoResDto> tripPlanInfoResDtoList;

    public TripGetInfoResDto(TripInfoResDto tripInfoResDto, List<TripPlanInfoResDto> tripPlanInfoResDtoList) {
        this.tripInfoResDto = tripInfoResDto;
        this.tripPlanInfoResDtoList = tripPlanInfoResDtoList;
    }
}
