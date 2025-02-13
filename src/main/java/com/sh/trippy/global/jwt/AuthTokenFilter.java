package com.sh.trippy.global.jwt;

import com.sh.trippy.api.jwt.application.TokenService;
import com.sh.trippy.global.common.RedisConst;
import com.sh.trippy.global.exception.JwtCustomErrorCode;
import com.sh.trippy.global.exception.JwtCustomException;
import com.sh.trippy.global.security.CustomUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;


@Slf4j
@RequiredArgsConstructor
//@Component
public class AuthTokenFilter extends OncePerRequestFilter {

    private final JwtUtils jwtUtils;
    private final CustomUserDetailsService customUserDetailsService;
    private final TokenService tokenService;
    private static final String BEARER = "Bearer ";


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        log.info("======= AuthTokenFilter =========");
        String requestURI = request.getRequestURI();
        log.info("======= Request Path : " + requestURI+" =======");

        checkTokenInfoFromHeader(request);

        // antMatcher + 정규식으로 표현해줘야함, return 던져버리기

        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {

        String[] excludePath = {
                "/api/test",
                "/api/apple/login",
                "/api/apple/logout",
                "/api/google/login",
                "/api/google/logout",
                "/api/token/reissue",
                "/api/token/reissue/access",
                "/api/token/reissue/refresh",
                "/favicon.ico",
                "/swagger-ui/index.html",
                "/swagger-ui/favicon-16x16.png",
                "/swagger-ui/favicon-32x32.png",
                "/swagger-ui/swagger-initializer.js",
                "/swagger-ui/swagger-ui-standalone-preset.js",
                "/swagger-ui/swagger-ui-bundle.js",
                "/swagger-ui/swagger-ui.css.map",
                "/swagger-ui/swagger-ui-bundle.js.map",
                "/swagger-ui/swagger-ui-standalone-preset.js.map",
                "/swagger-ui/index.css",
                "/swagger-ui/swagger-ui.css",
                "/api-docs/swagger-config",
                "/api-docs/Trippy%20API",
                "/error"


        };

        String path = request.getRequestURI();

        return Arrays.stream(excludePath).anyMatch(path::startsWith);

    }


    /**
     * 토큰 검사
     * access_token, refresh_token 검사
     */
    private void checkTokenInfoFromHeader(HttpServletRequest request) {
        checkAccessToken(request);
    }


    private void checkAccessToken(HttpServletRequest request){

        //accessToken 검사
        try{
            log.info("========= Check Access Token ==========");
            String accessToken = jwtUtils.getAccessTokenFromHeader(request);

            String refreshToken = jwtUtils.getRefreshTokenFromHeader(request);

            String accessTokenFromRedis = getAccessTokenFromRedis(accessToken, refreshToken);
            log.info("====== accessToken from header : " + accessToken);
            log.info("====== accessToken from redis : " + accessTokenFromRedis);

            jwtUtils.validateAccessToken(accessToken); // access token 형식, 만료시간 등 검사

            // header와 redis의 accessToken 비교
            if (accessToken.equals(accessTokenFromRedis)) {
                setUserInSecurityContext(request, accessToken);

            }
            else{
                throw new JwtCustomException(JwtCustomErrorCode.AccessTokenNotMatchWithRedisException); // J006
            }

        }
        catch (JwtCustomException e){// accessToken 만료시 refreshToken 검사
            // refresh token이 건재하다면 access token만 재발급하면 된다.
            if(checkRefreshToken(request)){
                throw new JwtCustomException(JwtCustomErrorCode.AccessTokenExpiredException); // J005, AccessToken 만료 에러 보내주기
            }

        }

    }



    private boolean checkRefreshToken(HttpServletRequest request){

        try {
            String refreshToken = jwtUtils.getRefreshTokenFromHeader(request);

            String refreshTokenFromRedis = getRefreshTokenFromRedis(refreshToken);

            log.info("========= Check Refresh Token==========");
            log.info("====== refreshToken from header : " + refreshToken);
            log.info("====== refreshToken from redis : " + refreshTokenFromRedis);

            jwtUtils.validateRefreshToken(refreshToken);

            // header와 redis의 refreshToken 비교
            if(refreshToken.equals(refreshTokenFromRedis)){
                setUserInSecurityContext(request, refreshToken);
            }
            else{
                throw new JwtCustomException(JwtCustomErrorCode.RefreshTokenNotMatchWithRedisException); // J007
            }

        }
        // refresh_token 만료시 access, refresh 둘 다 발급하기
        catch (JwtCustomException ex){ // refreshToken 만료시 error 던지기
            throw new JwtCustomException(JwtCustomErrorCode.RefreshTokenExpiredException); // J001
        }

        return true;
    }


    private void setUserInSecurityContext(HttpServletRequest request, String token) {
        JwtClaimDto claimFromToken = jwtUtils.getClaimFromToken(token);

        UserDetails userDetails = customUserDetailsService.loadUserByUsernameAndProvider(claimFromToken.getEmail(), claimFromToken.getProvider());


        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                userDetails,
        null,
                userDetails.getAuthorities());

        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
    }



    private String getRefreshTokenFromRedis(String refreshToken){
        log.info("==== Get Refresh Token From Redis ====");
        JwtClaimDto claimFromToken = jwtUtils.getClaimFromRefreshToken(refreshToken);

        String refreshTokenFromRedis = tokenService.getTokenFromRedis(RedisConst.REFRESH_TOKEN.prefix() + claimFromToken.getUserId());
        log.info("refreshTokenFromRedis : " + refreshTokenFromRedis);
        if(refreshTokenFromRedis == null){
            log.info("===== Refresh Token From Redis is Null ====");
            throw new JwtCustomException(JwtCustomErrorCode.RefreshTokenExpiredException);
        }

        return refreshTokenFromRedis;
    }



    private String getAccessTokenFromRedis(String accessToken, String refreshToken){
        log.info("==== Get Access Token From Redis ====");
        JwtClaimDto claimFromToken = jwtUtils.getClaimFromToken(accessToken);

        String accessTokenFromRedis = tokenService.getTokenFromRedis(RedisConst.ACCESS_TOKEN.prefix() + claimFromToken.getUserId());
        log.info("accessTokenFromRedis : " + accessTokenFromRedis);

        if(accessTokenFromRedis == null){
            log.info("===== Access Token From Redis is Null ====");

            getRefreshTokenFromRedis(refreshToken);

            throw new JwtCustomException(JwtCustomErrorCode.AccessTokenExpiredException);
        }

        return accessTokenFromRedis.substring(BEARER.length());

    }



    private Long getUserIdFromToken(String token){
        JwtClaimDto userInfo = jwtUtils.getClaimFromToken(token);

        return userInfo.getUserId();
    }


}
