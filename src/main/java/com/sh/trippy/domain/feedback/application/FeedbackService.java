package com.sh.trippy.domain.feedback.application;


import com.sh.trippy.domain.feedback.domain.model.Feedback;
import com.sh.trippy.domain.feedback.domain.repository.FeedbackRepository;
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
@Transactional
@RequiredArgsConstructor
public class FeedbackService {

    private final FeedbackRepository feedbackRepository;
    private final UsersRepository usersRepository;

    public void createFeedback(UserInfoFromHeaderDto userInfoFromHeaderDto, String content){

        Users users = usersRepository.findById(userInfoFromHeaderDto.getUserId()).orElseThrow(() -> new CustomException(CustomErrorCode.UserNotFoundException));
        Feedback feedback = Feedback.createFeedback(content, users);

        feedbackRepository.save(feedback);

    }
}
