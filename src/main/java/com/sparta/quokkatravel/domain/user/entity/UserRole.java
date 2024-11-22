package com.sparta.quokkatravel.domain.user.entity;

import com.sparta.quokkatravel.domain.common.exception.NotFoundException;
import com.sun.jdi.request.InvalidRequestStateException;
import lombok.Getter;

import java.util.Arrays;

@Getter
public enum UserRole {
    ADMIN, HOST, GUEST, // 회원가입 하면 default로 GUEST
    OWNER, USER, // 채팅방을 관리할 수 있는 권한 , 일반 사용자의 역할
    INACTIVE; // 휴면계정

    public static UserRole of(String role) {
        // 문자열로 전달된 역할을 UserRole 열거형으로 변환
        return Arrays.stream(UserRole.values()) // UserRole의 모든 값을 스트림으로 변환
                .filter(r -> r.name().equalsIgnoreCase(role)) // role과 대소문자 구분 없이 일치하는 값 필터링
                .findFirst() // 일치하는 첫 번째 값을 찾음
                .orElseThrow(() -> new NotFoundException("유효하지 않은 UserRole")); // 일치하는 값이 없으면 예외 발생
    }
    public String toUpperCase(){
        return name().toUpperCase();
    }
}