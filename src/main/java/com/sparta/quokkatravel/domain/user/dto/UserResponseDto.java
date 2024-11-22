package com.sparta.quokkatravel.domain.user.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sparta.quokkatravel.domain.user.entity.User;
import com.sparta.quokkatravel.domain.user.entity.UserRole;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserResponseDto {
    private String email;

    private String nickname;

    private UserRole userRole;

    // password는 JSON 응답에서 제외되므로 제거할 수 있음
    @JsonIgnore
    private String password;

    public UserResponseDto(String email, String nickname, UserRole userRole) {
        this.email = email;
        this.nickname = nickname;
        this.userRole = userRole;
    }

    public static UserResponseDto from(User user) {
        return new UserResponseDto(
                user.getEmail(),
                user.getNickname(),
                user.getUserRole()
        );
    }
}