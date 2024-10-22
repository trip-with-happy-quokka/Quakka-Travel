package com.sparta.quokkatravel.domain.common.dto;

import lombok.Getter;

@Getter
public class AuthUser {

    private String email;
    private String userRole;

    public AuthUser(String email, String userRole) {
        this.email = email;
        this.userRole = userRole;
    }
}