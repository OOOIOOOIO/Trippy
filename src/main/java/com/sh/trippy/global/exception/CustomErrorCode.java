package com.sh.trippy.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@Getter
@AllArgsConstructor
public enum CustomErrorCode {

    // client
    AlreadyExistEmailException(BAD_REQUEST, "C001", "이미 존재하는 이메일입니다."),
    UserNotFoundException(BAD_REQUEST, "C002", "유저가 존재하지 않습니다."),
    AlreadyDeletedException(BAD_REQUEST, "C003", "이미 삭제되었습니다."),
    DuplicateSaveAttemptedException(BAD_REQUEST, "C004", "중복 저장을 시도하였습니다."),
    DuplicateDeleteAttemptedException(BAD_REQUEST, "C005", "중복 삭제를 시도하였습니다."),
    MethodArgumentNotValidException(BAD_REQUEST, "C006", "유효하지 않은 형식입니다."),
    IllegalArgumentException(BAD_REQUEST,"C007", "적절하지 못한 인자입니다."),
    NoHandlerFoundException(BAD_REQUEST,"C008", "잘못된 uri 요청입니다."),
    MethodNotAllowedException(BAD_REQUEST,"C009", "잘못된 메서드 요청입니다."),
    DuplicateEmailException(BAD_REQUEST,"C010", "중복된 email입니다."),
    AlreadyPaidVersionException(BAD_REQUEST, "C11", "이미 유료버전을 구매하였습니다."),
    NotPaidVersionException(BAD_REQUEST, "C11", "이미 유료버전을 구매하였습니다."),



    // server
    FailToUploadFileS3Exception(INTERNAL_SERVER_ERROR, "S001", "S3 file upload에 실패하였습니다."),
    FailToDeleteFileS3Exception(INTERNAL_SERVER_ERROR, "S001", "S3 file delete에 실패하였습니다."),
    FailToSaveFileInRedisException(INTERNAL_SERVER_ERROR,"S002", "redis에 데이터 저장을 실패하였습니다."),
    FailToDeleteFIleInRedisException(INTERNAL_SERVER_ERROR, "S003", "redis에서 데이터 삭제를 실패하였습니다."),
    NotExistTripException(INTERNAL_SERVER_ERROR, "S004", "해당 여행이 존재하지 않습니다."),
    NotExistPlanException(INTERNAL_SERVER_ERROR, "S004", "해당 계획이 존재하지 않습니다."),
    NotExistRuleException(INTERNAL_SERVER_ERROR, "S005", "해당 목표의 규칙이 존재하지 않습니다."),
    NotExistTripReplyException(INTERNAL_SERVER_ERROR, "S006", "해당 후기가 존재하지 않습니다."),
    NotExistCompanionException(INTERNAL_SERVER_ERROR, "S007", "해당 동반자가 존재하지 않습니다."),
    NotExistInvitationException(INTERNAL_SERVER_ERROR, "S008", "해당 초대가 존재하지 않습니다."),




    // security
    UsernameNotFoundException(NOT_FOUND, "SC001", "유저가 존재하지 않습니다."),
    UnauthorizedException(UNAUTHORIZED, "SC002", "인증에 실패하였습니다.");


    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}
