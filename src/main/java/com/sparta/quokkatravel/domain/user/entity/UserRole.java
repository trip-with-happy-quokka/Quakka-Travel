package com.sparta.quokkatravel.domain.user.entity;

import lombok.Getter;

@Getter
public enum UserRole {
    ADMIN, HOST, GUEST,
    OWNER, USER // 채팅방을 관리할 수 있는 권한 , 일반 사용자의 역할
}
