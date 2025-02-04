package com.sh.trippy.domain.tripcompanion.application;

import com.sh.trippy.domain.trip.domain.model.Trip;
import com.sh.trippy.domain.trip.domain.repository.TripRepository;
import com.sh.trippy.domain.tripcompanion.api.dto.res.TripCompanionDto;
import com.sh.trippy.domain.tripcompanion.api.dto.res.TripCompanionResDto;
import com.sh.trippy.domain.tripcompanion.domain.model.TripCompanion;
import com.sh.trippy.domain.tripcompanion.domain.model.TripRole;
import com.sh.trippy.domain.tripcompanion.domain.repository.TripCompanionRepository;
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

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class TripCompanionService {

    private final TripCompanionRepository tripCompanionRepository;
    private final TripRepository tripRepository;
    private final UsersRepository usersRepository;

    /**
     * 해당 여행 동반인들 전체 조회
     */
    public TripCompanionResDto getCompanionList(Long tripId){
        List<TripCompanionDto> tripCompanionDtoList = tripCompanionRepository.findByTrip_TripId(tripId).stream()
                .map(TripCompanionDto::new)
                .collect(Collectors.toList());

        return new TripCompanionResDto(tripCompanionDtoList);
    }

    /**
     * (Guest가 Host의 초대 수락하기 후) 동반자 추가
     */
    public void addCompanion(UserInfoFromHeaderDto userInfoFromHeaderDto, Long tripId){
        Users user = usersRepository.findById(userInfoFromHeaderDto.getUserId()).orElseThrow(() -> new CustomException(CustomErrorCode.UserNotFoundException));
        Trip trip = tripRepository.findById(tripId).orElseThrow(() -> new CustomException(CustomErrorCode.NotExistTripException));

        tripCompanionRepository.save(TripCompanion.createTripCompanion(TripRole.GUEST, trip, user));

    }


    /**
     * 호스트가 동반자 퇴출시키기 & 게스트가 스스로 탈퇴하기
     */
    @LogTrace
    public void quitCompanion(Long companionId) {
        TripCompanion tripCompanion = tripCompanionRepository.findById(companionId).orElseThrow(() -> new CustomException(CustomErrorCode.NotExistCompanionException));

        tripCompanionRepository.delete(tripCompanion);

    }




}
