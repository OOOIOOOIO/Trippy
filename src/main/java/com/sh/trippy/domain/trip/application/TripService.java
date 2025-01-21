package com.sh.trippy.domain.trip.application;

import com.sh.trippy.domain.plan.domain.repository.PlanRepository;
import com.sh.trippy.domain.trip.api.dto.req.TripCreateReqDto;
import com.sh.trippy.domain.trip.api.dto.req.TripUpdateReqDto;
import com.sh.trippy.domain.trip.api.dto.res.TripCompanionUserInfoResDto;
import com.sh.trippy.domain.trip.api.dto.res.TripGetInfoResDto;
import com.sh.trippy.domain.trip.api.dto.res.TripInfoResDto;
import com.sh.trippy.domain.trip.api.dto.res.TripPlanInfoResDto;
import com.sh.trippy.domain.trip.domain.model.Trip;
import com.sh.trippy.domain.trip.domain.repository.TripRepository;
import com.sh.trippy.domain.tripcompanion.domain.model.TripCompanion;
import com.sh.trippy.domain.user.domain.Users;
import com.sh.trippy.domain.user.domain.repository.UsersRepository;
import com.sh.trippy.global.exception.CustomErrorCode;
import com.sh.trippy.global.exception.CustomException;
import com.sh.trippy.global.log.LogTrace;
import com.sh.trippy.global.resolver.token.userinfo.UserInfoFromHeaderDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class TripService {

    private final TripRepository tripRepository;
    private final PlanRepository planRepository;
    private final UsersRepository usersRepository;

    /**
     * 여행 상세 조회
     *
     */
    @LogTrace
    public TripGetInfoResDto getTripInfo(UserInfoFromHeaderDto userInfoFromHeaderDto, Long tripId){
        //여행 정보
        Trip trip = tripRepository.findById(tripId).orElseThrow(() -> new CustomException(CustomErrorCode.NotExistTrip));

        TripInfoResDto tripInfoResDto = new TripInfoResDto(trip);

        List<TripCompanion> tripCompanionList = trip.getTripCompanionList();

        if(tripCompanionList != null){
            // 동반자 수 추가
            tripInfoResDto.setCompanionCnt(tripCompanionList.size());

            List<TripCompanionUserInfoResDto> tripCompanionUserInfoList = new ArrayList<>();

            for(TripCompanion tripCompanion : tripCompanionList){
                Users users = tripCompanion.getUsers();
                tripCompanionUserInfoList.add(new TripCompanionUserInfoResDto(users));
            }

            //동반자 정보 리스트 추가
            tripInfoResDto.setTripCompanionUserInfoList(tripCompanionUserInfoList);

        }

        //d-day추가
        tripInfoResDto.setDDay(calculateDDay(trip.getDepartureDate(), trip.getArrivalDate()));


        //여행 계획(리스트), tripDate 오름차순 -> {"date" : {~~~}} 이케 수정해야 함
        List<TripPlanInfoResDto> tripPlanInfoResDtoList = planRepository.findAllByTripOrderByTripDate(trip).stream()
                .map(TripPlanInfoResDto::new)
                .collect(Collectors.toList());


        TripGetInfoResDto tripGetInfoResDto = new TripGetInfoResDto(tripInfoResDto, tripPlanInfoResDtoList);

        return tripGetInfoResDto;
    }

    /**
     * 여행 생성
     */
    @LogTrace
    public void createTrip(UserInfoFromHeaderDto userInfoFromHeaderDto, TripCreateReqDto tripCreateReqDto){
        Users user = getUser(userInfoFromHeaderDto.getUserId());

        Trip trip = Trip.createTrip(tripCreateReqDto, user);

        tripRepository.save(trip);

    }


    /**
     * 여행 수정
     */
    @LogTrace
    public void updateTrip(Long tripId, TripUpdateReqDto tripUpdateReqDto) {
        Trip trip = tripRepository.findById(tripId).orElseThrow(() -> new CustomException(CustomErrorCode.NotExistTrip));

        trip.updateTrip(tripUpdateReqDto);
    }

    /**
     * 여행 삭제
     */
    @LogTrace
    public void deleteTrip(Long tripId){
        Trip trip = tripRepository.findById(tripId).orElseThrow(() -> new CustomException(CustomErrorCode.NotExistTrip));

        tripRepository.delete(trip);

    }

    /**
     * 여행 다녀왔다는 거 수정
     */
    @LogTrace
    public void updateBeenFlag(Long tripId){
        Trip trip = tripRepository.findById(tripId).orElseThrow(() -> new CustomException(CustomErrorCode.NotExistTrip));

        trip.updateBeenFlag(trip.isBeenFlag());

    }



    /**
     * Home - 여행 통계 조회
     */


    /**
     * 유저 조회
     */
    @LogTrace
    private Users getUser(Long userId){

        return usersRepository.findById(userId).orElseThrow(() -> new CustomException(CustomErrorCode.UserNotFoundException));
    }

    /**
     * d-day 구하기
     * @param departureDate
     * @param arrivalDate
     * @return
     */
    private int calculateDDay(LocalDate departureDate, LocalDate arrivalDate){

        int betweenCnt = (int)ChronoUnit.DAYS.between(departureDate, arrivalDate);

        return betweenCnt;
    }



}
