package com.sh.trippy.domain.plan.application;

import com.sh.trippy.domain.plan.api.dto.req.PlanCreateReqDto;
import com.sh.trippy.domain.plan.api.dto.req.PlanUpdateReqDto;
import com.sh.trippy.domain.plan.api.dto.res.PlanInfoDto;
import com.sh.trippy.domain.plan.api.dto.res.PlanInfoResDto;
import com.sh.trippy.domain.plan.domain.model.Plan;
import com.sh.trippy.domain.plan.domain.repository.PlanQueryRepository;
import com.sh.trippy.domain.plan.domain.repository.PlanRepository;
import com.sh.trippy.domain.trip.domain.model.Trip;
import com.sh.trippy.domain.trip.domain.repository.TripRepository;
import com.sh.trippy.global.exception.CustomErrorCode;
import com.sh.trippy.global.exception.CustomException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class PlanService {

    private final PlanRepository planRepository;
    private final TripRepository tripRepository;
    private final PlanQueryRepository planQueryRepository;

    /**
     * 해당 일자 계획 상세 조회
     */
    public PlanInfoResDto getTripPlan(Long tripId, LocalDate tripDate){
        List<Plan> tripPlanInfoWithDate = planQueryRepository.getTripPlanInfoWithDate(tripId, tripDate).orElseGet(null);

        if(tripPlanInfoWithDate == null){
            return new PlanInfoResDto(0, null);
        }

        int planCnt = tripPlanInfoWithDate.size();

        List<PlanInfoDto> planInfoDtoList = tripPlanInfoWithDate.stream()
                .map(PlanInfoDto::new)
                .collect(Collectors.toList());

        return new PlanInfoResDto(planCnt, planInfoDtoList);

    }

    /**
     * 계획 생성
     */
    public void createTripPlan(Long tripId, PlanCreateReqDto planCreateReqDto){
        Trip trip = tripRepository.findById(tripId).orElseThrow(() -> new CustomException(CustomErrorCode.NotExistTrip));
        Plan plan = Plan.createPlan(planCreateReqDto, trip);

        planRepository.save(plan);

    }


    /**
     * 계획 수정
     */
    public void updateTripPlan(Long planId, PlanUpdateReqDto planUpdateReqDto){
        Plan plan = planRepository.findById(planId).orElseThrow(() -> new CustomException(CustomErrorCode.NotExistPlan));

        plan.updatePlan(planUpdateReqDto);


    }


    /**
     * 계획 삭제
     */
    public void deleteTripPlan(Long planId){
        Plan plan = planRepository.findById(planId).orElseThrow(() -> new CustomException(CustomErrorCode.NotExistPlan));

        planRepository.delete(plan);
    }

    /**
     * 완료여부 설정
     */
    public void updateCompleteStatus(Long planId){
        Plan plan = planRepository.findById(planId).orElseThrow(() -> new CustomException(CustomErrorCode.NotExistPlan));

        plan.updateCompleteStatus(plan.getCompleteStatus());
    }





}
