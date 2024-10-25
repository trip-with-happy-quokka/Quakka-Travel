package com.sparta.quokkatravel.domain.user.service;

import com.sparta.quokkatravel.domain.user.dto.UserRequestDto;
import com.sparta.quokkatravel.domain.user.dto.UserResponseDto;

public interface UserService {

    UserResponseDto signup(UserRequestDto userRequestDto);

    String login(UserRequestDto userRequestDto);

    void deleteUser(String email,String password);

}