package com.sh.trippy.domain.plan.api.dto.res;

import com.sh.trippy.domain.plan.domain.model.Plan;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PlanInfoDto {

    private String title;
    private String place;
    private LocalDateTime plannedAt;
    private String memo;
    private int priority;
    private LocalDate tripDate;

    public PlanInfoDto(Plan plan) {
        this.title = plan.getTitle();
        this.place = plan.getPlace();
        this.plannedAt = plan.getPlannedAt();
        this.memo = plan.getMemo();
        this.priority = plan.getPriority();
        this.tripDate = plan.getTripDate();
    }
}
