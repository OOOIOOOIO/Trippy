package com.sh.trippy.domain.user.application;


import com.sh.trippy.api.login.apple.controller.dto.AppleUserInfoResponseDto;
import com.sh.trippy.domain.user.api.dto.UserInfoResDto;
import com.sh.trippy.domain.user.api.dto.UserInfoUpdateReqDto;
import com.sh.trippy.domain.user.domain.Role;
import com.sh.trippy.domain.user.domain.Users;
import com.sh.trippy.domain.user.domain.repository.UsersRepository;
import com.sh.trippy.global.exception.CustomErrorCode;
import com.sh.trippy.global.exception.CustomException;
import com.sh.trippy.global.log.LogTrace;
import com.sh.trippy.global.resolver.token.userinfo.UserInfoFromHeaderDto;
import com.sh.trippy.global.util.aws.AmazonS3Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;


@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class UsersService {

    private final UsersRepository usersRepository;
    private final AmazonS3Service amazonS3Service;

    @Value("${file.path}")
    private String filePath;

    /**
     * mypage - 회원 정보 불러오기
     *
     */
    @LogTrace
    public UserInfoResDto getUserInfo(Long userId){
        Users users = usersRepository.findById(userId).orElseThrow(() -> new CustomException(CustomErrorCode.UserNotFoundException));

        UrlResource profileImg = getProfileImg(users.getProfileImg());

        UserInfoResDto userInfoResDto = new UserInfoResDto(users, profileImg);

        return userInfoResDto;

    }


    /**
     * mypage - 처음 회원 정보 저장(dirty check)
     */
    @LogTrace
    public Long saveUserInfo(Long userId, String nickname, String motherLand, MultipartFile file){
        Users users = usersRepository.findById(userId).orElseThrow(() -> new CustomException(CustomErrorCode.UserNotFoundException));

        // S3에 저장
        String profileImg = amazonS3Service.uploadFile(file);
        log.info("===== profile img link : " + profileImg + "=====");

        users.updateUserInfo(nickname, profileImg, motherLand);

        return userId;
    }

    /**
     * mypage - 회원 정보 수정(dirty check)
     * @param userId
     * @param nickname
     * @param motherLand
     * @param file
     */
    @LogTrace
    public void updateUserInfo(Long userId, String nickname, String motherLand, MultipartFile file){
        Users users = usersRepository.findById(userId).orElseThrow(() -> new CustomException(CustomErrorCode.UserNotFoundException));

        String pastProfileImg = users.getProfileImg();

        // S3에서 삭제
        amazonS3Service.delete(pastProfileImg);

        // S3에 저장
        String newProfileImg = amazonS3Service.uploadFile(file);

        users.updateUserInfo(nickname, newProfileImg, motherLand);

    }


    /**
     * 기존 회원인지 확인 -> 로그인 후 호출
     * email로 검증
     */
    public void isUserExist(){

    }


    /**
     * 회원 저장
     */
//    public Long saveUserInfo(KakaoUserInfoResDto kakaoUserInfoResDto) {
//        Users user = Users.createUser(kakaoUserInfoResDto.getEmail(), kakaoUserInfoResDto.getProvider(), Role.USER);
//
//        Users savedUser = usersRepository.save(user);
//
//        return savedUser.getUserId();
//    }

    /**
     * 회원 정보 수정, 로컬에 이미지 저장
     * Apple 회원 저장
     * @param appleUserInfoResponseDto
     */
    public Long saveAppleUser(AppleUserInfoResponseDto appleUserInfoResponseDto) {
        String nickname = generateNickname();
        Users user = Users.createUser(appleUserInfoResponseDto.getEmail(),
                appleUserInfoResponseDto.getRefreshToken(),
                appleUserInfoResponseDto.getProvider(),
                nickname,
                Role.USER);

        Users savedUser = usersRepository.save(user);

        return savedUser.getUserId();

    }


    /**
     * 회원 정보 수정
>>>>>>> feature/apple
     * imgFileName = userId + @ + originalFileName
     */
//    public void updateUserInfo(Long userId, String nickname, String motherLand, MultipartFile file) {
//        Users users = usersRepository.findById(userId).orElseThrow(() -> new CustomException(CustomErrorCode.UserNotFoundException));
//        String newFileName = null;
//
//        // upload할 img가 있다면
//        if(file != null){
//            //만약 이미지가 존재하지 않는다면
//            if(users.getProfileImg() == null){
//                // 그냥 저장
//                newFileName = saveFile(file, userId);
//            }
//            else{
//                // local에서 img 삭제 후 저장
//                try {
//                    File prevFile = new File(filePath +"/"+ URLDecoder.decode(users.getProfileImg(), "UTF-8"));
//                    prevFile.delete();
//                }
//                catch (UnsupportedEncodingException e) {
//                    log.error("파일 삭제 에러 : " + e.getMessage());
//                }
//
//                newFileName = saveFile(file, userId);
//
//            }
//
//            log.info("==== save file name : " + newFileName);
//        }
//
//
//
//        users.updateUserInfo(userInfoUpdateReqDto, newFileName, "모국어~~~~~~~");
//
//    }


//    public int isUserExist(UserInfoFromHeaderDto userInfoFromHeaderDto) {
//        if(usersQueryRepository.findByEmailAndProvider(userInfoFromHeaderDto.getEmail(), userInfoFromHeaderDto.getProvider()).isEmpty()){
//            return 0;
//        }
//
//        return 1;
//    }


//    public Long isUserExist(KakaoUserInfoResDto kakaoUserInfoResDto) {
//        Optional<Users> user = usersQueryRepository.findByEmailAndProvider(kakaoUserInfoResDto.getEmail(), kakaoUserInfoResDto.getProvider());
//
//        if(!user.isEmpty()){
//            return user.get().getUserId();
//        }
//
//        return -1L;
//    }


    /**
     * 파일 저장
     */
    private String saveFile(MultipartFile file, Long userId){
        String originalFileName = file.getOriginalFilename();
        String newFileName = userId + "@" + originalFileName.substring(originalFileName.lastIndexOf("\\")+1);

        File saveFile = new File(filePath, newFileName);

        try{
            file.transferTo(saveFile);
        }
        catch (Exception e){
            log.error("===== profile img save 에러  : " + e.getMessage());
        }

        return newFileName;
    }


    /**
     * 파일 조회
     */
    private UrlResource getProfileImg(String serverProfileImg ) {
        UrlResource profileImg = null;
        try {
            profileImg = new UrlResource("file:" + filePath + "/" + serverProfileImg);

        }catch (Exception e){
            log.error("profile img 불러오기 실패 : " + e.getMessage());
        }

        return profileImg;
    }

    /**
     * 초기 닉네임 : uuid + 랜덤 1자리 정수
     * @return 랜덤 uuid
     */
    private String generateNickname(){

        String uuid = UUID.randomUUID().toString().substring(10) + (int)(Math.random() * 10);

        return uuid;
    }


}
