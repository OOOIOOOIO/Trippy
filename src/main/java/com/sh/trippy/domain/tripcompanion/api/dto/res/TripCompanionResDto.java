package com.sh.trippy.domain.tripcompanion.api.dto.res;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TripCompanionResDto {

    private List<TripCompanionDto> tripCompanionDtoList;

    public TripCompanionResDto(List<TripCompanionDto> tripCompanionDtoList) {
        this.tripCompanionDtoList = tripCompanionDtoList;
    }
}
