package com.sparta.quokkatravel.domain.user.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sparta.quokkatravel.domain.common.jwt.JwtUtil;
import com.sparta.quokkatravel.domain.user.dto.KakaoUserInfoDto;
import com.sparta.quokkatravel.domain.user.entity.User;
import com.sparta.quokkatravel.domain.user.util.KakaoApiUtil;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j(topic = "KAKAO Login")
@Service
@RequiredArgsConstructor
public class KakaoService {

    private final JwtUtil jwtUtil;
    private final KakaoApiUtil kakaoApiUtil;

    public String loginViaKakao(String code) throws JsonProcessingException {

        String accessToken = kakaoApiUtil.getToken(code);

        KakaoUserInfoDto kakaoUserInfoDto = kakaoApiUtil.getKakaoUserInfo(accessToken);

        User kakaoUser = kakaoApiUtil.registerKakaoUserIfNeeded(kakaoUserInfoDto);

        return jwtUtil.createToken(
                kakaoUser.getId(),
                kakaoUser.getEmail(),
                kakaoUser.getUserRole()
        );
    }
}
