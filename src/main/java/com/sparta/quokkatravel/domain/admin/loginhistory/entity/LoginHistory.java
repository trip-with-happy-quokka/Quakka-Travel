package com.sparta.quokkatravel.domain.admin.loginhistory.entity;

import com.sparta.quokkatravel.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "login_history")
public class LoginHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private String ipAddress; // 로그인한 IP 주소

    private LocalDateTime loginTime; // 로그인 시각

    public LoginHistory() {}

    public LoginHistory(User user, String ipAddress, LocalDateTime loginTime) {
        this.user = user;
        this.ipAddress = ipAddress;
        this.loginTime = loginTime;
    }
}
