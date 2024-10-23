package com.sparta.quokkatravel.domain.auth.dto.response;

import lombok.Getter;

@Getter
public class SignInResponseDto {
    private final String bearerToken;

    public SignInResponseDto(String bearerToken){
        this.bearerToken = bearerToken;
    }
}