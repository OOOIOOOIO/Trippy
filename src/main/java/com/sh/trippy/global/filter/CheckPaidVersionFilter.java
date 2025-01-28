package com.sh.trippy.global.filter;

import com.sh.trippy.api.jwt.application.TokenService;
import com.sh.trippy.global.exception.CustomErrorCode;
import com.sh.trippy.global.exception.CustomException;
import com.sh.trippy.global.jwt.JwtClaimDto;
import com.sh.trippy.global.jwt.JwtUtils;
import com.sh.trippy.global.security.CustomUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class CheckPaidVersionFilter extends OncePerRequestFilter {

    private final JwtUtils jwtUtils;
    private final CustomUserDetailsService customUserDetailsService;
    private final TokenService tokenService;
    private static final String BEARER = "Bearer ";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        log.info("======= CheckPaidVersionFilter =========");
        String requestURI = request.getRequestURI();
        log.info("======= Request Path : " + requestURI+" =======");

        String accessToken = jwtUtils.getAccessTokenFromHeader(request);

        boolean paidFlag = jwtUtils.getClaimFromToken(accessToken).isPaidFlag();

        if(!paidFlag){
            throw new CustomException(CustomErrorCode.NotPaidVersionException);
        }

        filterChain.doFilter(request, response);

    }
}
