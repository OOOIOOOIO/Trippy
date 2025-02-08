package com.sh.trippy.domain.trip.application;

import com.sh.trippy.api.home.controller.dto.HomeTripInfoResDto;
import com.sh.trippy.domain.plan.domain.repository.PlanRepository;
import com.sh.trippy.domain.trip.api.dto.req.TripCreateReqDto;
import com.sh.trippy.domain.trip.api.dto.req.TripUpdateReqDto;
import com.sh.trippy.domain.trip.api.dto.res.TripCompanionUserInfoResDto;
import com.sh.trippy.domain.trip.api.dto.res.TripGetInfoResDto;
import com.sh.trippy.domain.trip.api.dto.res.TripInfoResDto;
import com.sh.trippy.domain.trip.domain.model.Trip;
import com.sh.trippy.domain.trip.domain.repository.TripQueryRepository;
import com.sh.trippy.domain.trip.domain.repository.TripRepository;
import com.sh.trippy.domain.tripcompanion.domain.model.TripCompanion;
import com.sh.trippy.domain.tripcompanion.domain.model.TripRole;
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

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class TripService {

    private final TripRepository tripRepository;
    private final TripQueryRepository tripQueryRepository;
    private final PlanRepository planRepository;
    private final UsersRepository usersRepository;

    /**
     * 여행 상세 조회
     *
     */
    @LogTrace
    public TripGetInfoResDto getTripInfo(UserInfoFromHeaderDto userInfoFromHeaderDto, Long tripId){
        //여행 정보
        Trip trip = tripRepository.findById(tripId).orElseThrow(() -> new CustomException(CustomErrorCode.NotExistTripException));

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
        tripInfoResDto.setDDay(calculateDDay(trip.getDepartureDate()));


        //여행 계획에 해당하는 날짜 리스트
        List<LocalDate> tripDateList = getTripDateList(trip.getDepartureDate(), trip.getArrivalDate());


//        여행 계획(리스트), tripDate 오름차순 -> {"date" : {~~~}} 이케 수정해야 함
//        List<TripPlanInfoResDto> tripPlanInfoResDtoList = planRepository.findAllByTripOrderByTripDate(trip).stream()
//                .map(TripPlanInfoResDto::new)
//                .collect(Collectors.toList());


        TripGetInfoResDto tripGetInfoResDto = new TripGetInfoResDto(tripInfoResDto, tripDateList);

        return tripGetInfoResDto;
    }

    /**
     * 여행 생성
     *
     * user의 paidFlag가 1(true)일 경우에만 HOST넣기
     * 그리고 유저가 유료버전을 구매할 경우 HOST로 넣기
     */
    @LogTrace
    public void createTrip(UserInfoFromHeaderDto userInfoFromHeaderDto, TripCreateReqDto tripCreateReqDto){
        Users user = getUser(userInfoFromHeaderDto.getUserId());

        Trip trip = Trip.createTrip(tripCreateReqDto, user);

        // 여행 최초 생성시 유료버전이라면 동반자에 자신(Host) 추가
        if(user.isPaidFlag()){
            trip.addTripCompanion(TripCompanion.createTripCompanion(TripRole.HOST, trip, user));
        }


        tripRepository.save(trip);

    }


    /**
     * 여행 수정
     */
    @LogTrace
    public void updateTrip(Long tripId, TripUpdateReqDto tripUpdateReqDto) {
        Trip trip = tripRepository.findById(tripId).orElseThrow(() -> new CustomException(CustomErrorCode.NotExistTripException));

        trip.updateTrip(tripUpdateReqDto);
    }

    /**
     * 여행 삭제
     */
    @LogTrace
    public void deleteTrip(Long tripId){
        Trip trip = tripRepository.findById(tripId).orElseThrow(() -> new CustomException(CustomErrorCode.NotExistTripException));

        tripRepository.delete(trip);

    }

    /**
     * 여행 다녀왔다는 거 수정
     */
    @LogTrace
    public void updateBeenFlag(Long tripId){
        Trip trip = tripRepository.findById(tripId).orElseThrow(() -> new CustomException(CustomErrorCode.NotExistTripException));

        trip.updateBeenFlag(trip.isBeenFlag());

    }



    /**
     * Home - 여행 통계 조회
     */


    /**
     * Home - 여행 리스트 조회
     */
    @LogTrace
    public List<HomeTripInfoResDto> getTripList(UserInfoFromHeaderDto userInfoFromHeaderDto){
        Users users = usersRepository.findById(userInfoFromHeaderDto.getUserId()).orElseThrow(() -> new CustomException(CustomErrorCode.UserNotFoundException));


//        List<HomeTripInfoResDto> homeTripInfoResDtoList = tripQueryRepository.getTripListByUserId(users.getUserId())
//                .stream()
//                .map(HomeTripInfoResDto::new)
//                .collect(Collectors.toList());

        List<Trip> tripList = tripQueryRepository.getTripListByUserId(users.getUserId());
        List<HomeTripInfoResDto> homeTripInfoResDtoList = new ArrayList<>();


        for (Trip trip : tripList) {

            HomeTripInfoResDto homeTripInfoResDto = new HomeTripInfoResDto(trip);

            // 동반자
            List<TripCompanion> tripCompanionList = trip.getTripCompanionList();

            if(tripCompanionList != null){
                // 동반자 수 추가
                homeTripInfoResDto.setCompanionCnt(tripCompanionList.size());

            }

            //d-day추가
            homeTripInfoResDto.setDDay(calculateDDay(trip.getDepartureDate()));


            homeTripInfoResDtoList.add(homeTripInfoResDto);
        }



        return homeTripInfoResDtoList;

    }


    /**
     * 유저 조회
     */
    @LogTrace
    private Users getUser(Long userId){

        return usersRepository.findById(userId).orElseThrow(() -> new CustomException(CustomErrorCode.UserNotFoundException));
    }

    /**
     * d-day 구하기
     * 도착일 포함 X
     * eX) 12/1 ~ 12/2 : betweenCnt = 1
     * @param departureDate
     * @return
     */
    private int calculateDDay(LocalDate departureDate){
        LocalDate now = LocalDate.now();

        int betweenCnt = (int)ChronoUnit.DAYS.between(now, departureDate);

        if(betweenCnt < 0) return 0;

        return betweenCnt;
    }

    private List<LocalDate> getTripDateList(LocalDate departureDate, LocalDate arrivalDate){
        List<LocalDate> tripDateList = new ArrayList<>();

        int between = (int) ChronoUnit.DAYS.between(departureDate, arrivalDate); // 도착일 포함X

        for(int i = 0; i <= between; i++){
            LocalDate localDate = departureDate.plusDays(i);
            tripDateList.add(localDate);
        }

        return tripDateList;
    }



}
