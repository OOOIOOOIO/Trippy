package com.sh.trippy.domain.reply.application;

import com.sh.trippy.domain.reply.api.dto.req.ReplyCreateReqDto;
import com.sh.trippy.domain.reply.api.dto.res.ReplyInfoDto;
import com.sh.trippy.domain.reply.api.dto.res.ReplyResDto;
import com.sh.trippy.domain.reply.domain.model.Reply;
import com.sh.trippy.domain.reply.domain.repository.ReplyQueryRepository;
import com.sh.trippy.domain.reply.domain.repository.ReplyRepository;
import com.sh.trippy.domain.trip.domain.model.Trip;
import com.sh.trippy.domain.trip.domain.repository.TripRepository;
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

@Slf4j
@Transactional
@Service
@RequiredArgsConstructor
public class ReplyService {

    private final ReplyRepository replyRepository;
    private final ReplyQueryRepository replyQueryRepository;
    private final UsersRepository usersRepository;
    private final TripRepository tripRepository;


    /**
     * 댓글 조회
     */
    public ReplyResDto getTripReply(Long tripId){
        List<ReplyInfoDto> replyInfoDtoList = replyQueryRepository.getTripReplyList(tripId).orElseGet(null);

        if(replyInfoDtoList == null){
            return new ReplyResDto(null);
        }

        return new ReplyResDto(replyInfoDtoList);

    }



    /**
     * 댓글 생성
     */
    public void createReply(Long tripId, ReplyCreateReqDto replyCreateReqDto, UserInfoFromHeaderDto userInfoFromHeaderDto) {
        Users users = usersRepository.findById(userInfoFromHeaderDto.getUserId()).orElseThrow(() -> new CustomException(CustomErrorCode.UserNotFoundException));
        Trip trip = tripRepository.findById(tripId).orElseThrow(() -> new CustomException(CustomErrorCode.NotExistTripException));

        Reply reply = Reply.createReply(replyCreateReqDto, trip, users);

        replyRepository.save(reply);

    }

//    /**
//     * 댓글 수정
//     */

    /**
     * 댓글 삭제
     */
    public void deleteReply(Long replyId){
        Reply reply = replyRepository.findById(replyId).orElseThrow(() -> new CustomException(CustomErrorCode.NotExistTripReplyException));

        replyRepository.delete(reply);

    }



}
