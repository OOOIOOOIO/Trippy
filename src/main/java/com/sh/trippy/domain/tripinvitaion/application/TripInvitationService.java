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

import java.util.List;
import java.util.Optional;

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
        Users receivedUser = usersRepository.findByNickname(nickname).orElseThrow(() -> new CustomException(CustomErrorCode.UserNotFoundException));
        Trip trip = tripRepository.findById(tripId).orElseThrow(() -> new CustomException(CustomErrorCode.NotExistTripException));

        Long sendUserId = userInfoFromHeaderDto.getUserId();

        TripInvitation tripInvitation = TripInvitation.createTripInvitaion(trip, sendUserId, receivedUser.getUserId());

        tripInvitationRepository.save(tripInvitation);


    }



    /**
     * Host가 Guest 초대 취소하기 or Geust가 Host의 초대 거절하기
     */
    public void deleteInvitation(Long invitationId) {
        TripInvitation tripInvitation = tripInvitationRepository.findById(invitationId).orElseThrow(() -> new CustomException(CustomErrorCode.NotExistInvitationException));

        tripInvitationRepository.delete(tripInvitation);
    }




    /**
     * Host의 초대한 내역 보기
     */
    public void getSendInvitationList(UserInfoFromHeaderDto userInfoFromHeaderDto) {
        List<TripInvitation> tripInvitationList = tripInvitationRepository.findByUserFrom(userInfoFromHeaderDto.getUserId());

        for (TripInvitation tripInvitation : tripInvitationList) {
            Long receivedUserId = tripInvitation.getReceivedUserId();
            Optional<Users> users = usersRepository.findById(receivedUserId);

            // 존재할 때만, 탈퇴한 유저라면 패스
            if(users.isPresent()){
                // 닉네임, 프로필 이미지, 초대수락상태
            }

        }
    }




    /**
     * GUEST의 초대받은 내역 보기
     */
    public void getReceiveInvitationList(UserInfoFromHeaderDto userInfoFromHeaderDto) {
        List<TripInvitation> tripInvitedList = tripInvitationRepository.findByUserTo(userInfoFromHeaderDto.getUserId());

    }



}
