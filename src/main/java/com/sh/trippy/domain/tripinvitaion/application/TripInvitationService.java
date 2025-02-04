package com.sh.trippy.domain.tripinvitaion.application;

import com.sh.trippy.domain.trip.domain.model.Trip;
import com.sh.trippy.domain.trip.domain.repository.TripRepository;
import com.sh.trippy.domain.tripinvitaion.domain.model.TripInvitation;
import com.sh.trippy.domain.tripinvitaion.domain.repository.TripInvitationRepository;
import com.sh.trippy.domain.user.domain.Users;
import com.sh.trippy.domain.user.domain.repository.UsersRepository;
import com.sh.trippy.global.exception.CustomErrorCode;
import com.sh.trippy.global.exception.CustomException;
import com.sh.trippy.global.resolver.token.userinfo.UserInfoFromHeaderDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class TripInvitationService {

    private final TripInvitationRepository tripInvitationRepository;
    private final TripRepository tripRepository;
    private final UsersRepository usersRepository;




    /**
     * Host가 Guest 초대하기
     */
    public void inviteGuest(UserInfoFromHeaderDto userInfoFromHeaderDto, Long tripId, String nickname) {
        Users usersTo = usersRepository.findByNickname(nickname).orElseThrow(() -> new CustomException(CustomErrorCode.UserNotFoundException));
        Trip trip = tripRepository.findById(tripId).orElseThrow(() -> new CustomException(CustomErrorCode.NotExistTripException));

        Long userFrom = userInfoFromHeaderDto.getUserId();

        TripInvitation tripInvitation = TripInvitation.createTripInvitaion(trip, userFrom, usersTo.getUserId());

        tripInvitationRepository.save(tripInvitation);




    }



    /**
     * Host가 Guest 초대 취소하기
     */
    public void deleteInvitation(Long invitationId) {
        TripInvitation tripInvitation = tripInvitationRepository.findById(invitationId).orElseThrow(() -> new CustomException(CustomErrorCode.NotExistInvitationException));

        tripInvitationRepository.delete(tripInvitation);
    }


    /**
     * Guest가 Host의 초대 수락하기
     */



    /**
     * Geust가 Host의 초대 거절하기
     */



    /**
     * Host의 초대한 내역 보기
     */



    /**
     * GUEST의 초대받은 내역 보기
     */




}
