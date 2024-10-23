package com.sparta.quokkatravel.domain.auth.dto.response;

import lombok.Getter;

@Getter
public class SignUpResponseDto {
    private final String bearerToken;

    public SignUpResponseDto(String bearerToken){
        this.bearerToken = bearerToken;
    }
}
