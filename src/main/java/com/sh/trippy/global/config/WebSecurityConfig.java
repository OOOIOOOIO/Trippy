package com.sh.trippy.global.config;

import com.sh.trippy.api.jwt.application.TokenService;
import com.sh.trippy.global.filter.CheckPaidVersionFilter;
import com.sh.trippy.global.jwt.AuthEntryPointJwt;
import com.sh.trippy.global.jwt.AuthTokenFilter;
import com.sh.trippy.global.jwt.JwtExceptionHandlerFilter;
import com.sh.trippy.global.jwt.JwtUtils;
import com.sh.trippy.global.security.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HttpBasicConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.Collections;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {  // extends WebSecurityConfigurerAdapte, Spring 2.7.0부터 Deprecated 됨(현재 프로젝트 2.7.6)

    private final AuthEntryPointJwt authEntryPointJwt;
    private final JwtExceptionHandlerFilter jwtExceptionHandlerFilter;
    private final JwtUtils jwtUtils;
    private final CustomUserDetailsService customUserDetailsService;
    private final TokenService tokenService;

    @Value("${cors.address}")
    private String corsAddress;



    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {

        return authConfig.getAuthenticationManager();
    }



    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{


        http
                .httpBasic(HttpBasicConfigurer::disable)
                .cors(corsConfigurationSource -> corsConfigurationSource.configurationSource(corsConfigurationSource()))
                .csrf(AbstractHttpConfigurer::disable)

                // disable session
                .sessionManagement((sessionManagement) ->
                        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authorizeHttpRequests(authorizeRequest ->
                        authorizeRequest
                                .requestMatchers("/api/test").permitAll()
                                .requestMatchers("/api/apple/**").permitAll()
                                .requestMatchers("/api/google/**").permitAll()
                                .requestMatchers("/api/token/reissue/**").permitAll()
                                .requestMatchers("/favicon.ico").permitAll()
                                .requestMatchers("/swagger-ui/**").permitAll()
                                .requestMatchers("/api-docs/**").permitAll()
//                                .requestMatchers(new AntPathRequestMatcher("/api/token/**")).permitAll()
                                .requestMatchers("/error/**").permitAll()
//                                .requestMatchers("Request Path").permitAll()
                                .anyRequest().authenticated()
                )

                .exceptionHandling(handling -> handling.authenticationEntryPoint(authEntryPointJwt));


//        http.addFilterBefore(new AuthTokenFilter(jwtUtils, customUserDetailsService, tokenService), CheckPaidVersionFilter.class); //
        http.addFilterBefore(new AuthTokenFilter(jwtUtils, customUserDetailsService, tokenService), UsernamePasswordAuthenticationFilter.class); // 여기서 jwt 인증
        http.addFilterBefore(jwtExceptionHandlerFilter, AuthTokenFilter.class); // jwt 예외처리 필터

        return http.build();

    }

//    @Bean
//    public SecurityFilterChain checkPaidVersionFilterChain(HttpSecurity http) throws Exception{
//
//
//        http
//                .httpBasic(HttpBasicConfigurer::disable)
//                .cors(corsConfigurationSource -> corsConfigurationSource.configurationSource(corsConfigurationSource()))
//                .csrf(AbstractHttpConfigurer::disable)
//
//                // disable session
//                .sessionManagement((sessionManagement) ->
//                        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                )
//                .authorizeHttpRequests(authorizeRequest ->
//                                authorizeRequest
//                                        .requestMatchers("/api/trip/reply/**").authenticated()
//                                        .requestMatchers("/api/trip/checklist/**").permitAll()
////                                        .requestMatchers("/api/trip/checklist/**").permitAll()
////                                .requestMatchers("Request Path").permitAll()
//                                        .anyRequest().permitAll()
//                )
//
//                .exceptionHandling(handling -> handling.authenticationEntryPoint(authEntryPointJwt));
//
//
//        http.addFilterBefore(new CheckPaidVersionFilter(jwtUtils, customUserDetailsService, tokenService), UsernamePasswordAuthenticationFilter.class); // 여기서 유료버전 확인
//
//        return http.build();
//
//    }

    // CORS 설정
    CorsConfigurationSource corsConfigurationSource() {
        return request -> {
            CorsConfiguration config = new CorsConfiguration();
            config.setAllowedHeaders(Collections.singletonList("*"));
            config.setAllowedMethods(Collections.singletonList("*"));
            config.setAllowedOriginPatterns(Collections.singletonList(corsAddress));
            config.setAllowCredentials(true);
            return config;
        };
    }


}
