package com.sh.trippy.domain.plan.api.dto.res;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PlanInfoResDto {
    private int planCnt;
    private List<PlanInfoDto> planInfoDtoList;


    public PlanInfoResDto(int planCnt, List<PlanInfoDto> planInfoDtoList) {
        this.planCnt = planCnt;
        this.planInfoDtoList = planInfoDtoList;
    }
}
