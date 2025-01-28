package com.sh.trippy.domain.plan.domain.model;

import com.sh.trippy.domain.common.BaseTimeEntity;
import com.sh.trippy.domain.common.CompleteStatus;
import com.sh.trippy.domain.plan.api.dto.req.PlanCreateReqDto;
import com.sh.trippy.domain.plan.api.dto.req.PlanUpdateReqDto;
import com.sh.trippy.domain.trip.domain.model.Trip;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Plan extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long planId;
    private String title;
    private LocalDate tripDate;
    private String place;
    private LocalDateTime plannedAt;
    private String memo;
    private Integer priority;
    @Enumerated(EnumType.STRING)
    private CompleteStatus completeStatus;

    private Double corpLat; // 위도
    private Double corpLon; // 경도

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tripId")
    private Trip trip;

    @Builder
    private Plan(String title, LocalDate tripDate, String place, LocalDateTime plannedAt, String memo, Integer priority, Double corpLat, Double corpLon, Trip trip) {
        this.title = title;
        this.tripDate = tripDate;
        this.place = place;
        this.plannedAt = plannedAt;
        this.memo = memo;
        this.priority = priority;
        this.completeStatus = CompleteStatus.WAIT;
        this.corpLat = corpLat;
        this.corpLon = corpLon;
        this.trip = trip;
    }

    public static Plan createPlan(PlanCreateReqDto planCreateReqDto, Trip trip) {
        return Plan.builder()
                .title(planCreateReqDto.getTitle())
                .tripDate(planCreateReqDto.getTripDate())
                .place(planCreateReqDto.getPlace())
                .plannedAt(planCreateReqDto.getPlannedAt())
                .memo(planCreateReqDto.getMemo())
                .priority(planCreateReqDto.getPriority())
                .corpLat(planCreateReqDto.getCorpLat())
                .corpLon(planCreateReqDto.getCorpLon())
                .trip(trip)
                .build();

    }

    public void updatePlan(PlanUpdateReqDto planUpdateReqDto) {
        this.title = planUpdateReqDto.getTitle();
        this.tripDate = planUpdateReqDto.getTripDate();
        this.place = planUpdateReqDto.getPlace();
        this.plannedAt = planUpdateReqDto.getPlannedAt();
        this.memo = planUpdateReqDto.getMemo();
        this.priority = planUpdateReqDto.getPriority();
        this.corpLat = planUpdateReqDto.getCorpLat();
        this.corpLon = planUpdateReqDto.getCorpLon();
    }

    public void updateCompleteStatus(CompleteStatus completeStatus){
        this.completeStatus = completeStatus.equals(CompleteStatus.COMP) ? CompleteStatus.WAIT : CompleteStatus.COMP;
    }




}




