package com.sh.trippy.domain.plan.domain.model;

import com.sh.trippy.domain.common.BaseTimeEntity;
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

    @Builder
    private Plan(String title, LocalDate tripDate, String place, LocalDateTime plannedAt, String memo, Integer priority) {
        this.title = title;
        this.tripDate = tripDate;
        this.place = place;
        this.plannedAt = plannedAt;
        this.memo = memo;
        this.priority = priority;
    }

    public static Plan createPlan() {

        return null;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tripId")
    private Trip trip;


}




