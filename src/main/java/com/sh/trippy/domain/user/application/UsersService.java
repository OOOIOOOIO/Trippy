package com.sh.trippy.domain.user.application;


import com.sh.trippy.api.home.controller.dto.HomeUserInfoResDto;
import com.sh.trippy.api.jwt.application.TokenService;
import com.sh.trippy.api.login.UserTokenResDto;
import com.sh.trippy.api.login.apple.controller.dto.AppleResponseInfoResDto;
import com.sh.trippy.domain.user.api.dto.MypageUserInfoResDto;
import com.sh.trippy.domain.user.domain.Role;
import com.sh.trippy.domain.user.domain.Users;
import com.sh.trippy.domain.user.domain.repository.UsersRepository;
import com.sh.trippy.global.exception.CustomErrorCode;
import com.sh.trippy.global.exception.CustomException;
import com.sh.trippy.global.jwt.JwtUtils;
import com.sh.trippy.global.log.LogTrace;
import com.sh.trippy.global.resolver.token.userinfo.UserInfoFromHeaderDto;
import com.sh.trippy.global.util.apple.AppleLoginClient;
import com.sh.trippy.global.util.aws.AmazonS3Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;
import java.util.UUID;


@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class UsersService {

    private final UsersRepository usersRepository;
    private final AmazonS3Service amazonS3Service;
    private final AppleLoginClient appleLoginClient;
    private final JwtUtils jwtUtils;
    private final TokenService tokenService;

    @Value("${file.path}")
    private String filePath;


    /**
     * ============================================== USER ===========================================================
     */

    @LogTrace
    public HomeUserInfoResDto getUserInfo(UserInfoFromHeaderDto userInfoFromHeaderDto){
        Users users = usersRepository.findById(userInfoFromHeaderDto.getUserId()).orElseThrow(() -> new CustomException(CustomErrorCode.UserNotFoundException));

        return new HomeUserInfoResDto(users);
    }

    /**
     * 탈퇴하기
     * 애플인 경우에만 로그아웃 후 DB에서 user 정보 삭제
     * @param userInfoFromHeaderDto
     */
    @LogTrace
    public void cancelAccount(UserInfoFromHeaderDto userInfoFromHeaderDto){
        Users users = usersRepository.findById(userInfoFromHeaderDto.getUserId()).orElseThrow(() -> new CustomException(CustomErrorCode.UserNotFoundException));

        if(userInfoFromHeaderDto.getProvider().equals("apple")){
            appleLoginClient.logoutAppleAccount(users.getRefreshToken());
        }


        usersRepository.delete(users);
    }


    @LogTrace
    public UserTokenResDto purchasePaidVersion(UserInfoFromHeaderDto userInfoFromHeaderDto){
        Users users = usersRepository.findById(userInfoFromHeaderDto.getUserId()).orElseThrow(() -> new CustomException(CustomErrorCode.UserNotFoundException));

        boolean currentUserPaidVersion = users.isPaidFlag();

        if(!currentUserPaidVersion){
            throw new CustomException(CustomErrorCode.AlreadyPaidVersionException);
        }

        users.updatePaidFlag(currentUserPaidVersion);

        Long userId = users.getUserId();
        int existUser = 1;
        boolean paidFlag = true;

        // token 발급, 첫로그인은
        String jwtAccessToken = jwtUtils.generateAccessToken(userId, users.getEmail(), users.getProvider(), paidFlag);
        String jwtRefreshToken = jwtUtils.generateRefreshToken(userId, users.getEmail(), users.getProvider(), paidFlag);

        // redis 저장
        tokenService.uploadAccessTokenToRedis(jwtAccessToken, userId);
        tokenService.uploadRefreshTokenToRedis(jwtRefreshToken, userId);


        return new UserTokenResDto(jwtAccessToken, jwtRefreshToken, existUser);


    }


    /**
     * ================================================ LOGIN =========================================================
     */


    /**
     * 새로 로그인할 때마다 refreshToken 재발급 됨
     * Apple 회원 저장
     * @param appleResponseInfoResDto
     */
    @LogTrace
    public String saveAppleUser(AppleResponseInfoResDto appleResponseInfoResDto) {
        String nickname = generateNickname();

        Users userExist = isUserExist(appleResponseInfoResDto.getEmail());
        if(userExist != null){
            userExist.updateAppleRefreshToken(appleResponseInfoResDto.getRefreshToken());
            return userExist.getUserId() + "," + "none,exist";
        }

        Users user = Users.createUser(appleResponseInfoResDto.getEmail(),
                appleResponseInfoResDto.getRefreshToken(),
                nickname,
                appleResponseInfoResDto.getProvider(),
                Role.USER);

        Users savedUser = usersRepository.save(user);

        if(savedUser.isPaidFlag()) return savedUser.getUserId() +"," + "paid,new";

        return savedUser.getUserId() + "," + "none,new";

    }

    @LogTrace
    public String getAppleUserRefreshToken(Long userId){
        Users users = usersRepository.findById(userId).orElseThrow(() -> new CustomException(CustomErrorCode.UserNotFoundException));

        return users.getRefreshToken();
    }


    @LogTrace
    public int saveGoogleUser(String email, String provider) {
        String nickname = generateNickname();

        if(isUserExist(email) != null){
            return 1;
        }

        Users user = Users.createUser(email,
                "NONE",
                nickname,
                provider,
                Role.USER);

        usersRepository.save(user);

        return -1;

    }


    /**
     * =========================================================================================================
     */

    /**
     * mypage - 회원 정보 불러오기
     *
     */
    @LogTrace
    public MypageUserInfoResDto getMypageProfile(Long userId){
        Users users = usersRepository.findById(userId).orElseThrow(() -> new CustomException(CustomErrorCode.UserNotFoundException));

        MypageUserInfoResDto mypageUserInfoResDto = new MypageUserInfoResDto(users);

        return mypageUserInfoResDto;

    }

    /**
     * mypage - 처음 회원 정보 저장(dirty check)
     */
    @LogTrace
    public Long saveMypageProfile(Long userId, String nickname, String motherLand, MultipartFile file){
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
    public void updateMypageProfile(Long userId, String nickname, String motherLand, MultipartFile file){
        Users users = usersRepository.findById(userId).orElseThrow(() -> new CustomException(CustomErrorCode.UserNotFoundException));

        String pastProfileImg = users.getProfileImg();

        // S3에서 삭제
        amazonS3Service.delete(pastProfileImg);

        // S3에 저장
        String newProfileImg = amazonS3Service.uploadFile(file);

        users.updateUserInfo(nickname, newProfileImg, motherLand);

    }



    private Users isUserExist(String email) {
        Optional<Users> user = usersRepository.findByEmail(email);

        if(user.isEmpty()){
            return null;
        }
        return user.get();
    }




    /**
     * 초기 닉네임 : uuid + 랜덤 1자리 정수
     * @return 랜덤 uuid
     */
    private String generateNickname(){

        String uuid = UUID.randomUUID().toString().substring(10) + (int)(Math.random() * 10);

        return uuid;
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

//    /**
//     * 회원 정보 수정
//     * 회원 정보 수정, 로컬에 이미지 저장
//     * imgFileName = userId + @ + originalFileName
//     */
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

    /**
     * 서버 내 파일 저장
     */
//    private String saveFile(MultipartFile file, Long userId){
//        String originalFileName = file.getOriginalFilename();
//        String newFileName = userId + "@" + originalFileName.substring(originalFileName.lastIndexOf("\\")+1);
//
//        File saveFile = new File(filePath, newFileName);
//
//        try{
//            file.transferTo(saveFile);
//        }
//        catch (Exception e){
//            log.error("===== profile img save 에러  : " + e.getMessage());
//        }
//
//        return newFileName;
//    }

    /**
     * 서버 내 파일 조회
     */
//    private UrlResource getProfileImg(String serverProfileImg ) {
//        UrlResource profileImg = null;
//        try {
//            profileImg = new UrlResource("file:" + filePath + "/" + serverProfileImg);
//
//        }catch (Exception e){
//            log.error("profile img 불러오기 실패 : " + e.getMessage());
//        }
//
//        return profileImg;
//    }

}
