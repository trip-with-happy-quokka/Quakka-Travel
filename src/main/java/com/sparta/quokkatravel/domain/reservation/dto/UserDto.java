package com.sparta.quokkatravel.domain.reservation.dto;

import com.sparta.quokkatravel.domain.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private Long id;
    private String email;
    private String nick_name;

    public UserDto(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.nick_name = user.getNickname();
    }
}
