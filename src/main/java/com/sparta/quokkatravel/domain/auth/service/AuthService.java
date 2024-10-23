package com.sparta.quokkatravel.domain.auth.service;

import com.sparta.quokkatravel.domain.auth.dto.request.SignInRequestDto;
import com.sparta.quokkatravel.domain.auth.dto.request.SignUpRequestDto;
import com.sparta.quokkatravel.domain.auth.dto.response.SignInResponseDto;
import com.sparta.quokkatravel.domain.auth.dto.response.SignUpResponseDto;

public interface AuthService {
    // 회원가입
    SignUpResponseDto signUp(SignUpRequestDto signupRequestDto);
    // 로그인
    SignInResponseDto signIn(SignInRequestDto signinRequestDto);
}
