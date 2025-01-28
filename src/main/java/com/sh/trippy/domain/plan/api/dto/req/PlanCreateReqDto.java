package com.sh.trippy.domain.plan.api.dto.req;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PlanCreateReqDto {
    private String title;
    private String place;
    private LocalDateTime plannedAt;
    private String memo;
    private int priority;
    private LocalDate tripDate;
    private Double corpLat; // 위도
    private Double corpLon; // 경도


}
