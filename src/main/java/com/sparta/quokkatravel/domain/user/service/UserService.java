package com.sparta.quokkatravel.domain.user.service;

import com.sparta.quokkatravel.domain.user.dto.UserLoginRequestDto;
import com.sparta.quokkatravel.domain.user.dto.UserSignupRequestDto;
import com.sparta.quokkatravel.domain.user.dto.UserResponseDto;

public interface UserService {

    UserResponseDto signup(UserSignupRequestDto userSignupRequestDto);

    String login(UserLoginRequestDto UserLoginRequestDto);

    void deleteUser(String email,String password);

}